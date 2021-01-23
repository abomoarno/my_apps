package niamoro.annonces.pages.pages_chargement;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.activities.Chargement;
import niamoro.annonces.adapters.SimplePaysAdapter;
import niamoro.annonces.databases.managers.PaysManager;
import niamoro.annonces.utils.Pays;
import niamoro.annonces.utils.Utils;

public class ChoixPays extends Fragment implements AdapterView.OnItemClickListener {

    private List<Pays> pays;
    private Chargement context;
    private SimplePaysAdapter adapter;
    private LinearLayout empty;

    private ListView listView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = (niamoro.annonces.activities.Chargement) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.choix_pays,null,false);

        listView = view.findViewById(R.id.listView);
        empty = view.findViewById(R.id.empty);
        listView.setEmptyView(empty);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        pays = new PaysManager(context).getPays();
        adapter = new SimplePaysAdapter(context,pays);
        listView.setAdapter(adapter);
        if (pays.isEmpty()){
            getPays();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Pays country = pays.get(i);
        preferences.edit().putString("pays",country.getCode()).apply();
        Utils.PAYS = country.getCode();
        context.getAnnonces(Utils.PAYS);
        context.setFragment(new niamoro.annonces.pages.pages_chargement.Chargement());
    }

    private void getPays(){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Patientez un instant ...");
        dialog.setTitle("Chargement des données");
        dialog.show();
        final PaysManager manager = new PaysManager(context);
        final List<String> ids = manager.getAllIds();
        final List<Pays> toAdd = new ArrayList<>();
        final List<Pays> toUpdate = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pays");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,Pays>> t = new GenericTypeIndicator<HashMap<String, Pays>>() {};
                HashMap<String,Pays> countries = dataSnapshot.getValue(t);
                if (countries != null) {
                    for (String code : countries.keySet()) {
                        Pays country = countries.get(code);
                        if (ids.contains(code)){
                            toUpdate.add(country);
                            ids.remove(code);
                        }
                        else
                            toAdd.add(country);
                    }

                    manager.insertPays(toAdd);
                    manager.updatePays(toUpdate);
                    manager.deletePays(ids);
                }

                pays.addAll(manager.getPays());
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
            }
        });
    }
}
