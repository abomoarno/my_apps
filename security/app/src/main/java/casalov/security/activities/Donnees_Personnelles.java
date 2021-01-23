package casalov.security.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import casalov.security.R;
import casalov.security.utils.Utils;

public class Donnees_Personnelles extends AppCompatActivity {

    private TextView nom;
    private TextView email;
    private TextView phone;
    private TextView adresse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voir_donnes_personnelles);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        nom = findViewById(R.id.nom_prenom);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.num_telephone);
        adresse = findViewById(R.id.adress);

        nom.setText(preferences.getString(Utils.USER_NAME,"username"));
        email.setText(preferences.getString(Utils.USER_MAIL,"email"));
        phone.setText(preferences.getString(Utils.USER_PHONE,"telephone"));
        adresse.setText(preferences.getString(Utils.USER_LOCATION,"adresse"));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
            actionBar.setTitle(R.string.donnes_personnelles_title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
        }
        return true;
    }
}
