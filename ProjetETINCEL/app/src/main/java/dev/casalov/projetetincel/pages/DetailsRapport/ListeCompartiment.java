package dev.casalov.projetetincel.pages.DetailsRapport;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.adaptors.CompartimentAdapter;
import dev.casalov.projetetincel.adaptors.MaintenanceAdapter;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.native_db_managment.NativeCompartimentsManager;
import dev.casalov.projetetincel.native_db_managment.NativeGammesManager;
import dev.casalov.projetetincel.utils.Maintenance;

public class ListeCompartiment extends Fragment implements AdapterView.OnItemClickListener {

    private List<String> compartiments_ids;
    private DetailsRapport activity;
    private Bundle bundle;
    private String project_id;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.liste_maintenance, null, false);
        listView = view.findViewById(R.id.listView);
        activity = (DetailsRapport) getActivity();
        view.findViewById(R.id.addMaintenance).setVisibility(View.GONE);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        bundle.putString("compartiment_id", compartiments_ids.get(i));
        Fragment fragment = new ListeTaches();
        fragment.setArguments(bundle);
        activity.setFragment(fragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.setSubTitle("Liste des Compartiments");
        DetailsRapport.selectedPage = 4;
        bundle = getArguments();
        project_id = bundle.getString("project_id");
        String gamme_id = bundle.getString("gamme_id");
        String maintenance_id = bundle.getString("maintenance_id");
        compartiments_ids = new ArrayList<>();
        compartiments_ids = new OperationsManager(activity).getCompartiments(project_id,maintenance_id);
        List<String> compartiments_names = new ArrayList<>();
        for (String id:compartiments_ids){
            Map<String, String> compartiment = new NativeCompartimentsManager(activity).getCompartiment(id);
            compartiments_names.add(compartiment.get("compartiment_nom"));
        }
        listView.setAdapter(new CompartimentAdapter(activity, compartiments_names));
    }
}