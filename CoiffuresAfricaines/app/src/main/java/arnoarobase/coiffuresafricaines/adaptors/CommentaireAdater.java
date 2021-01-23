package arnoarobase.coiffuresafricaines.adaptors;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import arnoarobase.coiffuresafricaines.R;
import arnoarobase.coiffuresafricaines.calsses.Commentaire;

public class CommentaireAdater extends ArrayAdapter<Commentaire> {

    private Activity context;
    private List<Commentaire> commentaires;

    public CommentaireAdater(@NonNull Activity context, List<Commentaire> commentaires) {
        super(context, R.layout.commentaire_view, commentaires);

        this.context = context;
        this.commentaires = commentaires;
    }

    private class Holder{
        TextView message;
        TextView date;
        TextView utilisateur;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Holder holder;
        Commentaire commentaire = commentaires.get(position);

        if (view == null){
            holder = new Holder();
            view = context.getLayoutInflater().inflate(R.layout.commentaire_view, null, false);
            holder.message = view.findViewById(R.id.message);
            holder.utilisateur = view.findViewById(R.id.utilisateur);
            holder.date = view.findViewById(R.id.date);

            view.setTag(holder);
        }
        else {
            holder = (Holder)view.getTag();
        }

        return view;
    }
}
