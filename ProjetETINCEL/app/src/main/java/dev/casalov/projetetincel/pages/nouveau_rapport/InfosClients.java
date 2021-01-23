package dev.casalov.projetetincel.pages.nouveau_rapport;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.NouveauRapport;

public class InfosClients extends Fragment implements View.OnClickListener {

    private NouveauRapport activity;
    private EditText nom_client;
    private EditText mobile;
    private EditText fixe;
    private EditText email;
    private EditText address;
    private EditText entreprise;
    private EditText ville;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infos_client,null,true);

        view.findViewById(R.id.next).setOnClickListener(this);
        activity = (NouveauRapport)getActivity();

        nom_client = view.findViewById(R.id.nom);
        ville = view.findViewById(R.id.ville);
        mobile = view.findViewById(R.id.mobile);
        fixe = view.findViewById(R.id.fixe);
        email = view.findViewById(R.id.mail);
        address = view.findViewById(R.id.adresse);
        entreprise = view.findViewById(R.id.entreprise);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (activity != null){
            activity.setSubTitle("Informations sur le Client");
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (activity != null){
            activity.setSubTitle("Informations sur le Client");
            activity.activateFooter(2);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (activity != null){
            activity.desactivateFooter(2);
        }
    }

    @Override
    public void onClick(View view) {
        if (verify()) {

            Random random = new Random();

            String ident = "CL"+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(100);

            NouveauRapport.rapport.setClient_id(
                    ident + ";" +
                    nom_client.getText().toString() + ";" +
                    ville.getText().toString() + ";" +
                    mobile.getText().toString() + ";" +
                    fixe.getText().toString() + ";" +
                    email.getText().toString() + ";" +
                    address.getText() + ";" +
                    entreprise.getText().toString()
            );
            activity.setFragment(new InfosTechniciens());
        }
    }

    private boolean verify(){
        return !nom_client.getText().toString().isEmpty();
    }
}
