package casalov.security.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import casalov.security.R;
import casalov.security.utils.Utils;

import static casalov.security.utils.Utils.CLENT_ENTREPRISE;
import static casalov.security.utils.Utils.CLENT_PARTICULIER;
import static casalov.security.utils.Utils.USER_LOCATION;
import static casalov.security.utils.Utils.USER_MAIL;
import static casalov.security.utils.Utils.USER_NAME;
import static casalov.security.utils.Utils.USER_PHONE;
import static casalov.security.utils.Utils.USER_TYPE;

public class FinalInscription extends AppCompatActivity {
    private EditText pass;
    private EditText email;
    private EditText pass_confirm;
    private FirebaseAuth mAuth;

    private String nom;
    private String code_postal;
    private String prenom;
    private String adresse;
    private String phone;
    int type_client;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_inscription);
        pass = findViewById(R.id.pass);
        email = findViewById(R.id.email);
        pass_confirm = findViewById(R.id.confirm_pass);
        mAuth = FirebaseAuth.getInstance();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
            actionBar.setTitle(R.string.inscription_suite);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        if (getIntent() != null){

            type_client = getIntent().getIntExtra("type_client", 1);
            if (type_client ==1)
                prenom = getIntent().getStringExtra("prenom");
            nom = getIntent().getStringExtra("nom");
            adresse = getIntent().getStringExtra("adresse");
            code_postal = getIntent().getStringExtra("code_postal");
            phone = getIntent().getStringExtra("phone");

            if (prenom != null && !prenom.isEmpty())
                nom = prenom + " " + nom;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inscription,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setEnabled(false);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Création de votre compte");
        dialog.setMessage("Veuillez patienter un instant ...");
        dialog.setCancelable(false);
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.valider:
                dialog = new ProgressDialog(this);
                dialog.setTitle("Création de votre compte");
                dialog.setMessage("Veuillez patienter un instant ...");
                dialog.setCancelable(false);
                if (validEmail() && validPass() && validPassConfirm()) {
                    dialog.show();
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        pushInfos(user);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(FinalInscription.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                    // ...
                                }
                            });
                }
                break;
        }

        item.setEnabled(true);
        return true;
    }

    private boolean validPass(){
        String typed = pass.getText().toString();
        boolean isCorrect = (!typed.isEmpty() && typed.length()>=6);
        if (!isCorrect)
            pass.setBackground(getResources().getDrawable(R.drawable.editext_bg_error));
        else
            pass.setBackground(getResources().getDrawable(R.drawable.editext_bg));
        return isCorrect;
    }
    private boolean validPassConfirm(){
        String typed = pass_confirm.getText().toString();
        boolean isCorrect = (!typed.isEmpty() && typed.equals(pass.getText().toString()));
        if (!isCorrect)
            pass_confirm.setBackground(getResources().getDrawable(R.drawable.editext_bg_error));
        else
            pass_confirm.setBackground(getResources().getDrawable(R.drawable.editext_bg));
        return isCorrect;
    }
    private boolean validEmail(){
        String typed = email.getText().toString();
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email.getText().toString());
        boolean isCorrect = (!typed.isEmpty() && matcher.find());
        if (!isCorrect)
            email.setBackground(getResources().getDrawable(R.drawable.editext_bg_error));
        else
            email.setBackground(getResources().getDrawable(R.drawable.editext_bg));
        return isCorrect;
    }

    public void pushInfos(FirebaseUser user){

        if (user != null){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FinalInscription.this);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            String id = reference.push().getKey();

            reference.child(id).child(USER_NAME).setValue(nom);
            reference.child(id).child(USER_LOCATION).setValue(adresse);
            reference.child(id).child(USER_MAIL).setValue(email.getText().toString());
            reference.child(id).child(USER_PHONE).setValue(phone);
            reference.child(id).child(USER_TYPE).setValue((type_client == 1)? CLENT_PARTICULIER:CLENT_ENTREPRISE);
            preferences.edit()
                    .putBoolean(Utils.IS_CCONNECTED,true)
                    .putString(USER_NAME,nom)
                    .putString(Utils.USER_PHONE,phone)
                    .putString(Utils.USER_MAIL,email.getText().toString())
                    .putString(Utils.USER_LOCATION,adresse)
                    .putString(Utils.USER_TYPE,(type_client == 1)? CLENT_PARTICULIER: CLENT_ENTREPRISE)
                    .putString(Utils.USER_ID,id)
                    .apply();

            startActivity(new Intent(this,Chargement.class));
            finish();
        }

        dialog.dismiss();

    }
}