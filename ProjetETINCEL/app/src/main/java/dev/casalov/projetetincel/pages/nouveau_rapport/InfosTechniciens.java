package dev.casalov.projetetincel.pages.nouveau_rapport;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.NouveauRapport;
import dev.casalov.projetetincel.adaptors.TechnicienAdapter;

public class InfosTechniciens extends Fragment implements View.OnClickListener{

    private List<String> techniciens;
    private TechnicienAdapter adapter;
    private ListView listView;
    private NouveauRapport activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infos_techniciens,null,true);
        listView = view.findViewById(R.id.listView);
        view.findViewById(R.id.addTechnician).setOnClickListener(this);
        view.findViewById(R.id.next).setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        techniciens = new ArrayList<>();
        activity = (NouveauRapport)getActivity();
        adapter = new TechnicienAdapter(activity,techniciens,this);
        listView.setAdapter(adapter);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (activity != null){
            activity.setSubTitle("Liste des techniciens");
            activity.activateFooter(3);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (activity != null){
            activity.desactivateFooter(3);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addTechnician:
                if (activity != null){
                    new TechnicienDialog(activity).show();
                }
                break;
            case R.id.next:
                if (activity != null && !techniciens.isEmpty()){
                    StringBuilder tects = new StringBuilder();
                    for (String st:techniciens)
                        tects.append(st).append(";");
                    NouveauRapport.rapport.setTechniciens(tects.toString().substring(0,tects.length()-1));
                    activity.setFragment(new InfosRedaction());
                }
                break;
        }
    }

    public void deleteNom(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Supprimer un technicien");
        builder.setMessage("Souhaitez-vous vraiment supprimer " + techniciens.get(position) + " de le liste ?");

        builder.setCancelable(false).setPositiveButton(
                "Confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        techniciens.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }
        ).setNegativeButton("Annuler", null);

        builder.create();
        builder.show();
    }

    private class TechnicienDialog extends Dialog implements View.OnClickListener{

        private EditText nom;

        TechnicienDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.technician_dialog);
            setCancelable(false);
            nom = findViewById(R.id.nom_technicien);
            findViewById(R.id.ok).setOnClickListener(this);
            findViewById(R.id.annuler).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ok:
                    String name = nom.getText().toString();
                    if (!name.isEmpty()) {
                        techniciens.add(name);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.supprimer:
                    break;
            }
            dismiss();
        }
    }
}
