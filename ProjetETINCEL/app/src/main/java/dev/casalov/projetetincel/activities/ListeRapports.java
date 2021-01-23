package dev.casalov.projetetincel.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.adaptors.RapportAdapter;
import dev.casalov.projetetincel.db_mangment.RapportsManager;
import dev.casalov.projetetincel.utils.Rapport;

public class ListeRapports extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private List<Rapport> rapports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_rapports);

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setTitle("Liste des Rapports");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,DetailsRapport.class);
        intent.putExtra("project_id",rapports.get(i).getRapport_id());
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
        rapports = new RapportsManager(this).getAllRapports();
        RapportAdapter adapter = new RapportAdapter(this,rapports);
        listView.setAdapter(adapter);
    }
}