package big.win.adapaters;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import big.win.R;
import big.win.classes.Pronostique;

public class Matchs_Adaptor extends ArrayAdapter<Pronostique> {
    private final List<Pronostique> matchs;
    private final Activity context;

    public Matchs_Adaptor(Activity context, List<Pronostique> matchs){
        super(context, R.layout.pronostic_view,matchs);

        this.context = context;
        this.matchs = matchs;
    }
    static class ViewContainer {
        TextView object;
        TextView name_A;
        TextView name_B;
        TextView scoreB;
        TextView scoreA;
        TextView hour;
        TextView pronostic;
        ImageView status;
        TextView cote;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pronostique p = matchs.get(position);
        ViewContainer viewContainer;
        View rowView = convertView;
        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            viewContainer = new ViewContainer();
            rowView = inflater.inflate(R.layout.pronostic_view, null, true);
            viewContainer.pronostic = rowView.findViewById(R.id.pronostic);
            viewContainer.object = rowView.findViewById(R.id.motif);
            viewContainer.name_A = rowView.findViewById(R.id.teamAName);
            viewContainer.name_B = rowView.findViewById(R.id.teamBName);
            viewContainer.scoreA = rowView.findViewById(R.id.scoreA);
            viewContainer.scoreB = rowView.findViewById(R.id.scoreB);
            viewContainer.hour = rowView.findViewById(R.id.hour);
            viewContainer.cote = rowView.findViewById(R.id.cote);
            viewContainer.status = rowView.findViewById(R.id.status);
            rowView.setTag(viewContainer);
        }
        else {
            viewContainer = (ViewContainer)rowView.getTag();
        }
        if (p.getResult().equals("0"))
            viewContainer.hour.setText(p.getHours().substring(11,16));
        else {
            viewContainer.hour.setText(p.getHours().substring(0, 11));
            viewContainer.scoreA.setText(p.getScoreA());
            viewContainer.scoreB.setText(p.getScoreB());
        }
        viewContainer.object.setText(p.getPays());
        viewContainer.name_A.setText(p.getName_teamA());
        viewContainer.name_B.setText(p.getName_teamB());
        viewContainer.pronostic.setText(p.getPronostic());
        viewContainer.cote.setText(p.getCote());

        switch (p.getResult()){
            case "1":
                rowView.findViewById(R.id.bar_cote).setBackgroundColor(Color.GREEN);
                rowView.findViewById(R.id.scores).setVisibility(View.VISIBLE);
                viewContainer.status.setImageDrawable(context.getResources().getDrawable(R.drawable.ok));
                break;
            case "-1":
                rowView.findViewById(R.id.bar_cote).setBackgroundColor(Color.RED);
                rowView.findViewById(R.id.scores).setVisibility(View.VISIBLE);
                viewContainer.status.setImageDrawable(context.getResources().getDrawable(R.drawable.bad));
                break;
            default:
                rowView.findViewById(R.id.scores).setVisibility(View.GONE);
                rowView.findViewById(R.id.bar_cote).setBackgroundColor(Color.BLUE);
                viewContainer.status.setImageDrawable(context.getResources().getDrawable(R.drawable.underway));
        }

        return rowView;
    }
}
