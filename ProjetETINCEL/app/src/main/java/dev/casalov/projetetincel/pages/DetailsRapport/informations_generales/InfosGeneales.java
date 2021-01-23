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

public class InfosGeneales extends Fragment {

    private DetailsRapport activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_infos_generales, null, false);
        activity = (DetailsRapport) getActivity();

        TextView type = view.findViewById(R.id.typeProjet);
        TextView date_d = view.findViewById(R.id.dateDebut);
        TextView date_f = view.findViewById(R.id.dateFin);
        TextView contact_site = view.findViewById(R.id.contact_site);
        TextView contact_site_phone = view.findViewById(R.id.phone_contact_site);
        TextView charge_affaires = view.findViewById(R.id.charge_affaires);
        TextView charge_affaires_phone = view.findViewById(R.id.phone_charge_affaires);
        TextView charge_traveaux_phone = view.findViewById(R.id.phone_charge_travaux);
        TextView charge_traveaux = view.findViewById(R.id.charge_travaux);

        Bundle bundle = getArguments();
        String project_id = bundle.getString("project_id");

        Rapport rapport = new RapportsManager(activity).getRapport(project_id);

        type.setText(rapport.getType());
        date_d.setText(rapport.getDate_debut());
        date_f.setText(rapport.getDate_fin());

        String[] concatact = rapport.getContact_sur_site().split(";");
        String[] charge_aff = rapport.getCharge_affaires().split(";");
        String[] charge_tra = rapport.getCharge_travaux().split(";");

        contact_site.setText(concatact[0]);
        contact_site_phone.setText(concatact[1]);

        charge_affaires.setText(charge_aff[0]);
        charge_affaires_phone.setText(charge_aff[1]);

        charge_traveaux.setText(charge_tra[0]);
        charge_traveaux_phone.setText(charge_tra[1]);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.setSubTitle("Généralités");
    }
}
