package dev.casalov.projetetincel.pages.DetailsRapport;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.casalov.projetetincel.R;

import dev.casalov.projetetincel.activities.DetailsRapport;

public class Maintenance_Infos extends Fragment implements View.OnClickListener{

    private DetailsRapport activity;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maintenance_infos,null,true);
        view.findViewById(R.id.taches).setOnClickListener(this);
        view.findViewById(R.id.photos).setOnClickListener(this);
        view.findViewById(R.id.caracteristiques).setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity = (DetailsRapport) getActivity();
        bundle = getArguments();
        activity.setSubTitle(bundle.getString("gamme_name"));
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()){
            case R.id.taches:
                fragment = new ListeCompartiment();
                break;
            case R.id.caracteristiques:
                fragment = new ListeCaracteristiques();
                break;
            case R.id.photos:
                fragment = new Photos();
        }
        fragment.setArguments(bundle);
        activity.setFragment(fragment);
    }
}
