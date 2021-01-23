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

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.utils.Prestation;

public class PrestationsAdapter extends ArrayAdapter<Prestation> {

    private List<Prestation> prestations;
    private Activity context;

    public PrestationsAdapter(@NonNull Activity context, List<Prestation> prestations) {
        super(context, R.layout.prestation_view,prestations);
        this.context = context;
        this.prestations = prestations;
    }

    private static class Holder
    {
        TextView nom;
        TextView prix;
        TextView qte;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        Holder holder;

        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.prestation_view,null,true);
            holder = new Holder();
            holder.nom = rowView.findViewById(R.id.titre);
            holder.prix = rowView.findViewById(R.id.prix);
            holder.qte = rowView.findViewById(R.id.qte);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder)rowView.getTag();
        }

        holder.nom.setText(prestations.get(position).getNom());
        holder.prix.setText(prestations.get(position).getPrix()+" Euros" );
        holder.qte.setText(prestations.get(position).getQuantite() + "");

        return rowView;
    }
}
