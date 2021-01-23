package niamoro.comedy.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import niamoro.comedy.R;
import niamoro.comedy.pages.Home_Comediens;
import niamoro.comedy.pages.More;
import niamoro.comedy.pages.Page_Accueil;
import niamoro.comedy.pages.Tendances;

import static niamoro.comedy.utilis.Utils.NUMBER_OPEN;

public class Accueil extends AppCompatActivity implements View.OnClickListener {

    public ImageView accueil_image;
    public TextView accueil_text;

    public ImageView comediens_image;
    public TextView comediens_text;

    public ImageView tendances_image;
    public TextView tendances_text;

    public ImageView more_image;
    public TextView more_text;

    public int selectedPage;

    SharedPreferences preferences;
    private ActionBar actionBar;

    private AdView mAdview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        mAdview = findViewById(R.id.adView);

        findViewById(R.id.tendances).setOnClickListener(this);
        findViewById(R.id.more).setOnClickListener(this);
        findViewById(R.id.accueil).setOnClickListener(this);
        findViewById(R.id.comediens).setOnClickListener(this);

        accueil_text = findViewById(R.id.accueil_text);
        accueil_image = findViewById(R.id.accueil_image);

        tendances_text = findViewById(R.id.tendances_text);
        tendances_image = findViewById(R.id.tendances_image);

        comediens_text = findViewById(R.id.comediens_text);
        comediens_image = findViewById(R.id.comediens_image);

        more_text = findViewById(R.id.more_text);
        more_image = findViewById(R.id.more_image);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/opensans_regular.ttf");

        accueil_text.setTypeface(tf);

        comediens_text.setTypeface(tf);
        more_text.setTypeface(tf);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        actionBar = getSupportActionBar();
        setFragment(new Page_Accueil());
        checkUpdate();


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        mAdview.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdview.setVisibility(View.VISIBLE);
            }
        });

        reviewReminder();
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }

    public void activateFooter(int number){
        switch (number){
            case 1:
                accueil_text.setTextColor(getResources().getColor(R.color.bg_yellow));
                accueil_image.setImageDrawable(getResources().getDrawable(R.drawable.home_selected));
                break;
            case 2:
                tendances_text.setTextColor(getResources().getColor(R.color.bg_yellow));
                tendances_image.setImageDrawable(getResources().getDrawable(R.drawable.fire_selected));
                break;
            case 3:
                comediens_text.setTextColor(getResources().getColor(R.color.bg_yellow));
                comediens_image.setImageDrawable(getResources().getDrawable(R.drawable.clown_selected));
                break;
            case 4:
                more_text.setTextColor(getResources().getColor(R.color.bg_yellow));
                more_image.setImageDrawable(getResources().getDrawable(R.drawable.more_selected));
                break;
        }
    }

    public void desactivateFooter(int number){
        switch (number){
            case 1:
                accueil_text.setTextColor(getResources().getColor(R.color.menu_unselected));
                accueil_image.setImageDrawable(getResources().getDrawable(R.drawable.home));
                break;
            case 2:
                tendances_text.setTextColor(getResources().getColor(R.color.menu_unselected));
                tendances_image.setImageDrawable(getResources().getDrawable(R.drawable.fire));
                break;
            case 3:
                comediens_text.setTextColor(getResources().getColor(R.color.menu_unselected));
                comediens_image.setImageDrawable(getResources().getDrawable(R.drawable.clown));
                break;
            case 4:
                more_text.setTextColor(getResources().getColor(R.color.menu_unselected));
                more_image.setImageDrawable(getResources().getDrawable(R.drawable.more));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accueil:
                setFragment(new Page_Accueil());
                break;
            case R.id.comediens:
                setFragment(new Home_Comediens());
                break;
            case R.id.tendances:
                setFragment(new Tendances());
                break;
            case R.id.more:
                setFragment(new More());
                break;

        }
    }

    public void toggleDesable(boolean b){
        findViewById(R.id.accueil).setEnabled(b);
        findViewById(R.id.comediens).setEnabled(b);
        findViewById(R.id.tendances).setEnabled(b);
        findViewById(R.id.more).setEnabled(b);
    }

    @Override
    public void onBackPressed() {
        switch (selectedPage){
            case 2:
            case 3:
            case 4:
                setFragment(new Page_Accueil());
                break;
            case 5:
                setFragment(new Home_Comediens());
                break;
            default:
                super.onBackPressed();
                break;
        }

    }

    public void toggleIndicator(Boolean b){
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(b);
        }
    }

    public void toogleIcone(boolean b){
        if (actionBar != null) {
            if(b) {
                actionBar.setDisplayUseLogoEnabled(true);
            }
            else
                actionBar.setDisplayUseLogoEnabled(false);
        }
    }

    public void toggleTitle(boolean b){
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(b);
        }
    }

    public void setTitre(String title){
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void checkUpdate(){
        int nbr_open = preferences.getInt(NUMBER_OPEN,1);

        int open_modulo = preferences.getInt("open_modulo",5);

        if (nbr_open%open_modulo == 0){

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot versionSnapshot = dataSnapshot.child("version");
                    if (versionSnapshot.exists()){
                        long versionCode = (long)versionSnapshot.child("code").getValue(Integer.class);
                        String versionMessage = versionSnapshot.child("message").getValue(String.class);
                        int myVersion;
                        try {
                            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
                            myVersion = packageInfo.versionCode;
                        } catch (PackageManager.NameNotFoundException e) {
                            myVersion = 10;
                        }
                        if (versionCode>myVersion){

                            AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this);
                            builder.setMessage(versionMessage);
                            builder.setTitle(getString(R.string.new_version));
                            builder.setPositiveButton(getString(R.string.update), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final String appName = getPackageName();
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                                                "market://details?id=" + appName)));
                                    }
                                    catch (Exception e){
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                                                "https://play.google.com/store/apps/details?id=" + appName)));
                                    }
                                }
                            });
                            builder.setNegativeButton(getString(R.string.annuler), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) { }});
                            builder.create();
                            builder.show();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void reviewReminder(){

        int nb_opens = preferences.getInt(NUMBER_OPEN,1);
        final int review_modulo = preferences.getInt("review_modulo",2);
        if ((nb_opens % review_modulo) !=0)
            return;
        AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this);
        builder.setMessage(getString(R.string.review));
        builder.setTitle(getString(R.string.like_app));
        builder.setPositiveButton(getString(R.string.noter), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String appName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                            "market://details?id=" + appName)));
                }
                catch (Exception e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                            "https://play.google.com/store/apps/details?id=" + appName)));
                }
                finally {
                    preferences.edit().putInt("review_modulo",review_modulo +11).apply();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.annuler), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }});
        builder.create();
        builder.show();
    }
}