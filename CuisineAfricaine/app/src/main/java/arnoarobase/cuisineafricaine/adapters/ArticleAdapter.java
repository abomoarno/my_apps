package arnoarobase.cuisineafricaine.adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.classes.Article;
import arnoarobase.cuisineafricaine.classes.Photo;

public class ArticleAdapter extends ArrayAdapter<Article> {

    private Activity context;
    private List<Article> articles;
    public ArticleAdapter(@NonNull Activity context, List<Article> articles) {
        super(context, R.layout.recette_view, articles);
        this.context = context;
        this.articles = articles;
    }

    private static class Holder{
        ImageView image;
        TextView titre;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article article = articles.get(position);
        View view = convertView;
        final Holder holder;

        if (view == null){
            view = context.getLayoutInflater().inflate(R.layout.article_list_view, null, false);
            holder = new Holder();
            holder.image = view.findViewById(R.id.recette_image);
            holder.titre = view.findViewById(R.id.titre);
            view.setTag(holder);
        }
        else
            holder = (Holder)view.getTag();
        holder.titre.setText(article.getTitre());
        Picasso.get().load(article.getUrl());
        return view;
    }
}