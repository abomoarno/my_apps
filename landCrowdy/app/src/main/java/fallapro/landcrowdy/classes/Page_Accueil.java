package fallapro.landcrowdy.classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.activities.Accueil;
import fallapro.landcrowdy.activities.CreerALert;

public class Page_Accueil extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_accueil,container,false);

        Button creer = view.findViewById(R.id.creer_compte);
        creer.setOnClickListener(this);
        view.findViewById(R.id.creer_alerte).setOnClickListener(this);
        view.findViewById(R.id.connexion).setOnClickListener(this);

        view.findViewById(R.id.bar_to_hide_if_connected).setVisibility(View.GONE);
        view.findViewById(R.id.bar_to_hide_if_not_connected).setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onClick(View view) {
        Accueil accueil = (Accueil)getActivity();
        switch (view.getId()){
            case R.id.creer_compte:
                if (accueil != null)
                    accueil.setViewPager(5);
                break;
            case R.id.creer_alerte:
                startActivity(new Intent(getActivity(), CreerALert.class));
                break;
            case R.id.connexion:
                if (accueil != null)
                    accueil.setViewPager(4);
                break;
        }
    }
}