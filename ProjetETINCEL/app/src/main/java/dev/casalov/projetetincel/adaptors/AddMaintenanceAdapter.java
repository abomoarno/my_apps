package dev.casalov.projetetincel.adaptors;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.native_db_managment.NativeCompartimentsManager;
import dev.casalov.projetetincel.native_db_managment.NativeOperationsManager;
import dev.casalov.projetetincel.pages.DetailsRapport.AddMaintenances;
import dev.casalov.projetetincel.utils.Maintenance;
import dev.casalov.projetetincel.utils.Operation;

public class AddMaintenanceAdapter extends ArrayAdapter<Maintenance> {

    private List<Maintenance> maintenances;
    private Activity context;
    private AddMaintenances fragment;

    public AddMaintenanceAdapter(@NonNull Activity context, List<Maintenance> maintenances, AddMaintenances fragment) {
        super(context, R.layout.add_maintenance_view, maintenances);
        this.context = context;
        this.maintenances = maintenances;
        this.fragment = fragment;
    }

    private static class Holder
    {
        TextView titre;
        Button add;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        final Holder holder;

        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.add_maintenance_view,null,true);
            holder = new Holder();
            holder.titre = rowView.findViewById(R.id.maintenance_title);
            holder.add = rowView.findViewById(R.id.addMaintenance);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder)rowView.getTag();
        }
        holder.titre.setText(maintenances.get(position).getTitle());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.ajouterMaintenance(position);
            }
        });
        return rowView;
    }

}