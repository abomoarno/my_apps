package dev.casalov.projetetincel.pages.DetailsRapport;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.adaptors.CompartimentAdapter;
import dev.casalov.projetetincel.adaptors.SurveillerConformiteAdapter;
import dev.casalov.projetetincel.db_mangment.ElementsManager;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.db_mangment.SurveillerConformiteManager;
import dev.casalov.projetetincel.native_db_managment.NativeCompartimentsManager;

public class SurveillerConformite extends Fragment implements View.OnClickListener {

    private List<String> elements_ids;
    private List<String> elements_name;
    private List<String> elements_valeur;
    private DetailsRapport activity;
    private Bundle bundle;
    private String project_id;
    private ListView listView;
    private SurveillerConformiteAdapter adapter;

    public SurveillerConformite() {
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
        elements_valeur = new ArrayList<>();

        List<Map<String, String>> elements = new SurveillerConformiteManager(activity).getFromProject(
                project_id,bundle.getString("type"));

        for (Map<String, String> element:elements){
            elements_ids.add(element.get("element_id"));
            elements_name.add(element.get("element_nom"));
            elements_valeur.add(element.get("element_valeur"));
        }

        adapter = new SurveillerConformiteAdapter(activity,elements_name, elements_valeur, this);
        listView.setAdapter(adapter);
    }

    public void supprimer(int position) {
        new SurveillerConformiteManager(activity).deleteElement(
                elements_ids.get(position),
                project_id
        );
        elements_ids.remove(position);
        elements_valeur.remove(position);
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

        private EditText valeur;
        private EditText nom;

        ElementDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.surveiller_conformite_dialog);
            setCancelable(false);
            valeur = findViewById(R.id.valeur);
            nom = findViewById(R.id.designation);
            findViewById(R.id.ok).setOnClickListener(this);
            findViewById(R.id.annuler).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ok:
                    String name = nom.getText().toString();
                    String value = valeur.getText().toString();
                    if (!name.isEmpty() && !value.isEmpty()) {

                        String id = FirebaseDatabase.getInstance().getReference().push().getKey();
                        Map<String,String> element = new HashMap<>();
                        element.put("element_id", id);
                        element.put("project_id", bundle.getString("project_id"));
                        element.put("element_valeur", value);
                        element.put("element_nom", name);
                        element.put("element_type", bundle.getString("type"));
                        new SurveillerConformiteManager(activity).insertElement(element);
                        elements_ids.add(id);
                        elements_name.add(name);
                        elements_valeur.add(value);
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