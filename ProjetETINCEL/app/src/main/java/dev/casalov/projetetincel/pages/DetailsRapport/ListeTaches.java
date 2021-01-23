package dev.casalov.projetetincel.pages.DetailsRapport;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.adaptors.TacheAdapter;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.utils.Operation;

public class ListeTaches extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private List<Operation> operations;
    private TacheAdapter adapter;

    private DetailsRapport activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.liste_taches,null,true);
        listView = view.findViewById(R.id.listView);
        activity = (DetailsRapport) getActivity();

        Bundle bundle = getArguments();
        operations = new OperationsManager(activity).getFromCompartiment(bundle.getString("compartiment_id"),
                bundle.getString("maintenance_id"),
                bundle.getString("project_id"));

        adapter = new TacheAdapter(activity,operations);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        new TacheDialog(activity,i).show();
    }

    private class TacheDialog extends Dialog implements View.OnClickListener{

        private TextView non_text;
        private TextView realiser_text;
        private TextView observer_text;

        private ImageView non_image;
        private ImageView realiser_image;
        private ImageView observer_image;

        private EditText observation;
        private int position;
        private int statut;
        private String myObservation;

        public TacheDialog(@NonNull Context context, int position) {
            super(context);
            this.position = position;
            statut = operations.get(position).isStatut();
            myObservation = operations.get(position).getObservations();
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.tache_dialog);
            setCancelable(false);

            non_text = findViewById(R.id.non_text);
            realiser_text = findViewById(R.id.realiser_text);
            observer_text = findViewById(R.id.observer_text);

            observer_image = findViewById(R.id.image_observer);
            realiser_image = findViewById(R.id.image_realiser);
            non_image = findViewById(R.id.image_non_realiser);
            observation = findViewById(R.id.observation);

            activate(statut);
            observation.setText(operations.get(position).getObservations());
            findViewById(R.id.annuler).setOnClickListener(this);
            findViewById(R.id.observer).setOnClickListener(this);
            findViewById(R.id.not_done).setOnClickListener(this);
            findViewById(R.id.realiser).setOnClickListener(this);
            findViewById(R.id.ok).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.observer:
                    statut = 0;
                    activate(0);
                    break;
                case R.id.not_done:
                    statut = -1;
                    activate(-1);
                    break;
                case R.id.realiser:
                    statut = 1;
                    activate(1);
                    break;
                case R.id.annuler:
                    dismiss();
                    break;
                case R.id.ok:
                    if (statut != operations.get(position).isStatut() || !myObservation.equals(observation.getText().toString())){
                        operations.get(position).setStatut(statut);
                        operations.get(position).setObservations(observation.getText().toString());
                        new OperationsManager(activity).updateOperation(operations.get(position));
                        adapter.notifyDataSetChanged();
                    }
                    dismiss();
                    break;
            }
        }

        private void activate(int pos){
            switch (pos){
                case -1:
                    non_image.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    non_text.setTypeface(non_text.getTypeface(), Typeface.BOLD);
                    desactivate(1);
                    desactivate(0);
                    break;
                case 0:
                    observer_image.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    observer_text.setTypeface(observer_text.getTypeface(), Typeface.BOLD);
                    desactivate(-1);
                    desactivate(1);
                    break;
                case 1:
                    realiser_image.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    realiser_text.setTypeface(realiser_text.getTypeface(), Typeface.BOLD);
                    desactivate(0);
                    desactivate(-1);
                    break;
            }
        }

        private void desactivate(int pos){
            switch (pos){
                case -1:
                    non_image.setImageDrawable(getResources().getDrawable(R.drawable.check));
                    non_text.setTypeface(null, Typeface.NORMAL);
                    break;
                case 0:
                    observer_image.setImageDrawable(getResources().getDrawable(R.drawable.check));
                    observer_text.setTypeface(null, Typeface.NORMAL);
                    break;
                case 1:
                    realiser_image.setImageDrawable(getResources().getDrawable(R.drawable.check));
                    realiser_text.setTypeface(null, Typeface.NORMAL);
                    break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.setSubTitle("Liste des Taches");
        DetailsRapport.selectedPage = 6;
    }
}
