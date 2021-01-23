package dev.casalov.projetetincel.adaptors;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import dev.casalov.projetetincel.R;

public class CompartimentAdapter extends ArrayAdapter<String> {

    private List<String> compartiments;
    private Activity context;

    public CompartimentAdapter(@NonNull Activity context, List<String> compartiments) {
        super(context, R.layout.compartiment_view, compartiments);
        this.context = context;
        this.compartiments = compartiments;
    }
    private static class Holder
    {
        TextView titre;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        final Holder holder;

        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.compartiment_view,null,true);
            holder = new Holder();
            holder.titre = rowView.findViewById(R.id.compartiment_title);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder)rowView.getTag();
        }
        holder.titre.setText(compartiments.get(position));

        return rowView;
    }
}