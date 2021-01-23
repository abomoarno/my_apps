package dev.casalov.projetetincel.pages.nouveau_devis;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsDevis;
import dev.casalov.projetetincel.activities.ListeDevis;
import dev.casalov.projetetincel.activities.NouveauDevis;
import dev.casalov.projetetincel.adaptors.PrestationsAdapter;
import dev.casalov.projetetincel.db_mangment.DevisManager;
import dev.casalov.projetetincel.db_mangment.PrestationsManager;
import dev.casalov.projetetincel.utils.Devis;
import dev.casalov.projetetincel.utils.Prestation;

public class Prestations extends Fragment implements View.OnClickListener {

    private List<Prestation> prestations;
    private PrestationsAdapter adapter;
    private ListView listView;

    private NouveauDevis activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_devis_prestations,null,true);
        activity = (NouveauDevis) getActivity();
        view.findViewById(R.id.next).setOnClickListener(this);
        view.findViewById(R.id.addPrestation).setOnClickListener(this);

        listView = view.findViewById(R.id.prestations);
        prestations = new ArrayList<>();

        adapter = new PrestationsAdapter(activity, prestations);
        listView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (activity != null){
            activity.setSubTitle("Liste des Prestations");
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (activity != null){
            activity.setSubTitle("Liste des charges");
            activity.activateFooter(4);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (activity != null){
            activity.desactivateFooter(4);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addPrestation:
                if (activity != null){
                    new PrestationDialog(activity).show();
                }
                break;
            case R.id.next:
                if (!prestations.isEmpty()){
                    for (Prestation prestation:prestations){
                        new PrestationsManager(activity).insertPrestation(prestation);
                    }
                    new DevisManager(activity).insertDevis(NouveauDevis.devis);
                    Intent intent = new Intent(activity, ListeDevis.class);
                    startActivity(intent);
                    activity.finish();
                }
                break;
        }
    }

    private class PrestationDialog extends Dialog implements View.OnClickListener{

        private EditText nom;
        private EditText prix;
        private EditText qte;

        public PrestationDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.prestation_dialog);
            setCancelable(false);
            nom = findViewById(R.id.titre);
            prix = findViewById(R.id.prix);
            qte = findViewById(R.id.qte);
            findViewById(R.id.ok).setOnClickListener(this);
            findViewById(R.id.annuler).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ok:
                    String name = nom.getText().toString();
                    if (!name.isEmpty() && !qte.getText().toString().isEmpty() && !prix.getText().toString().isEmpty()) {
                        Prestation prestation = new Prestation(
                                FirebaseDatabase.getInstance().getReference().push().getKey(),
                                name
                        );
                        prestation.setPrix(Integer.parseInt(prix.getText().toString()));
                        prestation.setQuantite(Integer.parseInt(qte.getText().toString()));
                        prestation.setDevis_id(NouveauDevis.devis.getDevis_id());
                        prestations.add(prestation);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.annuler:
                    break;
            }
            dismiss();
        }
    }

}