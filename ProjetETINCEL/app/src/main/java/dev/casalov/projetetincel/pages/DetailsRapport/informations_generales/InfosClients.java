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

public class InfosClients extends Fragment {

    private DetailsRapport activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_infos_client,null,false);
        activity = (DetailsRapport) getActivity();

        TextView nom = view.findViewById(R.id.nom);
        TextView telephone = view.findViewById(R.id.phone);
        TextView mail = view.findViewById(R.id.mail);
        TextView adresse = view.findViewById(R.id.adresse);
        TextView ville = view.findViewById(R.id.ville);
        TextView entreprise = view.findViewById(R.id.entreprise);

        Bundle bundle = getArguments();
        String project_id = bundle.getString("project_id");

        Rapport rapport = new RapportsManager(activity).getRapport(project_id);

        String[] client = rapport.getClient_id().split(";");

        nom.setText(client[1]);
        telephone.setText(client[3]);
        mail.setText(client[5]);
        adresse.setText(client[6]);
        ville.setText(client[2]);
        entreprise.setText(client[7]);
        return view;
    }
}
