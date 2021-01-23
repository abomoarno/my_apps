package dev.casalov.projetetincel.adaptors;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.db_mangment.ElementsManager;
import dev.casalov.projetetincel.db_mangment.MaintenanceManager;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.utils.Maintenance;

public class MaintenanceAdapter extends ArrayAdapter<Maintenance> {

    private List<Maintenance> maintenances;
    private Activity context;

    public MaintenanceAdapter(@NonNull Activity context, List<Maintenance> maintenances) {
        super(context, R.layout.maintenance_view, maintenances);
        this.context = context;
        this.maintenances = maintenances;
    }

    private static class Holder
    {
        TextView titre;
        TextView supprimer;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        final Holder holder;

        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.maintenance_view,null,true);
            holder = new Holder();
            holder.titre = rowView.findViewById(R.id.maintenance_title);
            holder.supprimer = rowView.findViewById(R.id.supprimer);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder)rowView.getTag();
        }
        holder.titre.setText(maintenances.get(position).getTitle());

        holder.supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Suppression de la Gamme")
                        .setMessage("Voulez-vous vraiment supprimer la gamme \" " + maintenances.get(position).getTitle()
                                +" \" et toutes tâches associées ?");
                dialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OperationsManager manager = new OperationsManager(context);
                        manager.deleteOperation(maintenances.get(position).getMaintenance_id(), maintenances.get(position).getProjet_id());
                        new MaintenanceManager(context).deleteMaintenance(maintenances.get(position));
                        new ElementsManager(context).deleteFromGamme(maintenances.get(position).getMaintenance_id());
                        maintenances.remove(position);
                        notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });

        return rowView;
    }
}