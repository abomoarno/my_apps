package dev.casalov.projetetincel.pages.detailsDevis;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsDevis;

import static dev.casalov.projetetincel.activities.DetailsDevis.devis;

public class InfosGenerales extends Fragment implements View.OnClickListener {

    private EditText dateRedaction;
    private EditText dateRealisation;
    private EditText titre;
    private Bundle bundle;

    private EditText redacteur;
    private EditText redacteur_poste;

    private DetailsDevis activity;
    final Calendar myCalendar = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_devis_infos_generales,null,true);
        activity = (DetailsDevis) getActivity();
        dateRedaction = view.findViewById(R.id.date_redaction);
        dateRealisation = view.findViewById(R.id.date_realisation);
        titre = view.findViewById(R.id.intitule);
        redacteur = view.findViewById(R.id.redacteur);
        redacteur_poste = view.findViewById(R.id.poste_redacteur);

        bundle = getArguments();

        if (bundle != null){

            Log.e("DEVIS_ID", bundle.getString("devis_id"));

            dateRealisation.setText(devis.getDate_realisation());
            dateRedaction.setText(devis.getDate_document());
            titre.setText(devis.getNom());
            redacteur.setText(devis.getRedacteur());
            redacteur_poste.setText(devis.getRedacteur_poste());
        }

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
        }
    }

    @Override
    public void onClick(View view) {
        if (verification()) {
            devis.setNom(titre.getText().toString());
            devis.setDate_realisation(dateRealisation.getText().toString());
            devis.setDate_document(dateRedaction.getText().toString());
            devis.setRedacteur(redacteur.getText().toString()+";"+redacteur_poste.getText().toString());
            TabDetailsDevis.setPage(1);
        }
    }
    private boolean verification(){
        return !titre.getText().toString().isEmpty() &&
                !redacteur.getText().toString().isEmpty();
    }
}