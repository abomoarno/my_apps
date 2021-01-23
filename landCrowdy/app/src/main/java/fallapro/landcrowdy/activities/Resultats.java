package fallapro.landcrowdy.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.classes.Annonce;
import fallapro.landcrowdy.classes.Annonce_Adaptor;
import fallapro.landcrowdy.classes.Terrain;

public class Resultats extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<Annonce> annonces;
    private Annonce_Adaptor adaptor;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultats);

        annonces = new ArrayList<>(5);
        annonces = new ArrayList<>(5);
        annonces.add(new Terrain(1,"Maison cool"));
        annonces.add(new Terrain(1,"Maison cool"));
        annonces.add(new Terrain(1,"Maison cool"));
        annonces.add(new Terrain(1,"Maison cool"));
        annonces.add(new Terrain(1,"Maison cool"));
        annonces.add(new Terrain(1,"Maison cool"));
        adaptor = new Annonce_Adaptor(this, annonces);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Yaoundé");
        actionBar.setSubtitle("Achat,Appartement, max prix, max superficie");
        actionBar.setHomeAsUpIndicator(R.drawable.back);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        startActivity(new Intent(this, fallapro.landcrowdy.activities.Annonce.class));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }
}
