package casalov.security.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import casalov.security.R;
import casalov.security.utils.Utils;

public class Inscription extends AppCompatActivity implements View.OnClickListener{

    private int typeClient = 1;
    private Button particulier;
    private Button entrreprise;

    private EditText phone;
    private EditText nom;
    private EditText prenom;
    private EditText adresse;
    private EditText code_postal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        particulier = findViewById(R.id.particulier);
        particulier.setOnClickListener(this);
        entrreprise = findViewById(R.id.entreprise);
        entrreprise.setOnClickListener(this);

        phone = findViewById(R.id.phone_number);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        adresse = findViewById(R.id.adress);
        code_postal = findViewById(R.id.code_postal);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
            actionBar.setTitle(R.string.inscription);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inscription,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.valider:
                if (validNom() && validAdresse() && validPhone() && validPostalCode() && validPreNom()) {
                    Intent intent = new Intent(this,FinalInscription.class);
                    intent.putExtra("nom",nom.getText().toString());
                    if (typeClient == 1)
                        intent.putExtra("prenom",prenom.getText().toString());
                    intent.putExtra("adresse",adresse.getText().toString());
                    intent.putExtra("postal_code",code_postal.getText().toString());
                    intent.putExtra("phone",phone.getText().toString());
                    intent.putExtra("type_client",typeClient);
                    startActivity(new Intent(intent));
                }
                break;
        }
        return true;
    }

    private void switchTypeClient(View view){

        if (typeClient == 2 && (view.getId() == R.id.particulier)){

            findViewById(R.id.prenom).setVisibility(View.VISIBLE);
            particulier.setTypeface(particulier.getTypeface(), Typeface.BOLD);
            particulier.setBackground(getResources().getDrawable(R.drawable.type_client_bg));

            entrreprise.setTypeface(entrreprise.getTypeface(), Typeface.NORMAL);
            entrreprise.setBackgroundColor(Color.TRANSPARENT);

            typeClient = 1;
        }
        else if (typeClient == 1 && (view.getId() == R.id.entreprise)){
            findViewById(R.id.prenom).setVisibility(View.GONE);
            entrreprise.setTypeface(entrreprise.getTypeface(), Typeface.BOLD);
            entrreprise.setBackground(getResources().getDrawable(R.drawable.type_client_bg));

            particulier.setTypeface(particulier.getTypeface(), Typeface.NORMAL);
            particulier.setBackgroundColor(Color.TRANSPARENT);

            typeClient = 2;
        }

    }

    @Override
    public void onClick(View view) {
        switchTypeClient(view);
    }

    private boolean validNom(){
        String typed = nom.getText().toString();
        boolean isCorrect = !typed.isEmpty();
        if (!isCorrect)
            nom.setBackground(getResources().getDrawable(R.drawable.editext_bg_error));
        else
            nom.setBackground(getResources().getDrawable(R.drawable.editext_bg));
        return isCorrect;
    }

    private boolean validPreNom(){
        String typed = prenom.getText().toString();
        boolean isCorrect = (!typed.isEmpty() || typeClient == 2);
        if (!isCorrect)
            prenom.setBackground(getResources().getDrawable(R.drawable.editext_bg_error));
        else
            prenom.setBackground(getResources().getDrawable(R.drawable.editext_bg));
        return isCorrect;
    }

    private boolean validPhone(){
        String typed = phone.getText().toString();
        boolean isCorrect = !typed.isEmpty();
        if (!isCorrect)
            phone.setBackground(getResources().getDrawable(R.drawable.editext_bg_error));
        else
            phone.setBackground(getResources().getDrawable(R.drawable.editext_bg));
        return isCorrect;
    }

    private boolean validAdresse(){
        String typed = adresse.getText().toString();
        boolean isCorrect = !typed.isEmpty();
        if (!isCorrect)
            adresse.setBackground(getResources().getDrawable(R.drawable.editext_bg_error));
        else
            adresse.setBackground(getResources().getDrawable(R.drawable.editext_bg));
        return isCorrect;
    }

    private boolean validPostalCode(){
        String typed = code_postal.getText().toString();
        boolean isCorrect = !typed.isEmpty();
        if (!isCorrect)
            code_postal.setBackground(getResources().getDrawable(R.drawable.editext_bg_error));
        else
            code_postal.setBackground(getResources().getDrawable(R.drawable.editext_bg));
        return isCorrect;
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.getBoolean(Utils.IS_CCONNECTED,false))
            finish();
    }
}