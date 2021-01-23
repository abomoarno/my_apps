package dev.casalov.projetetincel.adaptors;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.utils.Operation;

public class TacheAdapter extends ArrayAdapter<Operation> {

    private List<Operation> operations;
    private Activity context;

    public TacheAdapter(@NonNull Activity context, List<Operation> techniciens) {
        super(context, R.layout.technicien_view,techniciens);
        this.context = context;
        this.operations = techniciens;
    }

    private static class Holder
    {
        TextView titre;
        TextView status;
        TextView observations;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        final Holder holder;

        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.tache_view,null,true);
            holder = new Holder();
            holder.titre = rowView.findViewById(R.id.tache_texte);
            holder.status = rowView.findViewById(R.id.finish);
            holder.observations = rowView.findViewById(R.id.tache_observations);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder)rowView.getTag();
        }

        holder.titre.setText(operations.get(position).getNom());
        if (!operations.get(position).getObservations().isEmpty()) {
            holder.observations.setVisibility(View.VISIBLE);
            holder.observations.setText(operations.get(position).getObservations());
        }
        else
            holder.observations.setVisibility(View.GONE);
       switch (operations.get(position).isStatut()){
           case -1:
               holder.status.setText("Non Réalisée");
               holder.status.setBackgroundColor(Color.RED);
               break;
           case 0:
               holder.status.setText("A observer");
               holder.status.setBackgroundColor(Color.rgb(244,188, 68));
               break;
           case 1:
               holder.status.setText("Réalisée");
               holder.status.setBackgroundColor(Color.GREEN);
               break;
       }
        return rowView;
    }
}