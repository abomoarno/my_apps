package niamoro.annonces.adapters;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.activities.Accueil;
import niamoro.annonces.databases.managers.RechercheManager;
import niamoro.annonces.pages.pages_accueil.Resultats;
import niamoro.annonces.utils.Recherche;
import niamoro.annonces.utils.Utils;

public class RechercheAdapter extends ArrayAdapter<Recherche> {

    private List<Recherche> recherches;
    private Accueil context;
    public RechercheAdapter(@NonNull Accueil context, List<Recherche> objects) {
        super(context, R.layout.recherche_view, objects);
        this.context = context;
        this.recherches = objects;
    }

    private static class Holder{
        ImageView browse;
        ImageView checked;
        TextView since;
        ImageView delete;
        TextView titre;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final Holder holder;

        final Recherche recherche = recherches.get(position);

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.recherche_view,null,false);

            holder = new Holder();
            holder.browse = view.findViewById(R.id.browse);
            holder.checked = view.findViewById(R.id.checked);
            holder.titre = view.findViewById(R.id.recherche);
            holder.since = view.findViewById(R.id.since);
            holder.delete = view.findViewById(R.id.delete);

            view.setTag(holder);
        }
        else
            holder = (Holder)view.getTag();

        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");
        Typeface tf_bold = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_extrabold.ttf");

        holder.titre.setText(recherche.getTitre());
        holder.since.setText(recherche.getInterval());
        holder.titre.setTypeface(tf_bold);
        holder.since.setTypeface(tf_bold);

        if (recherche.getStatus() == 1){
            holder.checked.setImageDrawable(context.getResources().getDrawable(R.drawable.activated));
            holder.browse.setImageDrawable(context.getResources().getDrawable(R.drawable.search_activated));
            holder.titre.setTextColor(context.getResources().getColor(R.color.myBlue));
        }
        else{
            holder.checked.setImageDrawable(context.getResources().getDrawable(R.drawable.alarm));
            holder.browse.setImageDrawable(context.getResources().getDrawable(R.drawable.search_deactivated));
            holder.titre.setTextColor(Color.BLACK);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (view == holder.checked){
                  if (recherche.getStatus() == 1){
                      AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                      dialog
                          .setTitle("Désactiver les notifications")
                           .setMessage("Vous êtes sur le point de désactiver les notifications pour les nouvelles annonces " +
                                       "associées à cette recherche.\n\nSi vous souhaitez être le(la) premier(e) à recevoir les meilleurs deals " +
                                       "nous vous recommandons de garder vos notifications actives")
                        .setPositiveButton("Désactiver", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                recherche.setStatus(0);
                                //holder.checked.setImageDrawable(context.getResources().getDrawable(R.drawable.desactivated));
                                new RechercheManager(context).updateStatus(recherche);
                                Toast.makeText(context,"Alertes désactivées pour cette recherche",Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        })
                      .setNegativeButton("Annuler", null)
                      .setCancelable(false)
                      .show();

                  }
                  else{
                      recherche.setStatus(1);
                      //holder.checked.setImageDrawable(context.getResources().getDrawable(R.drawable.activated));
                      new RechercheManager(context).updateStatus(recherche);

                      if (!Utils.NOTIFICATIONS){
                          SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                          preferences.edit().putBoolean("notifications",true).apply();
                          Utils.NOTIFICATIONS = true;
                      }

                      Toast.makeText(context,"Alertes activées pour cette recherche",Toast.LENGTH_SHORT).show();

                      notifyDataSetChanged();
                  }
               }
               else if(view == holder.browse){
                   Bundle bundle = new Bundle();
                   bundle.putString("pays", recherche.getPays());
                   bundle.putString("type_bien",recherche.getTypeBien());
                   bundle.putString("type_operation",recherche.getTypeOperation());
                   bundle.putString("ville",recherche.getVille());
                   bundle.putString("titre","Trouver " + recherche.getTitre());
                   bundle.putBoolean("from",true);

                   Fragment fragment = new Resultats();
                   fragment.setArguments(bundle);
                   context.setPageHistory(fragment);
               }

               else {
                   AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                   dialog
                           .setTitle("Supprimer un élément !")
                           .setMessage("Vous êtes sur le point de supprimer cette recherche de votre historique.\n\nSachez que cette action est " +
                                   "irreversible")
                           .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {

                                   //holder.checked.setImageDrawable(context.getResources().getDrawable(R.drawable.desactivated));
                                   new RechercheManager(context).deleteRecherche(recherche);
                                   recherches.remove(recherche);
                                   Toast.makeText(context,"Elément supprimé !!",Toast.LENGTH_SHORT).show();
                                   notifyDataSetChanged();
                               }
                           })
                           .setNegativeButton("Annuler", null)
                           .setCancelable(false)
                           .show();
               }

            }
        };

        holder.checked.setOnClickListener(listener);
        holder.browse.setOnClickListener(listener);
        holder.delete.setOnClickListener(listener);

        return view;
    }
}