package fallapro.landcrowdy.classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.activities.Accueil;
import fallapro.landcrowdy.activities.Modifier_Password;

public class Page_Connexion extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.connexion,container,false);
        view.findViewById(R.id.facebook_connexion).setOnClickListener(this);
        view.findViewById(R.id.pass_forgoten).setOnClickListener(this);
        view.findViewById(R.id.inscription).setOnClickListener(this);
        view.findViewById(R.id.valider).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Accueil accueil = (Accueil)getActivity();
        switch (view.getId()){
            case R.id.facebook_connexion:
                break;
            case R.id.valider:
                if (accueil != null)
                    accueil.setViewPager(3);
                break;
            case R.id.pass_forgoten:
                startActivity(new Intent(getActivity(),Modifier_Password.class));
                break;
            case R.id.inscription:
                if (accueil != null)
                    accueil.setViewPager(5);
                break;
        }
    }
}