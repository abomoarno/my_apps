package casalov.security.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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

public class Connexion extends AppCompatActivity implements View.OnClickListener {

    private EditText pass;
    private EditText email;

    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        findViewById(R.id.pass_forgoten).setOnClickListener(this);
        findViewById(R.id.inscription).setOnClickListener(this);
        findViewById(R.id.valider).setOnClickListener(this);

        pass = findViewById(R.id.password);
        email = findViewById(R.id.username);

        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){

            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
            actionBar.setTitle(R.string.connexion_header);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.valider:
                dialog = new ProgressDialog(this);
                dialog.setTitle("Connexion à votre compte");
                dialog.setMessage("Veuillez patienter un instant ...");
                dialog.setCancelable(false);
                dialog.show();
                mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Connexion.this);
                                    if (!email.getText().toString().equals(preferences.getString(Utils.USER_PHONE,""))) {
                                        getInfos(user);
                                    }
                                    else {
                                        dialog.dismiss();
                                    }

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Connexion.this, "Wrong credentials.",
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                                // ...
                            }
                        });
                break;
            case R.id.pass_forgoten:
                startActivity(new Intent(getApplicationContext(),Modifier_Pass.class));
                break;
            case R.id.inscription:
                startActivity(new Intent(getApplicationContext(),Inscription.class));
                break;
        }
    }

    public void getInfos(FirebaseUser user){

        if (user != null){
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Connexion.this);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String storeMail = snapshot.child(USER_MAIL).getValue(String.class);
                        Log.e(USER_MAIL,storeMail);
                        if (email.getText().toString().equals(storeMail)){
                            String nom = snapshot.child(USER_NAME).getValue(String.class);
                            String adresse = snapshot.child(USER_LOCATION).getValue(String.class);
                            String phone = snapshot.child(USER_PHONE).getValue(String.class);
                            String type_client = snapshot.child(USER_TYPE).getValue(String.class);

                            preferences.edit()
                                    .putBoolean(Utils.IS_CCONNECTED,true)
                                    .putString(USER_NAME,nom)
                                    .putString(Utils.USER_PHONE,phone)
                                    .putString(Utils.USER_MAIL,email.getText().toString())
                                    .putString(Utils.USER_LOCATION,adresse)
                                    .putString(Utils.USER_TYPE,type_client)
                                    .putString(Utils.USER_ID,snapshot.getKey())
                                    .putBoolean(Utils.IS_CCONNECTED,true)
                                    .apply();

                            startActivity(new Intent(Connexion.this,Chargement.class));
                            finish();
                        }
                    }
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    dialog.dismiss();
                }
            });

        }

    }
}