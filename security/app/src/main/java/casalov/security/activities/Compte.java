package casalov.security.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import casalov.security.R;
import casalov.security.utils.Utils;

public class Compte extends AppCompatActivity implements View.OnClickListener {

    private TextView nom;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compte);

        findViewById(R.id.donnees_personnelles).setOnClickListener(this);
        findViewById(R.id.modifier_mail).setOnClickListener(this);
        findViewById(R.id.modifier_password).setOnClickListener(this);

        nom = findViewById(R.id.nom_prenom);
        email = findViewById(R.id.email);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        nom.setText(preferences.getString(Utils.USER_NAME,"Security"));
        email.setText(preferences.getString(Utils.USER_MAIL,"security@gmail.com"));
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
            actionBar.setTitle("Mon Compte");
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.donnees_personnelles:
                startActivity(new Intent(getApplicationContext(),Donnees_Personnelles.class));
                break;
            case R.id.modifier_mail:
                startActivity(new Intent(getApplicationContext(),Modifier_Mail.class));
                break;
            case R.id.modifier_password:
                startActivity(new Intent(getApplicationContext(),Modifier_Pass.class));
                break;
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