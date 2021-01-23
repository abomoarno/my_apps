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
import dev.casalov.projetetincel.pages.nouveau_devis.Charges;

public class ChargeAdapter extends ArrayAdapter<String> {

    private List<String> charges;
    private Activity context;

    public ChargeAdapter(@NonNull Activity context, List<String> charges) {
        super(context, R.layout.technicien_view,charges);
        this.context = context;
        this.charges = charges;
    }

    private static class Holder
    {
        TextView nom;
        Button supprimer;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        Holder holder;

        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.technicien_view,null,true);
            holder = new Holder();
            holder.nom = rowView.findViewById(R.id.nom_technicien);
            holder.supprimer = rowView.findViewById(R.id.supprimer);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder)rowView.getTag();
        }

        holder.nom.setText(charges.get(position));
        holder.supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charges.remove(position);
                notifyDataSetChanged();
            }
        });
        return rowView;
    }
}
