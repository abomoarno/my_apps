package dev.casalov.projetetincel.adaptors;

import android.app.Activity;
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
import dev.casalov.projetetincel.utils.Devis;
import dev.casalov.projetetincel.utils.Rapport;

public class DevisAdapter extends ArrayAdapter<Devis> {

    private List<Devis> devis;
    private Activity context;

    public DevisAdapter(@NonNull Activity context, List<Devis> devis) {
        super(context, R.layout.rapport_view,devis);
        this.context = context;
        this.devis = devis;
    }

    private static class Holder
    {
        TextView titre;
        TextView client;
        TextView ville;
        TextView dateDebut;
        ImageView rapport_image;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        final Holder holder;

        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rapport_view,null,true);
            holder = new Holder();
            holder.titre = rowView.findViewById(R.id.rapport_title);
            holder.client = rowView.findViewById(R.id.client_name);
            holder.ville = rowView.findViewById(R.id.client_ville);
            holder.dateDebut = rowView.findViewById(R.id.dateDebut);
            holder.rapport_image = rowView.findViewById(R.id.rapport_image);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder)rowView.getTag();
        }
        Devis devis = this.devis.get(position);
        holder.titre.setText(devis.getNom());
        holder.client.setText(devis.getClient_nom());
        holder.ville.setText(devis.getVille());
        holder.dateDebut.setText(devis.getDate_document());

        holder.rapport_image.setImageDrawable(context.getResources().getDrawable(R.drawable.logo_etincel));

        return rowView;
    }
}