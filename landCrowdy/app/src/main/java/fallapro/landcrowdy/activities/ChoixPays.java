package fallapro.landcrowdy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.classes.Pays;
import fallapro.landcrowdy.dbManagment.PaysManager;
import fallapro.landcrowdy.utils.Utils;

import static fallapro.landcrowdy.activities.Chargements.preferences;

public class ChoixPays extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choix_pays);

        findViewById(R.id.cm).setOnClickListener(this);
        findViewById(R.id.dc).setOnClickListener(this);
        findViewById(R.id.ci).setOnClickListener(this);
        findViewById(R.id.ga).setOnClickListener(this);
        findViewById(R.id.sn).setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.choix_pays_actionbar_title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.choix_pays,menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        String pays;
        switch (view.getId()){
            case R.id.cm:
                pays = "cm";
                break;
            case R.id.sn:
                pays ="sn";
                break;
            case R.id.ga:
                pays = "ga";
                break;
            case R.id.dc:
                pays = "rdc";
                break;
            case R.id.ci:
                pays = "ci";
                break;
            default:
                pays = "cm";
                break;
        }
        Pays p = null;
        try {
            p = new PaysManager(getApplicationContext()).getPays(pays);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (p != null){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Chargements.PAYS_NAME,p.getNom())
                    .putString(Chargements.PAYS_ISO,p.getIso())
                    .putString(Chargements.PAYS_LOGO,p.getLogo())
                    .putBoolean(Chargements.PAYS_SET,true)
                    .apply();
            Chargements.getPaysInfos();
        }
        if (Utils.PAYS_SET)
            super.onBackPressed();
        else
            startActivity(new Intent(getApplicationContext(),Presentation.class));
        finish();
    }
}
