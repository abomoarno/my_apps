package fallapro.landcrowdy.classes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.activities.Accueil;
import fallapro.landcrowdy.activities.AlerteDetails;
import fallapro.landcrowdy.activities.Resultats;

public class Page_Alerts extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.alertes,container,false);
        ArrayList<Alert> alerts = new ArrayList<>();
        alerts.add(null);
        alerts.add(null);
        alerts.add(null);
        alerts.add(null);
        alerts.add(null);
        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(new Alert_Adaptor(getActivity(),alerts));
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.voir_resultats:
                startActivity(new Intent(getActivity(),Resultats.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(getActivity(),AlerteDetails.class));
    }
}
