package niamoro.annonces.pages.pages_setting;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.adapters.PaysAdapter;
import niamoro.annonces.databases.managers.AnnonceManager;
import niamoro.annonces.databases.managers.PaysManager;
import niamoro.annonces.utils.Annonce;
import niamoro.annonces.utils.Pays;
import niamoro.annonces.utils.Utils;

public class ChoixPays extends Fragment implements AdapterView.OnItemClickListener {

    private List<Pays> pays;
    private Context context;
    private PaysAdapter adapter;
    private LinearLayout empty;

    private ListView listView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.choix_pays, null, false);
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
        adapter = new PaysAdapter(context, pays);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Pays country = pays.get(i);
        if (country.getCode().equals(Utils.PAYS) || !(new Utils(context).isNetworkReachable()))
            return;
        getAnnonces(country.getCode());
    }

    public void getAnnonces(final String pays){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Chargement ...");
        dialog.setMessage("Veuillez patienter  ...");
        dialog.setCancelable(false);
        dialog.show();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        final AnnonceManager manager = new AnnonceManager(context);
        final List<Annonce> toAdd = new ArrayList<>();
        final List<Annonce> toUpdate = new ArrayList<>();
        final List<String> ids = manager.getAllIds(pays);

        firestore.collection(pays)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Annonce annonce = document.toObject(Annonce.class);

                                if (ids.contains(annonce.getId())){
                                    toUpdate.add(annonce);
                                    ids.remove(annonce.getId());
                                }
                                else
                                    toAdd.add(annonce);
                            }

                            manager.insertAnnonces(toAdd);
                            manager.updateAnnonces(toUpdate);
                            manager.deleteAnnonces(ids,pays);

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            preferences.edit().putString("pays",pays).apply();
                            Utils.PAYS = pays;
                            adapter.notifyDataSetChanged();

                            Toast.makeText(context,"Pays Changé avec succès ...",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.e("Afrimoov","Erreur",task.getException());
                        }
                        dialog.cancel();
                    }
                });
    }
}