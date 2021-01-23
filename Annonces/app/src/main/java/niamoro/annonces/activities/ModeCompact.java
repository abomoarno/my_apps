package niamoro.annonces.activities;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import niamoro.annonces.R;
import niamoro.annonces.utils.Utils;


public class ModeCompact extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private TextView title;
    private TextView message;
    private Switch aSwitch;
    private Toast toast;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_compact);

        title = findViewById(R.id.modeCompact);
        message = findViewById(R.id.compactMessage);
        aSwitch = findViewById(R.id.compactSwitch);
        aSwitch.setOnCheckedChangeListener(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        aSwitch.setChecked(preferences.getBoolean("compact",false));

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Gérez vos données");
        }

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/opensans_extrabold.ttf");
        title.setTypeface(tf);
        message.setTypeface(tf);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return  true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        preferences.edit().putBoolean("compact",b).apply();
        Utils.COMPACT_MODE = b;
        if (b)
            toast = Toast.makeText(this,"Mode Compact activé",Toast.LENGTH_SHORT);
        else
            toast = Toast.makeText(this,"Mode Compact désactivé",Toast.LENGTH_SHORT);
        toast.show();
    }
}
