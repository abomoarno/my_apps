package fallapro.landcrowdy.classes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.activities.Resultats;

public class Page_Rechercher extends Fragment implements View.OnClickListener {

    private Button acheter;
    private Button louer;

    private int operationSelected = 0;
    private int bienSelected = 0;

    private List<Button> buttonsBien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recherche,container,false);
        view.findViewById(R.id.voir_resultats).setOnClickListener(this);
        acheter = view.findViewById(R.id.acheter);
        acheter.setOnClickListener(this);
        louer = view.findViewById(R.id.louer);
        louer.setOnClickListener(this);
        Button appartement = view.findViewById(R.id.appartement);
        appartement.setOnClickListener(this);
        Button villa = view.findViewById(R.id.villa);
        villa.setOnClickListener(this);
        Button terrain = view.findViewById(R.id.terrain);
        terrain.setOnClickListener(this);
        operationSelected = 0;

        bienSelected = 0;
        buttonsBien = new ArrayList<>();
        buttonsBien.add(appartement);
        buttonsBien.add(villa);
        buttonsBien.add(terrain);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.voir_resultats:
                startActivity(new Intent(getActivity(),Resultats.class));
                break;
            case R.id.acheter:
            case R.id.louer:
                switchTypeOperation();
                break;
            case R.id.appartement:
                switchTypeBien(0);
                break;
            case R.id.villa:
                switchTypeBien(1);
                break;
            case R.id.terrain:
                switchTypeBien(2);
                break;
        }
    }


    private void switchTypeOperation(){

        if (operationSelected ==0){
            acheter.setBackground(getResources().getDrawable(R.drawable.creer_alerte_bg_white));
            acheter.setTextColor(Color.BLACK);
            louer.setBackground(getResources().getDrawable(R.drawable.creer_alerte_bg_gray));
            louer.setTextColor(Color.WHITE);
            operationSelected = 1;
        }
        else{

            acheter.setBackground(getResources().getDrawable(R.drawable.creer_alerte_bg_gray));
            acheter.setTextColor(Color.WHITE);
            louer.setBackground(getResources().getDrawable(R.drawable.creer_alerte_bg_white));
            louer.setTextColor(Color.BLACK);
            operationSelected = 0;
        }
    }

    private void switchTypeBien(int selected){
        if (bienSelected != selected){
            buttonsBien.get(bienSelected).setBackground(getResources().getDrawable(R.drawable.creer_alerte_bg_white));
            buttonsBien.get(bienSelected).setTextColor(Color.BLACK);
            buttonsBien.get(selected).setBackground(getResources().getDrawable(R.drawable.creer_alerte_bg_gray));
            buttonsBien.get(selected).setTextColor(Color.WHITE);
            bienSelected = selected;
        }
    }
}
