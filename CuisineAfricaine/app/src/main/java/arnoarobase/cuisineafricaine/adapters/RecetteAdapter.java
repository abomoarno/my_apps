package arnoarobase.cuisineafricaine.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.classes.Recette;

public class RecetteAdapter extends ArrayAdapter<Recette> {

    private Activity context;

    public RecetteAdapter(@NonNull Activity context, List<Recette> recettes) {
        super(context, R.layout.recette_view, recettes);

        this.context = context;
    }

    private static class Holder{
        ImageView recette_image;
        TextView recette_nom;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Holder holder;

        if (view == null){
            view = context.getLayoutInflater().inflate(R.layout.recette_view, null, false);
            holder = new Holder();
            holder.recette_image = view.findViewById(R.id.recette_image);
            holder.recette_nom = view.findViewById(R.id.recette_nom);

            view.setTag(holder);
        }
        else
            holder = (Holder)view.getTag();

        return view;
    }
}
