package dev.casalov.projetetincel.pages.DetailsRapport;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.adaptors.AddMaintenanceAdapter;
import dev.casalov.projetetincel.db_mangment.MaintenanceManager;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.native_db_managment.NativeCompartimentsManager;
import dev.casalov.projetetincel.native_db_managment.NativeGammesManager;
import dev.casalov.projetetincel.native_db_managment.NativeOperationsManager;
import dev.casalov.projetetincel.utils.Maintenance;
import dev.casalov.projetetincel.utils.Operation;

public class AddMaintenances extends Fragment {

    private List<Maintenance> maintenances;
    private ListView listView;
    private DetailsRapport activity;
    private String project_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.liste_maintenance, null, false);
        view.findViewById(R.id.addMaintenance).setVisibility(View.GONE);
        listView = view.findViewById(R.id.listView);
        activity = (DetailsRapport) getActivity();
        maintenances = new ArrayList<>();

        Bundle bundle = getArguments();
        project_id = bundle.getString("project_id");

        Map<String,String> gammes = new NativeGammesManager(activity).getAllGammes();

        for (String key:gammes.keySet()){

            Maintenance maintenance = new Maintenance(gammes.get(key), key);
            maintenance.setProjet_id(project_id);
            maintenances.add(maintenance);

        }
        listView.setAdapter(new AddMaintenanceAdapter(activity,maintenances, this));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.setSubTitle("Ajouter des maintenances");
        DetailsRapport.selectedPage = 5;
    }

    public void ajouterMaintenance(int position){
        new MaintenanceDialog(activity,position).show();
    }

    private class MaintenanceDialog extends Dialog implements View.OnClickListener{

        private EditText valeur;
        private int position;

        MaintenanceDialog(@NonNull Context context, int position) {
            super(context);
            this.position = position;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.maintenance_dialog);
            setCancelable(false);
            valeur = findViewById(R.id.valeur);
            findViewById(R.id.ok).setOnClickListener(this);
            findViewById(R.id.annuler).setOnClickListener(this);
            valeur.setText(maintenances.get(position).getTitle());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ok:
                    maintenances.get(position).setMaintenance_id(
                            new Random().nextInt(100)+""
                                    + new Random().nextInt(100)
                                    + new Random().nextInt(100)
                                    + new Random().nextInt(100)
                                    + new Random().nextInt(100)+"");
                    maintenances.get(position).setTitle(valeur.getText().toString());
                    List<Map<String,String>> compartiments = new
                            NativeCompartimentsManager(activity).getFromGamme(maintenances.get(position).getInitial_id());

                    for (Map<String, String> compartiment:compartiments){
                        List<Map<String,String>> nativeOps = new
                                NativeOperationsManager(activity).getFromCompartiment(compartiment.get("compartiment_id"));
                        for (Map<String, String> operation:nativeOps){
                            Operation op = new Operation(operation.get("operation_id"), operation.get("operation_nom"));
                            op.setGamme_id(maintenances.get(position).getMaintenance_id());
                            op.setStatut(-1);
                            op.setProjet_id(maintenances.get(position).getProjet_id());
                            op.setCompartiment_id(operation.get("compartiment_id"));
                            op.setObservations("");
                            new OperationsManager(activity).insertOperation(op);
                        }
                    }
                    new MaintenanceManager(activity).insertMaintenance(maintenances.get(position));
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setTitle("Ajout d'une maintenance");
                    dialog.setMessage("Votre Mainenance a bien été ajouté !!");
                    dialog.setPositiveButton("OK", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                    break;
                case R.id.supprimer:
                    break;
            }
            dismiss();
        }
    }
}
