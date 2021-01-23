package afrimoov.ml.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import afrimoov.ml.R;
import afrimoov.ml.alerts.Pub_Interval;
import afrimoov.ml.pages.All_Direct_Tv;
import afrimoov.ml.pages.Home_Replay;
import afrimoov.ml.pages.More;
import afrimoov.ml.pages.Page_Accueil;
import afrimoov.ml.utilis.Utils;

public class Accueil extends AppCompatActivity implements View.OnClickListener {

    public ImageView accueil_image;
    public TextView accueil_text;

    public ImageView direct_image;
    public TextView direct_text;
    public ImageView replay_image;
    public TextView replay_text;
    public ImageView more_image;
    public TextView more_text;
    public int selectedPage;
    SharedPreferences preferences;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Pub_Interval.initAds(getApplicationContext());

        findViewById(R.id.direct).setOnClickListener(this);
        findViewById(R.id.replay).setOnClickListener(this);
        findViewById(R.id.more).setOnClickListener(this);
        findViewById(R.id.accueil).setOnClickListener(this);

        accueil_text = findViewById(R.id.accueil_text);
        accueil_image = findViewById(R.id.accueil_image);

        direct_text = findViewById(R.id.direct_text);
        direct_image = findViewById(R.id.direct_image);

        replay_text = findViewById(R.id.replay_text);
        replay_image = findViewById(R.id.replay_image);

        more_text = findViewById(R.id.more_text);
        more_image = findViewById(R.id.more_image);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/opensans_regular.ttf");

        accueil_text.setTypeface(tf);
        direct_text.setTypeface(tf);
        replay_text.setTypeface(tf);
        more_text.setTypeface(tf);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        actionBar = getSupportActionBar();
        if (actionBar != null){

            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
        }
        setFragment(new Page_Accueil());
        checkUpdate();
        reviewReminder(false);
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }

    public void activateFooter(int number){
        if (number == selectedPage)
            return;
        desactivateFooter(selectedPage);
        switch (number){
            case 1:
                accueil_text.setTextColor(getResources().getColor(R.color.menu_selected));
                accueil_image.setImageDrawable(getResources().getDrawable(R.drawable.home_selected));
                break;
            case 2:
                direct_text.setTextColor(getResources().getColor(R.color.menu_selected));
                direct_image.setImageDrawable(getResources().getDrawable(R.drawable.live_selected));
                break;
            case 3:
                replay_text.setTextColor(getResources().getColor(R.color.menu_selected));
                replay_image.setImageDrawable(getResources().getDrawable(R.drawable.replay_selected));
                break;
            case 4:
                more_text.setTextColor(getResources().getColor(R.color.menu_selected));
                more_image.setImageDrawable(getResources().getDrawable(R.drawable.more_selected));
                break;
        }
    }

    public void desactivateFooter(int number){
        switch (number){
            case 1:
                accueil_text.setTextColor(Color.WHITE);
                accueil_image.setImageDrawable(getResources().getDrawable(R.drawable.home_unselected));
                break;
            case 2:
                direct_text.setTextColor(Color.WHITE);
                direct_image.setImageDrawable(getResources().getDrawable(R.drawable.live_unselected));
                break;
            case 3:
            case 5:
                replay_text.setTextColor(Color.WHITE);
                replay_image.setImageDrawable(getResources().getDrawable(R.drawable.replay_unselected));
                break;
            case 4:
                more_text.setTextColor(Color.WHITE);
                more_image.setImageDrawable(getResources().getDrawable(R.drawable.more_unselected));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accueil:
                setFragment(new Page_Accueil());
                break;
            case R.id.direct:
                setFragment(new All_Direct_Tv());
                break;
            case R.id.replay:
                setFragment(new Home_Replay());
                break;
            case R.id.more:
                setFragment(new More());
                break;

        }
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
                setFragment(new Home_Replay());
                break;
            default:
                reviewReminder(true);
                break;
        }

    }

    public void toogleIndicator(Boolean b){
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(b);
            actionBar.setDisplayHomeAsUpEnabled(b);
            actionBar.setHomeButtonEnabled(b);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void checkUpdate(){
        int nbr_open = preferences.getInt(Utils.NUMBER_OPEN,1);

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

    private void reviewReminder(final boolean close){

        int nbr_open = preferences.getInt(Utils.NUMBER_OPEN,1);

        int review_modulo = preferences.getInt("review_modulo",2);

        if (nbr_open%review_modulo == 0){
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
                        preferences.edit().putInt("review_modulo",preferences.getInt("review_modulo",2)+20).apply();
                    }
                }
            });
            builder.setNegativeButton(getString(R.string.annuler), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (close)
                        closeApp();
                }});
            builder.create();
            builder.show();
        }
        else {
            if (close)
                closeApp();
        }
    }

    private void closeApp(){
        super.onBackPressed();
    }
}