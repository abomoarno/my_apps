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
import dev.casalov.projetetincel.pages.nouveau_rapport.InfosTechniciens;

public class TechnicienAdapter extends ArrayAdapter {

    private List<String> techniciens;
    private Activity context;
    private InfosTechniciens fragment;

    public TechnicienAdapter(@NonNull Activity context, List<String> techniciens,InfosTechniciens fragment) {
        super(context, R.layout.technicien_view,techniciens);
        this.context = context;
        this.techniciens = techniciens;
        this.fragment = fragment;
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

        holder.nom.setText(techniciens.get(position));
        holder.supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.deleteNom(position);
            }
        });
        return rowView;
    }
}
