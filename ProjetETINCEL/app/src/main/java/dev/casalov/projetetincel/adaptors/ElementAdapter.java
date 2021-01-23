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
import dev.casalov.projetetincel.pages.DetailsRapport.ListeCaracteristiques;

public class ElementAdapter extends ArrayAdapter<String> {

    private List<String> elements_name;
    private List<String> elements_valeur;
    private Activity context;
    private ListeCaracteristiques fragment;

    public ElementAdapter(@NonNull Activity context, List<String> elements_name, List<String> elements_valeur, ListeCaracteristiques fragment) {
        super(context, R.layout.element_view, elements_name);
        this.context = context;
        this.elements_name = elements_name;
        this.elements_valeur = elements_valeur;
        this.fragment = fragment;
    }

    private static class Holder
    {
        TextView titre;
        TextView valeur;
        Button supprimer;
        Button modifier;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View rowView = convertView;
        final Holder holder;

        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.element_view,null,true);
            holder = new Holder();
            holder.titre = rowView.findViewById(R.id.element_title);
            holder.valeur = rowView.findViewById(R.id.element_value);
            holder.supprimer = rowView.findViewById(R.id.supprimer);
            holder.modifier = rowView.findViewById(R.id.modifdier);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder)rowView.getTag();
        }
        holder.titre.setText(elements_name.get(position));
        holder.valeur.setText(elements_valeur.get(position));

        holder.supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.supprimer(position);
            }
        });
        holder.modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.modifier(position);
            }
        });

        return rowView;
    }
}