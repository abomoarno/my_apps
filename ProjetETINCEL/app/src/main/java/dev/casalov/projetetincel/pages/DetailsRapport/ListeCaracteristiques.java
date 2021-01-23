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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.adaptors.CompartimentAdapter;
import dev.casalov.projetetincel.adaptors.ElementAdapter;
import dev.casalov.projetetincel.db_mangment.ElementsManager;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.native_db_managment.NativeCompartimentsManager;

public class ListeCaracteristiques extends Fragment implements View.OnClickListener {

    private List<String> elements_name = new ArrayList<>();
    private List<String> elements_id = new ArrayList<>();
    private List<String> elements_valeur = new ArrayList<>();
    private DetailsRapport activity;
    private Bundle bundle;
    private ElementAdapter adapter;
    private  ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.liste_caracteristiques, null, false);
        listView = view.findViewById(R.id.listView);
        view.findViewById(R.id.addElement).setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity = (DetailsRapport) getActivity();
        activity.setSubTitle("Liste des Caractéristiques");
        DetailsRapport.selectedPage = 4;
        bundle = getArguments();
        List<Map<String, String>> elements = new ElementsManager(
                activity).getFromGamme(bundle.getString("gamme_id"),
                bundle.getString("project_id"));

        elements_valeur.clear();
        elements_id.clear();
        elements_name.clear();

        for (Map<String,String> element: elements){
            elements_id.add(element.get("element_id"));
            elements_name.add(element.get("element_nom"));
            elements_valeur.add(element.get("element_valeur"));
        }
        adapter = new ElementAdapter(activity,elements_name,elements_valeur,this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = new AddElements();
        fragment.setArguments(bundle);
        activity.setFragment(fragment);
    }

    public void supprimer(int position) {
        new ElementsManager(activity).deleteElement(elements_id.get(position), bundle.getString("project_id"));
        elements_name.remove(position);
        elements_id.remove(position);
        adapter.notifyDataSetChanged();
    }

    public void modifier(int position) {
        new ElementDialog(activity,position).show();
    }

    private class ElementDialog extends Dialog implements View.OnClickListener{

        private EditText valeur;
        private int position;

        ElementDialog(@NonNull Context context, int position) {
            super(context);
            this.position = position;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.element_dialog);
            setCancelable(false);
            valeur = findViewById(R.id.valeur);
            valeur.setText(elements_valeur.get(position));
            findViewById(R.id.ok).setOnClickListener(this);
            findViewById(R.id.annuler).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ok:
                    String name = valeur.getText().toString();
                    if (!name.isEmpty()) {
                        elements_valeur.set(position,name);
                        Map<String,String> element = new HashMap<>();
                        element.put("element_id", elements_id.get(position));
                        element.put("project_id", bundle.getString("project_id"));
                        element.put("element_valeur", elements_valeur.get(position));
                        new ElementsManager(activity).updateElement(element);
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