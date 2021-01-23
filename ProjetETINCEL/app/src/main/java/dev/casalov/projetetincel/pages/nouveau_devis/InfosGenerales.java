package dev.casalov.projetetincel.pages.nouveau_devis;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsDevis;
import dev.casalov.projetetincel.activities.NouveauDevis;
import dev.casalov.projetetincel.db_mangment.DevisManager;
import dev.casalov.projetetincel.pages.nouveau_devis.InfosClients;
import dev.casalov.projetetincel.utils.Devis;

public class InfosGenerales extends Fragment implements View.OnClickListener {

    private EditText dateRedaction;
    private EditText dateRealisation;
    private EditText titre;

    private EditText redacteur;
    private EditText redacteur_poste;

    private NouveauDevis activity;
    final Calendar myCalendar = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_devis_infos_generales,null,true);
        activity = (NouveauDevis) getActivity();
        dateRedaction = view.findViewById(R.id.date_redaction);
        dateRealisation = view.findViewById(R.id.date_realisation);
        titre = view.findViewById(R.id.intitule);
        redacteur = view.findViewById(R.id.redacteur);
        redacteur_poste = view.findViewById(R.id.poste_redacteur);

        view.findViewById(R.id.next).setOnClickListener(this);
        final DatePickerDialog.OnDateSetListener dateFinListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR,i);
                myCalendar.set(Calendar.MONTH,i1);
                myCalendar.set(Calendar.DAY_OF_MONTH,i2);
                updateDateFin();
            }
        };
        final DatePickerDialog.OnDateSetListener dateDebutListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR,i);
                myCalendar.set(Calendar.MONTH,i1);
                myCalendar.set(Calendar.DAY_OF_MONTH,i2);
                updateDateDebut();
            }
        };

        dateRedaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity,dateDebutListener,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dateRealisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity,dateFinListener,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        return view;
    }

    private void updateDateDebut(){
        String format = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
        dateRedaction.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateDateFin(){
        String format = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
        dateRealisation.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onStart() {
        super.onStart();

        if (activity != null){
            activity.setSubTitle("Informations Générales");
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (activity != null){
            activity.setSubTitle("Informations Générales");
            activity.activateFooter(1);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (activity != null){
            activity.desactivateFooter(1);
        }
    }

    @Override
    public void onClick(View view) {
        if (verification()) {
            Random random = new Random();
            String devis_id =
                    "DV-" +
                        random.nextInt(10) + "" +
                        random.nextInt(10) + "" +
                        random.nextInt(10) + "" +
                        random.nextInt(10) + "" +
                        random.nextInt(10) + "" +
                        random.nextInt(10) + "" +
                        random.nextInt(10) + "" +
                        random.nextInt(10) + "ER";
            NouveauDevis.devis = new Devis(devis_id,titre.getText().toString());
            NouveauDevis.devis.setDate_realisation(dateRealisation.getText().toString());
            NouveauDevis.devis.setDate_document(dateRedaction.getText().toString());
            NouveauDevis.devis.setRedacteur(redacteur.getText().toString()+";"+redacteur_poste.getText().toString());
            activity.setFragment(new InfosClients());
        }
    }
    private boolean verification(){
        return !titre.getText().toString().isEmpty() &&
                !dateRealisation.getText().toString().isEmpty() &&
                !dateRedaction.getText().toString().isEmpty() &&
                !redacteur.getText().toString().isEmpty();
    }
}