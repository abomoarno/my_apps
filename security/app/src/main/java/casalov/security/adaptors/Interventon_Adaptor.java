package casalov.security.adaptors;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import casalov.security.R;
import casalov.security.classes.Intervention;

public class Interventon_Adaptor extends ArrayAdapter<Intervention> {

    private List<Intervention> interventions;
    private Activity context;

    public Interventon_Adaptor(@NonNull Activity context, List<Intervention> interventions) {
        super(context, R.layout.intervention_view,interventions);
        this.interventions = interventions;
        this.context = context;
    }

    private static class Container{

        TextView motif;
        TextView lieu;
        TextView date;
        TextView statut;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Intervention intervention = interventions.get(position);
        Log.e(intervention.getId(),intervention.getMotif());
        Container container;
        View view = convertView;

        if (view == null){
            container = new Container();
            view = context.getLayoutInflater().inflate(R.layout.intervention_view,null,true);
            container.motif = view.findViewById(R.id.motif);
            container.lieu = view.findViewById(R.id.lieu);
            container.date = view.findViewById(R.id.date);
            container.statut = view.findViewById(R.id.statut);

            view.setTag(container);
        }
        else
            container = (Container)view.getTag();

        container.statut.setText(intervention.getStatut());
        container.lieu.setText(intervention.getLieu());
        container.date.setText(intervention.getDate());
        container.motif.setText(intervention.getMotif());

        return view;
    }
}
