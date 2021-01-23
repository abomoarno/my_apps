package fallapro.landcrowdy.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.classes.Alert;

public class CreerALert extends AppCompatActivity implements View.OnClickListener{

    private Button acheter;
    private Button louer;
    private int operationSelected = 0;
    private int bienSelected = 0;

    private List<Button> buttonsBien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creer_alert);
        findViewById(R.id.creer_alerte).setOnClickListener(this);

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
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.creer_alerte_title));
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.creer_alerte:
                startActivity(new Intent(getApplicationContext(), AlerteDetails.class));
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