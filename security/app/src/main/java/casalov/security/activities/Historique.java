package casalov.security.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import casalov.security.R;
import casalov.security.adaptors.Interventon_Adaptor;
import casalov.security.classes.Intervention;

public class Historique extends AppCompatActivity {

    private List<Intervention> interventions;
    private Interventon_Adaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique);

        ListView listView = findViewById(R.id.listview);
        interventions = new ArrayList<>();

        for (int i=0;i<10;i++){
            Intervention intervention = new Intervention("id_"+i,"intervention apreès un cambriolage");
            intervention.setDate("12-06-2008");
            intervention.setLieu("Montpelier");
            intervention.setStatut("Payé");
            interventions.add(intervention);
        }
        adaptor = new Interventon_Adaptor(this,interventions);
        listView.setAdapter(adaptor);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.historique_bar));
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }
}
