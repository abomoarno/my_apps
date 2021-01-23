package fallapro.landcrowdy.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.classes.Alert;
import fallapro.landcrowdy.dbManagment.AlertsManager;

public class AlertModifier extends AppCompatActivity implements View.OnClickListener{

    private Button acheter;
    private Button louer;
    private int operationSelected = 0;
    private int bienSelected = 0;
    private String alert_id;
    private Alert alert;
    private AlertsManager manager;

    private List<Button> buttonsBien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_modifier);
        findViewById(R.id.enregistrer_alert).setOnClickListener(this);
        findViewById(R.id.supprimer_alert).setOnClickListener(this);

        acheter = findViewById(R.id.acheter);
        acheter.setOnClickListener(this);
        louer = findViewById(R.id.louer);
        louer.setOnClickListener(this);
        Button appartement = findViewById(R.id.appartement);
        appartement.setOnClickListener(this);
        Button villa = findViewById(R.id.villa);
        villa.setOnClickListener(this);
        Button terrain = findViewById(R.id.terrain);
        terrain.setOnClickListener(this);
        operationSelected = 0;

        bienSelected = 0;
        buttonsBien = new ArrayList<>();
        buttonsBien.add(appartement);
        buttonsBien.add(villa);
        buttonsBien.add(terrain);

        manager = new AlertsManager(getApplicationContext());
        alert_id = getIntent().getStringExtra("alert_id");
        alert = manager.getAlert(alert_id);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Achat Appart Yaoundé");
        actionBar.setSubtitle(getString(R.string.modifier_alerte_subtitle));
        actionBar.setHomeAsUpIndicator(R.drawable.back);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.enregistrer_alert:
                startActivity(new Intent(getApplicationContext(),AlerteDetails.class));
                break;
            case R.id.supprimer_alert:
                startActivity(new Intent(getApplicationContext(),Alert_Supprimer.class));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
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