package afrimoov.jefala.classes;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import afrimoov.jefala.R;
import afrimoov.jefala.activities.AnnonceDetails;


public class Annonce_Adaptor extends ArrayAdapter<Annonce> {

    private List<Annonce> annonces;
    private Activity context;
    public Annonce_Adaptor(Activity context,List<Annonce> annonces) {
        super(context, R.layout.annonce_view, annonces);
        this.context = context;
        this.annonces = annonces;
    }

    private static class ViewConatainer{

        TextView titre;
        TextView details;
        Button voir;
        ImageView like;
        ImageView illustration;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Annonce annonce = annonces.get(position);
        View rowView = convertView;
        final ViewConatainer conatainer;

        if (rowView == null){
            conatainer = new ViewConatainer();
            rowView = context.getLayoutInflater().inflate(R.layout.annonce_view,null,true);
            conatainer.titre = rowView.findViewById(R.id.price_annonce);
            conatainer.details = rowView.findViewById(R.id.annonce_details);
            conatainer.like = rowView.findViewById(R.id.like);
            conatainer.illustration = rowView.findViewById(R.id.illustration);
            conatainer.voir = rowView.findViewById(R.id.voir_annonce);
            conatainer.voir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, AnnonceDetails.class));
                }
            });
            rowView.setTag(conatainer);
        }
        else {
            conatainer = (ViewConatainer)rowView.getTag();
        }

        conatainer.voir.setText("Voir l\'annonce");
        conatainer.titre.setText("CFA 180,000");
        conatainer.details.setText("Nkoabang - 50 m²");
        return rowView;
    }
}