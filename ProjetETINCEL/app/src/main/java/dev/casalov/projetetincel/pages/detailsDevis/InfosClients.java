package dev.casalov.projetetincel.pages.detailsDevis;

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
import dev.casalov.projetetincel.activities.DetailsDevis;
import dev.casalov.projetetincel.activities.NouveauDevis;
import dev.casalov.projetetincel.db_mangment.DevisManager;
import dev.casalov.projetetincel.utils.Devis;

import static dev.casalov.projetetincel.activities.DetailsDevis.devis;

public class InfosClients extends Fragment implements View.OnClickListener {

    private DetailsDevis activity;
    private EditText nom_client;
    private EditText mobile;
    private EditText fixe;
    private EditText email;
    private EditText address;
    private EditText entreprise;
    private Bundle bundle;
    private EditText ville;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_devis_infos_client,null,true);

        view.findViewById(R.id.next).setOnClickListener(this);
        activity = (DetailsDevis) getActivity();

        nom_client = view.findViewById(R.id.nom);
        ville = view.findViewById(R.id.ville);
        mobile = view.findViewById(R.id.mobile);
        fixe = view.findViewById(R.id.fixe);
        email = view.findViewById(R.id.mail);
        address = view.findViewById(R.id.adresse);
        entreprise = view.findViewById(R.id.entreprise);

        bundle = getArguments();
        if (bundle != null){
            nom_client.setText(devis.getClient_nom());
            mobile.setText(devis.getClient_mobile());
            fixe.setText(devis.getClient_fixe());
            email.setText(devis.getClient_mail());
            address.setText(devis.getClient_adresse());
            entreprise.setText(devis.getClient_entreprise());
            ville.setText(devis.getVille());
        }

        bundle = getArguments();
        if (bundle != null){
            Devis devis = new DevisManager(activity).getDevis(bundle.getString("devis_id"));
            nom_client.setText(devis.getClient_nom());
            mobile.setText(devis.getClient_mobile());
            fixe.setText(devis.getClient_fixe());
            email.setText(devis.getClient_mail());
            address.setText(devis.getClient_adresse());
            ville.setText(devis.getVille());
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        if (verify()) {
            Random random = new Random();

            String ident = "CL"+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(100);

            devis.setClient_id(
                   ident + ";" +
                    nom_client.getText().toString() + ";" +
                    mobile.getText().toString() + ";" +
                    fixe.getText().toString() + ";" +
                    email.getText().toString() + ";" +
                    address.getText().toString()+";"+
                   entreprise.getText().toString()
            );
            devis.setVille(ville.getText().toString());
            TabDetailsDevis.setPage(2);
        }
    }

    private boolean verify(){
        return !nom_client.getText().toString().isEmpty()&&
                !mobile.getText().toString().isEmpty()&&
                !address.getText().toString().isEmpty();
    }
}