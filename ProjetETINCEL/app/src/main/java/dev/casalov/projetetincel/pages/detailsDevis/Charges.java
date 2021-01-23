package dev.casalov.projetetincel.pages.detailsDevis;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsDevis;
import dev.casalov.projetetincel.activities.NouveauDevis;
import dev.casalov.projetetincel.adaptors.ChargeAdapter;
import dev.casalov.projetetincel.db_mangment.DevisManager;
import dev.casalov.projetetincel.utils.Devis;

import static dev.casalov.projetetincel.activities.DetailsDevis.devis;

public class Charges extends Fragment implements View.OnClickListener {


    private List<String> charge_client;
    private List<String> charge_etincel;

    private ChargeAdapter adapter1 ;
    private ChargeAdapter adapter2 ;

    private ListView client;
    private ListView etincel;

    private DetailsDevis activity;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_devis_charges,null,true);
        activity = (DetailsDevis) getActivity();
        view.findViewById(R.id.next).setOnClickListener(this);
        view.findViewById(R.id.addCharge).setOnClickListener(this);

        client = view.findViewById(R.id.charges_clients);
        etincel = view.findViewById(R.id.charges_etincel);

        View header_client = inflater.inflate(R.layout.client_charge_header,client,false);
        View header_etincel = inflater.inflate(R.layout.etincel_charge_header,client,false);
        client.addHeaderView(header_client);
        etincel.addHeaderView(header_etincel);

        charge_client = new ArrayList<>();
        charge_etincel = new ArrayList<>();

        bundle = getArguments();
        if (bundle != null){
            charge_client.addAll(Arrays.asList(devis.getCharges_client().split(";")));
            charge_etincel.addAll(Arrays.asList(devis.getCharges_etincel().split(";")));
        }
        adapter1 = new ChargeAdapter(activity,charge_client);
        adapter2 = new ChargeAdapter(activity,charge_etincel);
        client.setAdapter(adapter1);
        etincel.setAdapter(adapter2);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addCharge:
                if (activity != null){
                    new ChargeDialog(activity).show();
                }
                break;
            case R.id.next:
                StringBuilder c_client = new StringBuilder();
                for (String st:charge_client)
                    c_client.append(st).append(";");
                StringBuilder c_etincel = new StringBuilder();
                for (String st:charge_etincel)
                    c_etincel.append(st).append(";");
                if (!c_client.toString().isEmpty())
                    devis.setCharges_client(c_client.toString().substring(0,c_client.length()-1));
                else
                    devis.setCharges_client("");
                if (!c_etincel.toString().isEmpty())
                    devis.setCharges_etincel(c_etincel.toString().substring(0,c_etincel.length()-1));
                else
                    devis.setCharges_etincel("");
                TabDetailsDevis.setPage(3);
                break;
        }
    }

    private class ChargeDialog extends Dialog implements View.OnClickListener{

        private EditText nom;
        private Spinner responsable;
        private String respo;
        private List<String> responsables;

        public ChargeDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.charge_dialog);
            setCancelable(false);
            nom = findViewById(R.id.titre);
            responsable = findViewById(R.id.responsable);
            responsables = Arrays.asList(getResources().getStringArray(R.array.responsables));
            responsable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    respo = responsables.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            findViewById(R.id.ok).setOnClickListener(this);
            findViewById(R.id.annuler).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ok:
                    String name = nom.getText().toString();
                    if (!name.isEmpty()) {
                        if (respo.equals("Etincel")){
                            charge_etincel.add(name);
                            adapter2.notifyDataSetChanged();
                        }
                        else {
                            charge_client.add(name);
                            adapter1.notifyDataSetChanged();
                        }
                    }
                    break;
                case R.id.annuler:
                    break;
            }
            dismiss();
        }
    }

}