package dev.casalov.projetetincel.pages.DetailsRapport;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.adaptors.AppareilsAdaptor;
import dev.casalov.projetetincel.adaptors.SurveillerConformiteAdapter;
import dev.casalov.projetetincel.db_mangment.AppareilsMesureManager;
import dev.casalov.projetetincel.db_mangment.SurveillerConformiteManager;

public class AppareilsMesure extends Fragment implements View.OnClickListener {

    private List<String> elements_ids;
    private List<String> elements_name;
    private List<String> elements_series;
    private List<String> elements_validites;
    private DetailsRapport activity;
    private Bundle bundle;
    private String project_id;
    private ListView listView;
    private AppareilsAdaptor adapter;

    final Calendar myCalendar = Calendar.getInstance();

    public AppareilsMesure() {
        DetailsRapport.selectedPage = 4;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.liste_surveiller_conformite, null, false);
        listView = view.findViewById(R.id.listView);
        activity = (DetailsRapport) getActivity();
        view.findViewById(R.id.addElement).setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        bundle = getArguments();
        activity.setSubTitle(bundle.getString("title"));
        DetailsRapport.selectedPage = 4;
        project_id = bundle.getString("project_id");
        elements_ids = new ArrayList<>();
        elements_name = new ArrayList<>();
        elements_series = new ArrayList<>();
        elements_validites = new ArrayList<>();

        List<Map<String, String>> elements = new AppareilsMesureManager(activity).getFromProject(project_id);

        for (Map<String, String> element:elements){
            elements_ids.add(element.get("element_id"));
            elements_name.add(element.get("element_nom"));
            elements_series.add(element.get("element_serie"));
            elements_validites.add(element.get("element_validite"));
        }

        adapter = new AppareilsAdaptor(activity,elements_name, elements_series, this);
        listView.setAdapter(adapter);
    }

    public void supprimer(int position) {
        new SurveillerConformiteManager(activity).deleteElement(
                elements_ids.get(position),
                project_id
        );
        elements_ids.remove(position);
        elements_series.remove(position);
        elements_validites.remove(position);
        elements_name.remove(position);
        adapter.notifyDataSetChanged();
    }
    public void ajouter() {
        new ElementDialog(activity).show();
    }

    @Override
    public void onClick(View view) {
        ajouter();
    }

    private class ElementDialog extends Dialog implements View.OnClickListener{

        private EditText serie;
        private EditText nom;
        private EditText validite;

        ElementDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.appareils_dialog);
            setCancelable(false);
            serie = findViewById(R.id.valeur);
            nom = findViewById(R.id.designation);
            validite = findViewById(R.id.validite);
            findViewById(R.id.ok).setOnClickListener(this);
            findViewById(R.id.annuler).setOnClickListener(this);

            final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    myCalendar.set(Calendar.YEAR,i);
                    myCalendar.set(Calendar.MONTH,i1);
                    myCalendar.set(Calendar.DAY_OF_MONTH,i2);
                    updateDate();
                }
            };

            validite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(activity,dateListener,myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }

        private void updateDate(){
            String format = "dd/MM/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
            validite.setText(sdf.format(myCalendar.getTime()));
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ok:
                    String name = nom.getText().toString();
                    String value = serie.getText().toString();
                    if (!name.isEmpty()) {
                        String id = FirebaseDatabase.getInstance().getReference().push().getKey();
                        Map<String,String> element = new HashMap<>();
                        element.put("element_id", id);
                        element.put("project_id", bundle.getString("project_id"));
                        element.put("element_serie", value);
                        element.put("element_nom", name);
                        element.put("element_validite", validite.getText().toString());
                        new AppareilsMesureManager(activity).insertElement(element);
                        elements_ids.add(id);
                        elements_name.add(name);
                        elements_series.add(value);
                        elements_validites.add(validite.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.supprimer:
                    break;
            }
            dismiss();
        }
    }
}