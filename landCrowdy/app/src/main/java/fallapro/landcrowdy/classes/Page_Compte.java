package fallapro.landcrowdy.classes;

import android.content.Context;
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
import fallapro.landcrowdy.activities.Modifier_Email;
import fallapro.landcrowdy.activities.Modifier_Password;
import fallapro.landcrowdy.activities.Notifications;
import fallapro.landcrowdy.activities.Resultats;
import fallapro.landcrowdy.activities.VoirDonnesPersonnelles;

public class Page_Compte extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.compte,container,false);
        view.findViewById(R.id.donnees_personnelles).setOnClickListener(this);
        view.findViewById(R.id.modifier_mail).setOnClickListener(this);
        view.findViewById(R.id.modifier_password).setOnClickListener(this);
        view.findViewById(R.id.notifications_params).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.donnees_personnelles:
                startActivity(new Intent(getActivity(),VoirDonnesPersonnelles.class));
                break;
            case R.id.modifier_mail:
                startActivity(new Intent(getActivity(),Modifier_Email.class));
                break;
            case R.id.modifier_password:
                startActivity(new Intent(getActivity(),Modifier_Password.class));
                break;
            case R.id.notifications_params:
                startActivity(new Intent(getActivity(),Notifications.class));
                break;
        }
    }
}
