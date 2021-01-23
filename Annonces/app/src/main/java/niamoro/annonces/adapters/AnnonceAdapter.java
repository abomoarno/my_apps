package niamoro.annonces.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.databases.managers.AnnonceManager;
import niamoro.annonces.utils.Annonce;
import niamoro.annonces.utils.Utils;

public class AnnonceAdapter extends ArrayAdapter<Annonce> {

    private List<Annonce> annonces;
    private Context context;

    public AnnonceAdapter(@NonNull Context context, @NonNull List<Annonce> objects) {
        super(context, R.layout.annonce_view, objects);
        this.annonces = objects;
        this.context = context;
    }

    private static class Holder{
        TextView titre;
        TextView ville;
        ImageView liked;
        ImageView image;
        TextView voir;
        TextView prix;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        final Holder holder;

        final Annonce annonce = annonces.get(position);

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.annonce_view,null,true);

            holder = new Holder();

            holder.image = view.findViewById(R.id.image);
            holder.titre = view.findViewById(R.id.titre);
            holder.ville = view.findViewById(R.id.ville);
            holder.liked = view.findViewById(R.id.liked);
            holder.voir = view.findViewById(R.id.voir_annonce);
            holder.prix = view.findViewById(R.id.prix);
            view.setTag(holder);
        }
        else
            holder = (Holder)view.getTag();

        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");
        Typeface tf_bold = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_extrabold.ttf");

        String title = annonce.getTitre().trim();
        title = title.substring(0,1).toUpperCase() + title.substring(1);
        holder.titre.setText(title);
        holder.titre.setTypeface(tf_bold);

        String ville = annonce.getVille().trim();
        holder.ville.setText(ville);
        holder.prix.setText(annonce.getPrix().trim());

        holder.prix.setTypeface(tf_bold);
        holder.ville.setTypeface(tf_bold);

        if (annonce.getTypeBien().equals(Utils.TYPE_MAISON)){
            holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.apartment_for_rent));
        }
        else {
            holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.land_plots));
        }

        if (!Utils.COMPACT_MODE && new Utils(context).isNetworkReachable()) {
            Picasso.get().load(annonce.getImage()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }

        if (annonce.getLiked() == 1)
            holder.liked.setImageDrawable(context.getResources().getDrawable(R.drawable.liked));
        else
            holder.liked.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_heart_button));

        holder.liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (annonce.getLiked() == 1){
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Retirer de mes favoris")
                            .setMessage("Souhaitez-vous vraiment retirer cette annonce de vos favoris ?\n\n" +
                                    "Vous pouvez sauvegarder les annonces qui vous intéressent dans les favoris afin de " +
                                    "les retrouver plus facilement")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    annonce.setLiked(0);
                                    holder.liked.setImageDrawable(context.getResources()
                                    .getDrawable(R.drawable.favorite_heart_button));
                                    new AnnonceManager(context).updateLiked(annonce);

                                    Toast.makeText(context,"Annonce retirée de mes favoris",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Non",null)
                            .show();
                }
                else {
                    annonce.setLiked(1);
                    holder.liked.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.liked));
                    new AnnonceManager(context).updateLiked(annonce);

                    Toast.makeText(context,"Annonce ajoutée à mes favoris",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
