package wiredstartups.yakjayexpo.resto;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import wiredstartups.yakjayexpo.R;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.utils.Utils.APP_NAME_LABEL;

public class Resto extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        findViewById(R.id.scan).setOnClickListener(this);

        setPage(new HomePage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entry_exit, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_change_app){
            preferences.edit().remove(APP_NAME_LABEL).apply();
            super.onBackPressed();
            return true;
        }

        else if (id == android.R.id.home){
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setPage(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.main_container, fragment);

        ft.commit();
    }

    public void toggleFabs(boolean status){
        if (status){
            findViewById(R.id.scan).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.scan).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {


        Fragment fragment = new Scan();

        setPage(fragment);

        //sendError(VERIFICATION_RESULT_ERROR);
        // sendParticipant();
    }

    public void setTitre(String titre) {
        actionBar.setTitle(titre);
    }

    public void toggleHomeButton(boolean status){
        actionBar.setDisplayHomeAsUpEnabled(status);
    }

}
