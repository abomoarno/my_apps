package dev.casalov.projetetincel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.adaptors.DevisAdapter;
import dev.casalov.projetetincel.adaptors.RapportAdapter;
import dev.casalov.projetetincel.db_mangment.DevisManager;
import dev.casalov.projetetincel.db_mangment.RapportsManager;
import dev.casalov.projetetincel.utils.Devis;

public class ListeDevis extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private List<Devis> devis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_rapports);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setTitle("Liste des Devis");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,DetailsDevis.class);
        intent.putExtra("devis_id", devis.get(i).getDevis_id());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        devis = new DevisManager(this).getAllDevis();
        DevisAdapter adapter = new DevisAdapter(this, devis);
        listView.setAdapter(adapter);
    }
}