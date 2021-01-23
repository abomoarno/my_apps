package casalov.security.activities;

import android.content.Intent;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import casalov.security.R;
import casalov.security.utils.Utils;

public class Modifier_Mail extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText pass;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.modifier_email);
        pass = findViewById(R.id.password);
        email = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
            actionBar.setTitle(R.string.titre_modifier_email);
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
                if (validEmail()) {
                    final SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
                    mAuth.signInWithEmailAndPassword(p.getString(Utils.USER_MAIL, ""), pass.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(Modifier_Mail.this, "Authentication Ok.",
                                                Toast.LENGTH_SHORT).show();

                                        assert user != null;
                                        user.updateEmail(email.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            p.edit().putString(Utils.USER_MAIL, email.getText().toString()).apply();
                                                        }
                                                    }
                                                });

                                        p.edit()
                                                .putString(Utils.USER_MAIL, email.getText().toString())
                                                .apply();
                                        Modifier_Mail.super.onBackPressed();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Modifier_Mail.this, "Wrong credentials.",
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
}
