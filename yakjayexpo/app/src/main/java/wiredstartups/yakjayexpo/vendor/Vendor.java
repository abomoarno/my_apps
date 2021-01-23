package wiredstartups.yakjayexpo.vendor;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import wiredstartups.yakjayexpo.R;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.utils.Utils.PARTNER_ID_LABEL;

public class Vendor extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        findViewById(R.id.scan).setOnClickListener(this);


        //setPage(new HomePage());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (preferences.getInt(PARTNER_ID_LABEL,-1) == -1){
            setPage(new Enter_Pin());
        }
        else{
            setPage(new HomePage());
        }
    }

    public void setPage(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.main_container, fragment);

        ft.commit();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new Scan();

        setPage(fragment);
    }

    public void setTitre(String titre) {
        actionBar.setTitle(titre);
    }

    public void toggleHomeButton(boolean status){
        actionBar.setDisplayHomeAsUpEnabled(status);
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}