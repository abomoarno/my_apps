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
import dev.casalov.projetetincel.adaptors.MaintenanceAdapter;
import dev.casalov.projetetincel.db_mangment.MaintenanceManager;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.native_db_managment.NativeElementsManager;
import dev.casalov.projetetincel.native_db_managment.NativeGammesManager;
import dev.casalov.projetetincel.utils.Maintenance;

public class ListeMaintenances extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private List<Maintenance> maintenances;
    private ListView listView;
    private DetailsRapport activity;

    private String project_id;

    public ListeMaintenances(){
        DetailsRapport.selectedPage = 3;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.liste_maintenance, null, false);
        listView = view.findViewById(R.id.listView);
        activity = (DetailsRapport) getActivity();
        view.findViewById(R.id.addMaintenance).setOnClickListener(this);
        Bundle bundle = getArguments();
        project_id = bundle.getString("project_id");
        maintenances = new MaintenanceManager(activity).getAllMaintenances(project_id);
        listView.setAdapter(new MaintenanceAdapter(activity,maintenances));
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putString("gamme_id", maintenances.get(i).getInitial_id());
        bundle.putString("maintenance_id", maintenances.get(i).getMaintenance_id());
        bundle.putString("project_id", maintenances.get(i).getProjet_id());
        bundle.putString("gamme_name", maintenances.get(i).getTitle());
        Fragment fragment;
        fragment = new Maintenance_Infos();
        fragment.setArguments(bundle);
        activity.setFragment(fragment);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = new AddMaintenances();
        fragment.setArguments(getArguments());
        activity.setFragment(fragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.setSubTitle("Liste des Maintenances");
        DetailsRapport.selectedPage = 3;
    }
}