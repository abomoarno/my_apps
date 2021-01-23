package niamoro.annonces.pages.pages_accueil;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.activities.Accueil;
import niamoro.annonces.databases.managers.AnnonceManager;
import niamoro.annonces.utils.Utils;

public class Recehercher extends Fragment implements View.OnClickListener {

    private String type_service;
    private String type_operation;
    private String ville;

    private TextView maison;
    private TextView terrain;
    private TextView location;
    private TextView achat;
    private LinearLayout empty_page;
    private LinearLayout main_page;
    private TextView search;

    private LinearLayout rechercher;

    private List<String> villes;
    private Spinner cities;

    private Accueil context;
    private TextView titre;
    private TextView params;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = (Accueil) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search,null,false);

        empty_page = view.findViewById(R.id.empty);
        main_page = view.findViewById(R.id.main_page);
        maison = view.findViewById(R.id.maison);
        terrain = view.findViewById(R.id.terrain);
        location = view.findViewById(R.id.location);
        achat = view.findViewById(R.id.achat);
        cities = view.findViewById(R.id.ville);
        search = view.findViewById(R.id.searchText);
        titre = view.findViewById(R.id.titre);
        params = view.findViewById(R.id.params);

        rechercher = view.findViewById(R.id.search);

        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ville = villes.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        maison.setOnClickListener(this);
        terrain.setOnClickListener(this);
        location.setOnClickListener(this);
        achat.setOnClickListener(this);

        view.findViewById(R.id.search).setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");
        Typeface tf_bold = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_extrabold.ttf");
        maison.setTypeface(tf_bold);
        terrain.setTypeface(tf_bold);
        location.setTypeface(tf_bold);
        achat.setTypeface(tf_bold);
        search.setTypeface(tf_bold);
        titre.setTypeface(tf_bold);
        params.setTypeface(tf);

        villes = new AnnonceManager(context).getVilles(Utils.PAYS);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,villes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities.setAdapter(adapter);

        if (villes.isEmpty()) {
            main_page.setVisibility(View.GONE);
            empty_page.setVisibility(View.VISIBLE);
        }
        else {
            toggleTypeService(Utils.TYPE_MAISON);
            toggleTypeOperation(Utils.TYPE_LOCATION);
        }

        context.activeColor(1);
        Accueil.SELECTED_PAGE = 1;

        context.setTitre("Terrains - Maisons - Appartements - Studios ...");
        context.setSubTitre("Retrouvez les meilleurs deals autour de vous");
        context.setBackEnable(false);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.terrain:
                toggleTypeService(Utils.TYPE_TERRAIN);
                break;
            case R.id.maison:
                toggleTypeService(Utils.TYPE_MAISON);
                break;
            case R.id.location:
                toggleTypeOperation(Utils.TYPE_LOCATION);
                break;
            case R.id.achat:
                toggleTypeOperation(Utils.TYPE_VENTE);
                break;
            case R.id.search:

                Bundle bundle = new Bundle();
                bundle.putString("pays",Utils.PAYS);
                bundle.putString("type_bien",type_service);
                bundle.putString("type_operation",type_operation);
                bundle.putString("ville",ville);

                Fragment fragment = new Resultats();
                fragment.setArguments(bundle);
                context.setPage(fragment);
                break;
        }

    }

    private void toggleTypeService(String selected_service){

        if ((type_service != null) && type_service.equals(selected_service))
            return;
        type_service = selected_service;
        if (selected_service.equals(Utils.TYPE_MAISON)){
            maison.setTextColor(Color.WHITE);
            maison.setBackground(getResources().getDrawable(R.drawable.selected_bg));
            terrain.setTextColor(getResources().getColor(R.color.myBlue));
            terrain.setBackground(getResources().getDrawable(R.drawable.unselected_bg));

            location.setVisibility(View.VISIBLE);
        }
        else {


            terrain.setTextColor(Color.WHITE);
            terrain.setBackground(getResources().getDrawable(R.drawable.selected_bg));

            maison.setTextColor(getResources().getColor(R.color.myBlue));
            maison.setBackground(getResources().getDrawable(R.drawable.unselected_bg));

            location.setVisibility(View.INVISIBLE);
            toggleTypeOperation(Utils.TYPE_VENTE);

        }
    }

    private void toggleTypeOperation(String selected_operation){

        if ((type_operation !=null) && type_operation.equals(selected_operation))
            return;
        type_operation = selected_operation;
        if (selected_operation.equals(Utils.TYPE_LOCATION)){
            location.setTextColor(Color.WHITE);
            location.setBackground(getResources().getDrawable(R.drawable.selected_bg));

            achat.setTextColor(getResources().getColor(R.color.myBlue));
            achat.setBackground(getResources().getDrawable(R.drawable.unselected_bg));
        }
        else {

            achat.setTextColor(Color.WHITE);
            achat.setBackground(getResources().getDrawable(R.drawable.selected_bg));

            location.setTextColor(getResources().getColor(R.color.myBlue));
            location.setBackground(getResources().getDrawable(R.drawable.unselected_bg));

        }
    }
}
