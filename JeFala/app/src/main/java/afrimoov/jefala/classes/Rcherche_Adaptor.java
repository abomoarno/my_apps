package afrimoov.jefala.classes;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import afrimoov.jefala.R;

public class Rcherche_Adaptor extends ArrayAdapter<Alerte>{

    private List<Alerte> alertes;
    private Activity context;

    public Rcherche_Adaptor(Activity context, List<Alerte> alertes){

        super(context, R.layout.notif_alert_view, alertes);
        this.alertes = alertes;
        this.context = context;
    }

    private static class ViewConatainer{

        TextView titre;
        ImageView cloche;
        TextView time;
        TextView type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Alerte alerte = alertes.get(position);
        View rowView = convertView;
        final ViewConatainer conatainer;

        if (rowView == null){
            conatainer = new ViewConatainer();
            rowView = context.getLayoutInflater().inflate(R.layout.recherce_view,null,true);
            conatainer.titre = rowView.findViewById(R.id.alertTitle);
            conatainer.time = rowView.findViewById(R.id.time);
            conatainer.type = rowView.findViewById(R.id.type);
            conatainer.cloche = rowView.findViewById(R.id.cloche);
            conatainer.cloche.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    conatainer.cloche.setImageDrawable(context.getResources().getDrawable(R.drawable.cloche_active));
                }
            });
            rowView.setTag(conatainer);
        }
        else {
            conatainer = (ViewConatainer)rowView.getTag();
        }

        conatainer.titre.setText("Terrain - Yaoundé");
        conatainer.type.setText("Louer");

        return rowView;
    }
}
