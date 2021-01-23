package fallapro.landcrowdy.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.classes.Alert;
import fallapro.landcrowdy.classes.Annonce;
import fallapro.landcrowdy.classes.Annonce_Small_Adaptor;
import fallapro.landcrowdy.dbManagment.AlertsManager;

public class AlerteDetails extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{
    private TextView activer;
    private boolean actif = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerte_details);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("alert_id")){
            Alert alert = new AlertsManager(getApplicationContext()).getAlert(bundle.getString("alert_id"));

            if (alert != null){
                actif = alert.getStatus() == 1;
            }
        }

        activer = findViewById(R.id.activer_alert_text);
        ListView listView = findViewById(R.id.listView);
        ArrayList<Annonce> annonces = new ArrayList<>();
        annonces.add(null);
        annonces.add(null);
        annonces.add(null);
        annonces.add(null);
        annonces.add(null);
        Annonce_Small_Adaptor adaptor = new Annonce_Small_Adaptor(this,annonces);
        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(this);
        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("actif")){
            actif = b.getBoolean("actif");
        }
        if (actif){
            activer.setText(getString(R.string.alert_details_desactiver));
        }
        else
            activer.setText(R.string.alert_details_reactiver);
        findViewById(R.id.modifier_alert).setOnClickListener(this);
        findViewById(R.id.reactiver_alert).setOnClickListener(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("Achat Appart Yaoundé");
            actionBar.setSubtitle(R.string.alert_details_subtitle);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
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

            case R.id.modifier_alert:
                startActivity(new Intent(getApplicationContext(),AlertModifier.class));
                break;
            case R.id.reactiver_alert:
                if (actif){
                    startActivity(new Intent(getApplicationContext(),AlerteArret.class));
                }
                else {
                    activer.setText(R.string.alert_details_desactiver);
                    actif = true;
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(getApplicationContext(), fallapro.landcrowdy.activities.Annonce.class));
    }
}
