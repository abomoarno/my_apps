package niamoro.annonces.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import niamoro.annonces.R;
import niamoro.annonces.databases.managers.AnnonceManager;
import niamoro.annonces.pages.pages_accueil.MesFavoris;
import niamoro.annonces.pages.pages_accueil.MesRecherches;
import niamoro.annonces.pages.pages_accueil.More;
import niamoro.annonces.pages.pages_accueil.Recehercher;
import niamoro.annonces.utils.Annonce;

public class Accueil extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;

    public ImageView accueil_image;
    public TextView accueil_text;
    public ImageView favoris_image;
    public TextView favoris_text;
    public ImageView more_image;
    public TextView more_text;
    public ImageView recherches_image;
    public TextView recherches_text;

    private SharedPreferences preferences;

    private AdView mAdview;

    public static int SELECTED_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putInt("opens",preferences.getInt("opens",0)+1).apply();
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/opensans_regular.ttf");

        accueil_text = findViewById(R.id.accueil_text);
        accueil_image = findViewById(R.id.accueil_image);

        favoris_text = findViewById(R.id.favoris_text);
        favoris_image = findViewById(R.id.favoris_image);

        more_text = findViewById(R.id.more_text);
        more_image = findViewById(R.id.more_image);

        recherches_text = findViewById(R.id.recherches_text);
        recherches_image = findViewById(R.id.recherches_image);

        accueil_text.setTypeface(tf);
        favoris_text.setTypeface(tf);
        more_text.setTypeface(tf);
        recherches_text.setTypeface(tf);

        findViewById(R.id.accueil).setOnClickListener(this);
        findViewById(R.id.recherches).setOnClickListener(this);
        findViewById(R.id.favoris).setOnClickListener(this);
        findViewById(R.id.more).setOnClickListener(this);

        actionBar = getSupportActionBar();

        mAdview = findViewById(R.id.adView);
        mAdview.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdview.setVisibility(View.VISIBLE);
            }
        });

        mAdview.loadAd(new AdRequest.Builder().build());
        setPage(new Recehercher());

        if (getIntent().getStringExtra("id") != null){
            Annonce annonce = new AnnonceManager(this).getAnnonce(getIntent().getStringExtra("id"));
            preferences.edit().putInt("anneces_vues",preferences.getInt("annonces_vues",0)+1).apply();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(annonce.getLien()));
            startActivity(intent);
        }

        reviewReminder();

    }

    public void setPage(Fragment fragment){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.commit();

    }
    public void setPageHistory(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdview.resume();
    }

    public void setTitre(String title){
        actionBar.setTitle(title);
    }
    public void setSubTitre(String subtitre){
        actionBar.setSubtitle(subtitre);
    }

    public void setBackEnable(boolean action){
        actionBar.setDisplayHomeAsUpEnabled(action);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void activeColor(int fragment){

        if (fragment == SELECTED_PAGE)
            return;
        desactiveColor(SELECTED_PAGE);
        switch (fragment){

            case 1:
            case 5:
                accueil_text.setVisibility(View.VISIBLE);
                accueil_image.setImageDrawable(getResources().getDrawable(R.drawable.search_activated));
                break;
            case 2:
                recherches_text.setVisibility(View.VISIBLE);
                recherches_image.setImageDrawable(getResources().getDrawable(R.drawable.mes_recherches_activated));
                break;
            case 3:
                favoris_text.setVisibility(View.VISIBLE);
                favoris_image.setImageDrawable(getResources().getDrawable(R.drawable.favories_activated));
                break;
            case 4:
                more_text.setVisibility(View.VISIBLE);
                more_image.setImageDrawable(getResources().getDrawable(R.drawable.more_activated));
                break;
        }
    }

    private void desactiveColor(int fragment){

        switch (fragment){

            case 1:
            case 5:
            case 10:
                accueil_text.setVisibility(View.GONE);
                accueil_image.setImageDrawable(getResources().getDrawable(R.drawable.search_deactivated));
                break;
            case 2:
                recherches_text.setVisibility(View.GONE);
                recherches_image.setImageDrawable(getResources().getDrawable(R.drawable.mes_recherches_deactivated));
                break;
            case 3:
                favoris_text.setVisibility(View.GONE);
                favoris_image.setImageDrawable(getResources().getDrawable(R.drawable.favories_deactivated));
                break;
            case 4:
                more_text.setVisibility(View.GONE);
                more_image.setImageDrawable(getResources().getDrawable(R.drawable.more_deactivated));
                break;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.accueil:
                setPage(new Recehercher());
                break;
            case R.id.recherches:
                setPage(new MesRecherches());
                break;
            case R.id.favoris:
                setPage(new MesFavoris());
                break;
            case R.id.more:
                setPage(new More());
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (SELECTED_PAGE == 1 || SELECTED_PAGE == 10)
            super.onBackPressed();
        else
            setPage(new Recehercher());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdview.pause();
    }

    private void reviewReminder() {

        int nbr_open = preferences.getInt("opens", 1);

        int review_modulo = preferences.getInt("review_modulo", 2);

        if (nbr_open % review_modulo == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this);
            builder.setMessage("Vos avis nous aident à améliorer l'application");
            builder.setTitle("Noter l'application");
            builder.setPositiveButton("Noter Maintenant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String appName = getPackageName();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                                "market://details?id=" + appName)));
                    } catch (Exception e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                                "https://play.google.com/store/apps/details?id=" + appName)));
                    } finally {
                        preferences.edit().putInt("review_modulo", preferences.getInt("review_modulo", 2) + 20).apply();
                    }
                }
            });
            builder.setNegativeButton("Annuler", null).show();
        }
    }
}