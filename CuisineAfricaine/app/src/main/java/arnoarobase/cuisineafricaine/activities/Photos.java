package arnoarobase.cuisineafricaine.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.classes.Recette;
import arnoarobase.cuisineafricaine.db_mangment.RecettesManager;
import arnoarobase.cuisineafricaine.pages.pages_photos.ListePhotos;

public class Photos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles);
        String recette_id = getIntent().getStringExtra("recette");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Photos");
            Recette recette = new RecettesManager(this).getRecette(recette_id);
            actionBar.setSubtitle(recette.getName());
        }

        ListePhotos listePhotos = new ListePhotos();
        Bundle bundle = new Bundle();
        bundle.putString("recette",recette_id);
        setPage(listePhotos);
    }

    private void setPage(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }
}
