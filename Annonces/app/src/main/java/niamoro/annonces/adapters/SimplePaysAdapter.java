package niamoro.annonces.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class SimplePaysAdapter extends ArrayAdapter<Pays> {

    private List<Pays> pays;
    private Context context;

    public SimplePaysAdapter(@NonNull Context context, @NonNull List<Pays> objects) {
        super(context, R.layout.pays_view, objects);
        this.pays = objects;
        this.context = context;
    }

    private static class Holder{
        TextView titre;
        ImageView drapeau;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        Holder holder;

        Log.e("POSITION",""+position);

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.pays_view,null,true);

            holder = new Holder();
            holder.drapeau = view.findViewById(R.id.drapeau);
            holder.titre = view.findViewById(R.id.nom);

            view.setTag(holder);
        }
        else
            holder = (Holder)view.getTag();
        holder.titre.setText(pays.get(position).getNom());
        try {

            InputStream is = context.getAssets().open(pays.get(position).getCode()+".png");
            Drawable drawable = Drawable.createFromStream(is,null);
            holder.drapeau.setImageDrawable(drawable);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }
}
