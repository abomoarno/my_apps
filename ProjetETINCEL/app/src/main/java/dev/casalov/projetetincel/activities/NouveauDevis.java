package dev.casalov.projetetincel.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.pages.nouveau_devis.InfosGenerales;
import dev.casalov.projetetincel.utils.Devis;

public class NouveauDevis extends AppCompatActivity {

    private TextView infos_gen_text;
    private TextView infos_client_text;
    private TextView charges_text;
    private TextView prestations_text;

    private ImageView infos_gen_image;
    private ImageView infos_client_image;
    private ImageView charges_image;
    private ImageView prestations_image;

    public static Devis devis;

    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nouveau_devis);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("Nouveau Devis");
        }
        setFragment(new InfosGenerales());

        infos_gen_text = findViewById(R.id.gen_text);
        infos_client_text = findViewById(R.id.client_text);
        charges_text = findViewById(R.id.charges_text);
        prestations_text = findViewById(R.id.prestation_text);

        infos_gen_image = findViewById(R.id.gen_image);
        charges_image = findViewById(R.id.charges_image);
        infos_client_image = findViewById(R.id.client_image);
        prestations_image = findViewById(R.id.prestation_image);
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }

    public void setSubTitle(String subTitle){
        if (actionBar != null){
            actionBar.setSubtitle(subTitle);
        }
    }

    public void desactivateFooter(int number){
        switch (number){
            case 1:
                infos_gen_text.setTextColor(Color.BLACK);
                infos_gen_image.setImageDrawable(getResources().getDrawable(R.drawable.settings));
                break;
            case 2:
                infos_client_text.setTextColor(Color.BLACK);
                infos_client_image.setImageDrawable(getResources().getDrawable(R.drawable.customer));
                break;
            case 3:
                charges_text.setTextColor(Color.BLACK);
                charges_image.setImageDrawable(getResources().getDrawable(R.drawable.technician));
                break;
            case 4:
                prestations_text.setTextColor(Color.BLACK);
                prestations_image.setImageDrawable(getResources().getDrawable(R.drawable.project));
                break;
        }
    }

    public void activateFooter(int number){
        switch (number){
            case 1:
                infos_gen_text.setTextColor(getResources().getColor(R.color.colorSelected));
                infos_gen_image.setImageDrawable(getResources().getDrawable(R.drawable.settings_selected));
                break;
            case 2:
                infos_client_text.setTextColor(getResources().getColor(R.color.colorSelected));
                infos_client_image.setImageDrawable(getResources().getDrawable(R.drawable.customer_selected));
                break;
            case 3:
                charges_text.setTextColor(getResources().getColor(R.color.colorSelected));
                charges_image.setImageDrawable(getResources().getDrawable(R.drawable.technician_selected));
                break;
            case 4:
                prestations_text.setTextColor(getResources().getColor(R.color.colorSelected));
                prestations_image.setImageDrawable(getResources().getDrawable(R.drawable.writting_selected));
                break;
        }
    }
}
