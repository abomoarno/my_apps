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
import dev.casalov.projetetincel.pages.DetailsRapport.AddElements;
import dev.casalov.projetetincel.pages.DetailsRapport.ListeCaracteristiques;
import dev.casalov.projetetincel.utils.Maintenance;
import dev.casalov.projetetincel.utils.Operation;

public class AddElementAdapter extends ArrayAdapter<String> {

    private List<String> elements;
    private Activity context;
    private AddElements fragment;

    public AddElementAdapter(@NonNull Activity context, List<String> elements, AddElements fragment) {
        super(context, R.layout.add_element_view, elements);
        this.context = context;
        this.elements = elements;
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
            rowView = inflater.inflate(R.layout.add_element_view,null,true);
            holder = new Holder();
            holder.titre = rowView.findViewById(R.id.element_title);
            holder.add = rowView.findViewById(R.id.addElement);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder)rowView.getTag();
        }
        holder.titre.setText(elements.get(position));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.ajouterElement(position);
            }
        });
        return rowView;
    }

}