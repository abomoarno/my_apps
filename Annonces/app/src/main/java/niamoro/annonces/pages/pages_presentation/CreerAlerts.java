package niamoro.annonces.pages.pages_presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import niamoro.annonces.R;
import niamoro.annonces.activities.Chargement;
import niamoro.annonces.activities.Presentation;
import niamoro.annonces.utils.Utils;

public class CreerAlerts  extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.creer_alert,null,false);

        Typeface tf = Typeface.createFromAsset(getResources().getAssets(),"fonts/opensans_extrabold.ttf");

        TextView titre = view.findViewById(R.id.titre);
        TextView message = view.findViewById(R.id.message);
        Button commencer = view.findViewById(R.id.commencer);

        commencer.setOnClickListener(this);

        commencer.setTypeface(tf);
        titre.setTypeface(tf);
        message.setTypeface(tf);

        return view;
    }

    @Override
    public void onClick(View view) {

        Presentation activity = (Presentation)getActivity();
        if (activity !=null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
            preferences.edit().putBoolean("presentaion",true).apply();
            Utils.PRESENTATION = true;
            startActivity(new Intent(activity, Chargement.class));
            activity.finish();
        }
    }
}
