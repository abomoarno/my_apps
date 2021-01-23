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
import dev.casalov.projetetincel.utils.Rapport;

public class RapportAdapter extends ArrayAdapter<Rapport> {

    private List<Rapport> rapports;
    private Activity context;

    public RapportAdapter(@NonNull Activity context, List<Rapport> rapports) {
        super(context, R.layout.rapport_view,rapports);
        this.context = context;
        this.rapports = rapports;
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
        Rapport rapport = rapports.get(position);
        holder.titre.setText(rapport.getNom());

        holder.client.setText(rapport.getClient_id().split(";")[1]);
        holder.ville.setText(rapport.getClient_id().split(";")[2]);
        holder.dateDebut.setText(rapport.getDate_debut());

        holder.rapport_image.setImageDrawable(context.getResources().getDrawable(R.drawable.logo_etincel));

        return rowView;
    }
}