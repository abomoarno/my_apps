package fallapro.landcrowdy.classes;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.activities.Resultats;

public class Annonce_Small_Adaptor extends ArrayAdapter<Annonce> {

    private ArrayList<Annonce> annonces;
    private Activity context;
    public Annonce_Small_Adaptor(Activity context, ArrayList<Annonce> annonces) {
        super(context, R.layout.annonce_view, annonces);
        this.context = context;
        this.annonces = annonces;
    }

    private static class ViewConatainer{

        TextView titre;
        TextView details;
        TextView status;
        TextView voir;
        ImageView like;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Annonce annonce = annonces.get(position);
        View rowView = convertView;
        final ViewConatainer conatainer;

        if (rowView == null){
            conatainer = new ViewConatainer();
            rowView = context.getLayoutInflater().inflate(R.layout.annonce_small_view,null,true);
            conatainer.titre = rowView.findViewById(R.id.annonce_titre);
            conatainer.details = rowView.findViewById(R.id.annonce_details);
            conatainer.status = rowView.findViewById(R.id.annonce_status);
            conatainer.like = rowView.findViewById(R.id.annonce_like);
            conatainer.voir = rowView.findViewById(R.id.voir_annonce);
            conatainer.voir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, fallapro.landcrowdy.activities.Annonce.class));
                }
            });
            rowView.setTag(conatainer);
        }
        else {
            conatainer = (ViewConatainer)rowView.getTag();
        }

        conatainer.voir.setText("Voir l\'annonces");
        conatainer.titre.setText("CFA 180,000");
        conatainer.details.setText("Nkoabang - 50 m²");

        return rowView;
    }
}
