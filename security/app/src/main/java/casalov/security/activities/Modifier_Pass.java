package casalov.security.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import casalov.security.R;
import casalov.security.utils.Utils;

public class Modifier_Pass extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText pass;
    private EditText new_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifier_password);
        pass = findViewById(R.id.password);
        new_pass = findViewById(R.id.new_password);
        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
            actionBar.setTitle(R.string.titre_mot_de_pass);
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
            case R.id.valider:
                if (validPass()) {
                    final SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
                    mAuth.signInWithEmailAndPassword(p.getString(Utils.USER_MAIL, ""), pass.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(Modifier_Pass.this, "Authentication Ok.",
                                                Toast.LENGTH_SHORT).show();

                                        assert user != null;
                                        user.updatePassword(new_pass.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(Modifier_Pass.this, "Mot de passe changé !!",
                                                                    Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                        Modifier_Pass.super.onBackPressed();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Modifier_Pass.this, "Wrong credentials.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
                break;
            case android.R.id.home:
                super.onBackPressed();
        }
        return true;
    }

    private boolean validPass(){
        String typed = new_pass.getText().toString();
        boolean isCorrect = (!typed.isEmpty() && typed.length()>=6);
        if (!isCorrect)
            new_pass.setBackground(getResources().getDrawable(R.drawable.editext_bg_error));
        else
            new_pass.setBackground(getResources().getDrawable(R.drawable.editext_bg));
        return isCorrect;
    }
}
