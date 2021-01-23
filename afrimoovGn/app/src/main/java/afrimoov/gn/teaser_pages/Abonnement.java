package afrimoov.gn.teaser_pages;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import afrimoov.gn.R;
import afrimoov.gn.utilis.Utils;

public class Abonnement extends Fragment implements View.OnClickListener{

    private String sku;
    private int selectedPlan = 2;

    private LinearLayout plan1;
    private LinearLayout plan2;
    private LinearLayout plan3;

    private TextView basic;
    private TextView populaire;
    private TextView afrimoov;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.abonnement,null,true);
        sku = Utils.ABONNEMENT_6_MOIS;
        Button supprimer = view.findViewById(R.id.supprimer);
        plan1 = view.findViewById(R.id.abonnement_1_mois);
        plan2 = view.findViewById(R.id.abonnement_6_mois);
        plan3 = view.findViewById(R.id.abonnement_12_mois);

        basic = view.findViewById(R.id.basic);
        populaire = view.findViewById(R.id.populaire);
        afrimoov = view.findViewById(R.id.afrimoov);

        plan1.setOnClickListener(this);
        plan2.setOnClickListener(this);
        plan3.setOnClickListener(this);

        supprimer.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.supprimer:
                break;
            case R.id.abonnement_1_mois:
                sku = Utils.ABONNEMENT_1_MOIS;
                selectPlans(1);
                break;
            case R.id.abonnement_6_mois:
                sku = Utils.ABONNEMENT_6_MOIS;
                selectPlans(2);
                break;
            case R.id.abonnement_12_mois:
                sku = Utils.ABONNEMENT_12_MOIS;
                selectPlans(3);
                break;
        }
    }




    private void selectPlans(int plan){
        deselectPlans(selectedPlan);
        selectedPlan = plan;
        switch (plan){
            case 1:
                plan1.setBackground(getResources().getDrawable(R.drawable.bg_abonnement_selected));
                basic.setTextColor(Color.WHITE);
                basic.setBackgroundResource(R.drawable.bg_discover);
                break;
            case 2:
                plan2.setBackground(getResources().getDrawable(R.drawable.bg_abonnement_selected));
                populaire.setTextColor(Color.WHITE);
                populaire.setBackgroundResource(R.drawable.bg_discover);
                break;
            case 3:
                plan3.setBackground(getResources().getDrawable(R.drawable.bg_abonnement_selected));
                afrimoov.setTextColor(Color.WHITE);
                afrimoov.setBackgroundResource(R.drawable.bg_discover);
                break;
        }
    }
    private void deselectPlans(int plan){
        switch (plan){
            case 1:
                plan1.setBackground(getResources().getDrawable(R.drawable.bg_abonnement));
                basic.setTextColor(Color.BLACK);
                basic.setBackgroundResource(R.drawable.bg_abonnement);
                break;
            case 2:
                plan2.setBackground(getResources().getDrawable(R.drawable.bg_abonnement));
                populaire.setTextColor(Color.BLACK);
                populaire.setBackgroundResource(R.drawable.bg_abonnement);
                break;
            case 3:
                plan3.setBackground(getResources().getDrawable(R.drawable.bg_abonnement));
                afrimoov.setTextColor(Color.BLACK);
                afrimoov.setBackgroundResource(R.drawable.bg_abonnement);
                break;
        }
    }
}