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
import fallapro.landcrowdy.activities.AlerteDetails;
import fallapro.landcrowdy.activities.Resultats;

public class Alert_Adaptor extends ArrayAdapter<Alert> {

    private ArrayList<Alert> alerts;
    private Activity context;
    public Alert_Adaptor(Activity context,  ArrayList<Alert> alerts) {
        super(context, R.layout.alert_view, alerts);
        this.context = context;
        this.alerts = alerts;
    }

    private static class ViewConatainer{

        TextView titre;
        TextView details;
        TextView status;
        TextView voir;
        ImageView editer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Alert alert = alerts.get(position);
        View rowView = convertView;
        final ViewConatainer conatainer;

        if (rowView == null){
            conatainer = new ViewConatainer();
            rowView = context.getLayoutInflater().inflate(R.layout.alert_view,null,true);
            conatainer.titre = rowView.findViewById(R.id.alert_titre);
            conatainer.details = rowView.findViewById(R.id.alert_details);
            conatainer.status = rowView.findViewById(R.id.alert_status);
            conatainer.voir = rowView.findViewById(R.id.voir_alert);
            conatainer.editer = rowView.findViewById(R.id.edit_alert);
            conatainer.voir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, Resultats.class));
                }
            });
            conatainer.editer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, AlerteDetails.class));
                }
            });
            rowView.setTag(conatainer);
        }
        else {
            conatainer = (ViewConatainer)rowView.getTag();
        }

        conatainer.voir.setText("Voir les annonces");
        conatainer.titre.setText("Achat Appart Yaoundé");
        conatainer.details.setText("Prix max de 290,000,000, une surface minimum de 150 m2, à " +
                "Yaoundé, quartier Bastos");

        return rowView;
    }
}
