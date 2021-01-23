package casalov.security.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import casalov.security.R;
import casalov.security.classes.Entreprise;
import casalov.security.db_managment.EntreprisesManager;
import casalov.security.utils.Utils;

public class Accueil extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener{

    private NavigationView nav;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    private List<Entreprise> entreprises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        nav = findViewById(R.id.side_nav);
        nav.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        entreprises = new EntreprisesManager(this).getAllEntreprises();

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        findViewById(R.id.alarm_button).setOnClickListener(this);
        findViewById(R.id.alarm_button).setOnTouchListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.compte:
                startActivity(new Intent(getApplicationContext(),Compte.class));
                return true;
            default:
                return toggle.onOptionsItemSelected(item)
                        || super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        //startActivity(new Intent(getApplicationContext(),Inscription.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.compte:
                startActivity(new Intent(getApplicationContext(),Compte.class));
                break;
            case R.id.contactez_nous:
                break;
            case R.id.conditions:
                break;
            case R.id.logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                preferences.edit().remove(Utils.IS_CCONNECTED).apply();
                startActivity(new Intent(this, Chargement.class));
                finish();
                break;
        }

        drawer.closeDrawers();

        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        ImageView view1 = (ImageView)view;

        if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
        {
            view1.setImageDrawable(getResources().getDrawable(R.drawable.rec));
        }
        else if (MotionEvent.ACTION_UP == motionEvent.getAction())
        {
            view1.setImageDrawable(getResources().getDrawable(R.drawable.alarm));
            final String phone = "+34666777888";
            String entreprise = "Security Inc";

            AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this);
            builder.setMessage("Vous allez contacter l'entreprise " + entreprise + " pour signaler un problème !!");
            builder.setTitle("Appel d'urgence !!");
            builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new SendCall().execute();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }});
            builder.create();
            builder.show();

        }
        return false;
    }

    private class SendCall extends AsyncTask<String, Void, String> {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Accueil.this);

        @Override
        protected String doInBackground(String... strings) {
            String url = "http://www.afrimoov.com/casalovsecurity/newCall.php?"
                    + Utils.USER_ID + "=" + preferences.getString(Utils.USER_ID,"") + "&"
                    + "ENTREPRISE_ID" + "=" + "56546" + "&"
                    ;
            url = url.replace(" ","%20");
            URLConnection connection = null;
            try {
                connection = (new URL(url)).openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;

                while((line = br.readLine()) != null)
                    sb.append(line);
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String o) {
        }
    }
}
