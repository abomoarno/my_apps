package dev.casalov.projetetincel.pages.DetailsRapport.informations_generales;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.db_mangment.RapportsManager;
import dev.casalov.projetetincel.utils.Rapport;

public class InfosTechniciens extends Fragment {

    private ListView listView;
    private List<String> techniciens;
    private DetailsRapport activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_infos_techniciens,null,false);
        techniciens = new ArrayList<>();
        listView = view.findViewById(R.id.listView);
        activity = (DetailsRapport) getActivity();

        Bundle bundle = getArguments();
        String project_id = bundle.getString("project_id");
        Rapport rapport = new RapportsManager(activity).getRapport(project_id);
        techniciens = Arrays.asList(rapport.getTechniciens().split(";"));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                techniciens);
        listView.setAdapter(arrayAdapter);
        return view;
    }

}
