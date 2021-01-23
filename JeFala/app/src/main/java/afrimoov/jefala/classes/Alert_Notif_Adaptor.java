package afrimoov.jefala.classes;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import afrimoov.jefala.R;
import afrimoov.jefala.activities.AnnonceDetails;

public class Alert_Notif_Adaptor extends ArrayAdapter<Alerte>{

    private List<Alerte> alertes;
    private Activity context;

    public Alert_Notif_Adaptor(Activity context,List<Alerte> alertes){

        super(context, R.layout.notif_alert_view, alertes);
        this.alertes = alertes;
        this.context = context;
    }

    private static class ViewConatainer{

        TextView titre;
        Switch update;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Alerte alerte = alertes.get(position);
        View rowView = convertView;
        final ViewConatainer conatainer;

        if (rowView == null){
            conatainer = new ViewConatainer();
            rowView = context.getLayoutInflater().inflate(R.layout.notif_alert_view,null,true);
            conatainer.titre = rowView.findViewById(R.id.alertTitle);
            conatainer.update = rowView.findViewById(R.id.alert_update);
            conatainer.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alerte.setStatus(0);
                }
            });
            rowView.setTag(conatainer);
        }
        else {
            conatainer = (ViewConatainer)rowView.getTag();
        }

        conatainer.titre.setText("Terrain - Yaoundé");

        return rowView;
    }
}
