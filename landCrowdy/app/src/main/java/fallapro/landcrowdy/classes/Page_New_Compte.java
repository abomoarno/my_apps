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
import fallapro.landcrowdy.activities.EmailSendNotification;

public class Page_New_Compte extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_creation,container,false);
        view.findViewById(R.id.facebook_connexion).setOnClickListener(this);
        view.findViewById(R.id.connexion).setOnClickListener(this);
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
                startActivity(new Intent(getActivity(),EmailSendNotification.class));
                getActivity().finish();
                break;
            case R.id.connexion:
                if (accueil != null)
                    accueil.setViewPager(4);
                break;
        }
    }
}