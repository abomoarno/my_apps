package fallapro.landcrowdy.classes;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fallapro.landcrowdy.R;

public class Annonce_Adaptor extends ArrayAdapter<Annonce> {

    private ArrayList<Annonce> annonces;
    private Activity context;

    public Annonce_Adaptor(Activity context, ArrayList<Annonce> annonces) {
        super(context, R.layout.annonce_view, annonces);
        this.context = context;
        this.annonces = annonces;
    }

    private static class ViewConatainer{

        ImageView image;
        TextView prix;
        TextView details;
        TextView date;
        ImageView like;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int x = position;

        View rowView = convertView;
        final ViewConatainer conatainer;

        if (rowView == null){
            conatainer = new ViewConatainer();
            rowView = context.getLayoutInflater().inflate(R.layout.annonce_view,null,true);

            conatainer.image = rowView.findViewById(R.id.image_annonce);
            conatainer.like = rowView.findViewById(R.id.like);
            conatainer.details = rowView.findViewById(R.id.details_annonce);
            conatainer.date = rowView.findViewById(R.id.date_annonce);
            conatainer.prix = rowView.findViewById(R.id.prix_annonces);
            conatainer.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (annonces.get(x).getLiked() == 0) {
                        conatainer.like.setImageDrawable(context.getResources().getDrawable(R.drawable.like_full));
                        annonces.get(x).setLiked(1);
                    }
                    else if (annonces.get(x).getLiked() == 1){
                        conatainer.like.setImageDrawable(context.getResources().getDrawable(R.drawable.like));
                        annonces.get(x).setLiked(0);
                    }
                }
            });
            rowView.setTag(conatainer);
        }
        else {
            conatainer = (ViewConatainer)rowView.getTag();
        }
        if (annonces.get(position).getLiked() == 0) {
            conatainer.like.setImageDrawable(context.getResources().getDrawable(R.drawable.like));
        }
        else if (annonces.get(position).getLiked() == 1){
            conatainer.like.setImageDrawable(context.getResources().getDrawable(R.drawable.like_full));
        }

        return rowView;
    }
}
