package dev.casalov.projetetincel.pages.DetailsRapport;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.adaptors.AddElementAdapter;
import dev.casalov.projetetincel.adaptors.AddMaintenanceAdapter;
import dev.casalov.projetetincel.db_mangment.ElementsManager;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.native_db_managment.NativeElementsManager;
import dev.casalov.projetetincel.native_db_managment.NativeGammesManager;
import dev.casalov.projetetincel.utils.Maintenance;

public class AddElements extends Fragment {

    private List<String> elements_id = new ArrayList<>();
    private List<String> elements_names = new ArrayList<>();
    private ListView listView;
    private DetailsRapport activity;
    private AddElementAdapter adapter;
    private Bundle bundle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.liste_maintenance, null, false);
        view.findViewById(R.id.addMaintenance).setVisibility(View.GONE);
        listView = view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity = (DetailsRapport) getActivity();
        activity.setSubTitle("Ajouter des Caractéristiques");
        DetailsRapport.selectedPage = 5;
        bundle = getArguments();
        List<Map<String,String>> myElements = new ElementsManager(activity).getFromGamme(
                bundle.getString("gamme_id"),
                bundle.getString("project_id")
        );
        List<String> myIds = new ArrayList<>();

        for (Map<String, String> el:myElements){
            myIds.add(el.get("element_id"));
        }

        List<Map<String,String>> allGemmeEls = new NativeElementsManager(activity).getFromGamme(bundle.getString("gamme_id"));
        for (Map<String,String> el:allGemmeEls){
            if (!myIds.contains(el.get("element_id"))){
                elements_id.add(el.get("element_id"));
                elements_names.add(el.get("element_nom"));
            }
        }

        adapter = new AddElementAdapter(activity,elements_names,this);
        listView.setAdapter(adapter);
    }

    public void ajouterElement(int position){
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
            findViewById(R.id.ok).setOnClickListener(this);
            findViewById(R.id.annuler).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ok:
                    String name = valeur.getText().toString();
                    if (!name.isEmpty()) {
                        Map<String,String> element = new HashMap<>();
                        element.put("element_id", elements_id.get(position));
                        element.put("project_id", bundle.getString("project_id"));
                        element.put("gamme_id", bundle.getString("gamme_id"));
                        element.put("element_nom", elements_names.get(position));
                        element.put("element_valeur", name);
                        new ElementsManager(activity).insertElement(element);
                        elements_id.remove(position);
                        elements_names.remove(position);
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
