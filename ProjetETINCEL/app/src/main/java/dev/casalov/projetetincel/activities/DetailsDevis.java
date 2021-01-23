package dev.casalov.projetetincel.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.db_mangment.DevisManager;
import dev.casalov.projetetincel.pages.detailsDevis.AccueilDetailsDevis;
import dev.casalov.projetetincel.utils.Devis;

public class DetailsDevis extends AppCompatActivity {
    private ActionBar actionBar;
    public static Devis devis;
    public static int selectedDevis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_devis);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        devis = new DevisManager(this).getDevis(getIntent().getStringExtra("devis_id"));
        setTitle(devis.getNom());
        selectedDevis = 1;
        Fragment fragment = new AccueilDetailsDevis();
        Bundle bundle = new Bundle();
        bundle.putString("devis_id",getIntent().getStringExtra("devis_id"));
        fragment.setArguments(bundle);
        setFragment(fragment);
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        if (selectedDevis != 1)
            ft.addToBackStack(null);
        ft.commit();
    }

    public void setTitle(String title){
        if (actionBar != null){
            actionBar.setTitle(title);
        }
    }
    public void setSubTitle(String subTitle){
        if (actionBar != null){
            actionBar.setSubtitle(subTitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

}