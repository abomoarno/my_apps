package afrimoov.ke.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import afrimoov.ke.R;
import afrimoov.ke.utilis.Utils;

public class Notifications extends Activity implements CompoundButton.OnCheckedChangeListener{

    private TextView title;
    private TextView message;
    private Switch aSwitch;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        title = findViewById(R.id.notifTitle);
        message = findViewById(R.id.notifMessage);
        aSwitch = findViewById(R.id.notifSwitch);
        aSwitch.setOnCheckedChangeListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ActionBar actionBar = getActionBar();
        aSwitch.setChecked(preferences.getBoolean(Utils.NOTIFICATIONS,true));
        if (actionBar != null){

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
            actionBar.setTitle(getString(R.string.notifications));
        }
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/opensans_regular.ttf");
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
        Chargement.subscribeToTopic(b);
        preferences.edit().putBoolean(Utils.NOTIFICATIONS,b).apply();
    }
}
