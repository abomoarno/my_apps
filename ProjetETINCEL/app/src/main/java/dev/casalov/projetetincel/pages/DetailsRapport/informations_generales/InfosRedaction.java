package dev.casalov.projetetincel.pages.DetailsRapport.informations_generales;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.db_mangment.RapportsManager;
import dev.casalov.projetetincel.utils.Rapport;

public class InfosRedaction extends Fragment {

    private DetailsRapport activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_infos_redaction,null, false);
        activity = (DetailsRapport) getActivity();
        TextView redacteur = view.findViewById(R.id.redacteur);
        TextView redacteur_date = view.findViewById(R.id.date_redacteur);
        TextView verificateur = view.findViewById(R.id.verificateur);
        TextView verificateur_date = view.findViewById(R.id.date_verificateur);
        TextView approbateur = view.findViewById(R.id.approbateur);
        TextView approbateur_date = view.findViewById(R.id.date_approbateur);

        Bundle bundle = getArguments();
        String project_id = bundle.getString("project_id");

        Rapport rapport = new RapportsManager(activity).getRapport(project_id);

        String[] redact = rapport.getRedacteur().split(";");
        String[] verif = rapport.getVerificateur().split(";");
        String[] approb = rapport.getApprobateur().split(";");

        redacteur.setText(redact[0]);
        redacteur_date.setText(redact[1]);

        verificateur.setText(verif[0]);
        verificateur_date.setText(verif[1]);

        approbateur_date.setText(approb[1]);
        approbateur.setText(approb[0]);
        return view;
    }

}
