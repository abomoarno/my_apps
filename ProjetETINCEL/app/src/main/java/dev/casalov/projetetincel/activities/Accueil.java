package dev.casalov.projetetincel.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dev.casalov.projetetincel.R;

public class Accueil extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        findViewById(R.id.rapports).setOnClickListener(this);
        findViewById(R.id.devis).setOnClickListener(this);
        findViewById(R.id.newRepport).setOnClickListener(this);
        findViewById(R.id.newDevis).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.rapports:
                startActivity(new Intent(this, ListeRapports.class));
                break;
            case R.id.devis:
                startActivity(new Intent(this, ListeDevis.class));
                break;
            case R.id.newRepport:
                startActivity(new Intent(this, NouveauRapport.class));
                break;
            case R.id.newDevis:
                startActivity(new Intent(this, NouveauDevis.class));
                break;
        }

    }
}