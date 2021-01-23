package fallapro.landcrowdy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import fallapro.landcrowdy.R;

public class AlerteArret extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerte_arret);
        findViewById(R.id.desactiver_alert).setOnClickListener(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("Achat Appart Yaoundé");
            actionBar.setSubtitle(R.string.arreter_alert);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.desactiver_alert:
                Intent intent = new Intent(getApplicationContext(),AlerteDetails.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("actif",false);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
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
}
