package dev.casalov.projetetincel.pages.nouveau_rapport;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.NouveauRapport;
import dev.casalov.projetetincel.utils.Rapport;

public class InfosGenerales extends Fragment implements View.OnClickListener {

    private EditText dateDebut;
    private EditText dateFin;
    private EditText titre;

    private EditText contact_site;
    private EditText contact_site_phone;

    private EditText charge_affaires;
    private EditText charge_affaires_phone;
    private EditText charge_travaux_phone;
    private EditText charge_travaux;

    private Spinner type_projet;
    private String type;
    private List<String> types;
    private NouveauRapport activity;
    final Calendar myCalendar = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infos_generales,null,true);

        activity = (NouveauRapport)getActivity();
        dateDebut = view.findViewById(R.id.dateDebut);
        dateFin = view.findViewById(R.id.dateFin);
        titre = view.findViewById(R.id.intitule);
        contact_site = view.findViewById(R.id.contact_site);
        contact_site_phone = view.findViewById(R.id.phone_contact_site);
        charge_affaires = view.findViewById(R.id.charge_affaires);
        charge_affaires_phone = view.findViewById(R.id.phone_charge_affaires);
        charge_travaux = view.findViewById(R.id.charge_travaux);
        charge_travaux_phone = view.findViewById(R.id.phone_charge_travaux);
        type_projet = view.findViewById(R.id.typeProjet);
        String[] types_proj = activity.getResources().getStringArray(R.array.types_projet);
        types = new ArrayList<>();
        types.addAll(Arrays.asList(types_proj));

        type_projet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = types.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        dateDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity,dateDebutListener,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dateFin.setOnClickListener(new View.OnClickListener() {
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
        dateDebut.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateDateFin(){
        String format = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
        dateFin.setText(sdf.format(myCalendar.getTime()));
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
            String rapport_id =
                    "RP-" +
                            random.nextInt(10) + "" +
                            random.nextInt(10) + "" +
                            random.nextInt(10) + "" +
                            random.nextInt(10) + "" +
                            random.nextInt(10) + "" +
                            random.nextInt(10) + "" +
                            random.nextInt(10) + "" +
                            random.nextInt(10) + "ER";
            NouveauRapport.rapport = new Rapport(rapport_id,titre.getText().toString());
            NouveauRapport.rapport.setDate_debut(dateDebut.getText().toString());
            NouveauRapport.rapport.setDate_fin(dateFin.getText().toString());
            NouveauRapport.rapport.setType(type);
            NouveauRapport.rapport.setCharge_travaux(charge_travaux.getText().toString() + ";"
                    + charge_travaux_phone.getText().toString());
            NouveauRapport.rapport.setCharge_affaires(charge_affaires.getText().toString() + ";"
                    + charge_affaires_phone.getText().toString());
            NouveauRapport.rapport.setContact_sur_site(contact_site.getText().toString() + ";"
                    + contact_site_phone.getText().toString());

            activity.setFragment(new InfosClients());
        }
    }
    private boolean verification(){
        return !titre.getText().toString().isEmpty();
    }
}