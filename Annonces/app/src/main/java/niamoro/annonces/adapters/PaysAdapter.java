package niamoro.annonces.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.utils.Pays;
import niamoro.annonces.utils.Utils;

public class PaysAdapter extends ArrayAdapter<Pays> {

    private List<Pays> pays;
    private Context context;

    public PaysAdapter(@NonNull Context context, @NonNull List<Pays> objects) {
        super(context, R.layout.pays_view, objects);
        this.pays = objects;
        this.context = context;
    }

    private static class Holder{
        TextView titre;
        ImageView drapeau;
        ImageView tick;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Pays country = pays.get(position);
        View view = convertView;
        Holder holder;

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.pays_view,null,true);

            holder = new Holder();

            holder.drapeau = view.findViewById(R.id.drapeau);
            holder.titre = view.findViewById(R.id.nom);
            holder.tick = view.findViewById(R.id.tick);
            view.setTag(holder);
        }
        else
            holder = (Holder)view.getTag();

        holder.titre.setText(country.getNom());
        if (country.getCode().equals(Utils.PAYS)){
            holder.tick.setVisibility(View.VISIBLE);
            holder.titre.setTextColor(context.getResources().getColor(R.color.myBlue));
            view.setBackground(context.getResources().getDrawable(R.drawable.pays_selected));
            holder.titre.setTextSize(20);
        }
        else {
            holder.tick.setVisibility(View.GONE);
            holder.titre.setTextColor(Color.BLACK);
            view.setBackground(context.getResources().getDrawable(R.drawable.pays));
            holder.titre.setTextSize(16);
        }
        try {

            InputStream is = context.getAssets().open(country.getCode()+".png");
            Drawable drawable = Drawable.createFromStream(is,null);
            holder.drapeau.setImageDrawable(drawable);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
