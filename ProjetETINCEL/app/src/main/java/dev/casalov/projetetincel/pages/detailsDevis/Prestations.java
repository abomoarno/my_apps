package dev.casalov.projetetincel.pages.detailsDevis;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsDevis;
import dev.casalov.projetetincel.activities.NouveauDevis;
import dev.casalov.projetetincel.adaptors.PrestationsAdapter;
import dev.casalov.projetetincel.db_mangment.DevisManager;
import dev.casalov.projetetincel.db_mangment.PrestationsManager;
import dev.casalov.projetetincel.utils.Prestation;

public class Prestations extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private List<Prestation> prestations;
    private PrestationsAdapter adapter;
    private Bundle bundle;
    private ListView listView;

    private DetailsDevis activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_devis_prestations,null,true);
        activity = (DetailsDevis) getActivity();
        view.findViewById(R.id.next).setOnClickListener(this);
        view.findViewById(R.id.addPrestation).setOnClickListener(this);
        listView = view.findViewById(R.id.prestations);
        listView.setOnItemClickListener(this);
        prestations = new ArrayList<>();

        bundle = getArguments();
        bundle = getArguments();
        if (bundle != null) {
            prestations = new PrestationsManager(activity).getFromDevis(bundle.getString("devis_id"));
        }
        adapter = new PrestationsAdapter(activity, prestations);
        listView.setAdapter(adapter);

        return view;
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
                    new DevisManager(activity).updateDevis(DetailsDevis.devis);
                    TabDetailsDevis.setPage(0);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        new PrestationDialog(activity,prestations.get(i)).show();
    }

    private class PrestationDialog extends Dialog implements View.OnClickListener{

        private EditText nom;
        private EditText prix;
        private EditText qte;

        private Prestation prestation;

        public PrestationDialog(@NonNull Context context) {
            super(context);
        }
        public PrestationDialog(@NonNull Context context, Prestation prestation) {
            super(context);
            this.prestation = prestation;
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
            if (this.prestation != null){
                nom.setText(prestation.getNom());
                prix.setText(prestation.getPrix()+"");
                qte.setText(prestation.getQuantite()+"");
            }
            findViewById(R.id.ok).setOnClickListener(this);
            findViewById(R.id.annuler).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ok:
                    String name = nom.getText().toString();
                    if (!name.isEmpty() && !qte.getText().toString().isEmpty() && !prix.getText().toString().isEmpty()) {
                        if (this.prestation ==null) {
                            Prestation prestation = new Prestation(
                                    FirebaseDatabase.getInstance().getReference().push().getKey(),
                                    name
                            );
                            prestation.setPrix(Integer.parseInt(prix.getText().toString()));
                            prestation.setQuantite(Integer.parseInt(qte.getText().toString()));
                            prestation.setDevis_id(DetailsDevis.devis.getDevis_id());
                            prestations.add(prestation);
                        }
                        else {
                            this.prestation.setPrix(Integer.parseInt(prix.getText().toString()));
                            this.prestation.setQuantite(Integer.parseInt(qte.getText().toString()));
                            this.prestation.setNom(name);
                        }
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