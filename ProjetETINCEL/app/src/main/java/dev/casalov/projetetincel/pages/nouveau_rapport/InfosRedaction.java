package dev.casalov.projetetincel.pages.nouveau_rapport;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.activities.ListeRapports;
import dev.casalov.projetetincel.activities.NouveauRapport;
import dev.casalov.projetetincel.db_mangment.RapportsManager;

public class InfosRedaction extends Fragment implements View.OnClickListener {

    private NouveauRapport activity;

    private EditText redacteur;
    private EditText redacteur_date;
    private EditText verificateur;
    private EditText verificateur_date;
    private EditText approbateur;
    private EditText approbateur_date;

    final Calendar myCalendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infos_redaction,null,true);
        activity = (NouveauRapport) getActivity();
        view.findViewById(R.id.next).setOnClickListener(this);

        redacteur =view.findViewById(R.id.redacteur);
        redacteur_date =view.findViewById(R.id.date_redacteur);
        verificateur =view.findViewById(R.id.verificateur);
        verificateur_date =view.findViewById(R.id.date_verificateur);
        approbateur =view.findViewById(R.id.approbateur);
        approbateur_date =view.findViewById(R.id.date_approbateur);


        final DatePickerDialog.OnDateSetListener dateRedacteurListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR,i);
                myCalendar.set(Calendar.MONTH,i1);
                myCalendar.set(Calendar.DAY_OF_MONTH,i2);
                updateDateRedacteur();
            }
        };
        final DatePickerDialog.OnDateSetListener dateVerificateurListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR,i);
                myCalendar.set(Calendar.MONTH,i1);
                myCalendar.set(Calendar.DAY_OF_MONTH,i2);
                updateDateVerificacteur();
            }
        };
        final DatePickerDialog.OnDateSetListener dateApprobateurListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR,i);
                myCalendar.set(Calendar.MONTH,i1);
                myCalendar.set(Calendar.DAY_OF_MONTH,i2);
                updateDateApprobateur();
            }
        };

        redacteur_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity,dateRedacteurListener,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        verificateur_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity,dateVerificateurListener,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        approbateur_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity,dateApprobateurListener,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (activity != null){
            activity.setSubTitle("Informations Sur la Rédaction");
            activity.activateFooter(4);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (activity != null){
            activity.desactivateFooter(4);
        }
    }

    @Override
    public void onClick(View view){
        if (verify()) {
            NouveauRapport.rapport.setRedacteur(redacteur.getText().toString()+";"+redacteur_date.getText().toString());
            NouveauRapport.rapport.setVerificateur(verificateur.getText().toString()+";"+verificateur_date.getText().toString());
            NouveauRapport.rapport.setApprobateur(approbateur.getText().toString()+";"+approbateur_date.getText().toString());
            RapportsManager manager = new RapportsManager(activity);
            manager.insertRapport(NouveauRapport.rapport);
            Intent intent = new Intent(activity,ListeRapports.class);
            startActivity(intent);
            activity.finish();
        }
    }

    private boolean verify(){
        return true;
    }

    private void updateDateRedacteur(){
        String format = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
        redacteur_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateDateApprobateur(){
        String format = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
        approbateur_date.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateDateVerificacteur(){
        String format = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
        verificateur_date.setText(sdf.format(myCalendar.getTime()));
    }
}
