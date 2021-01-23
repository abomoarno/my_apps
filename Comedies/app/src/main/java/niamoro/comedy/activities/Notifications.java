package niamoro.comedy.activities;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import niamoro.comedy.R;
import niamoro.comedy.adaptor.NotificationAdapter;
import niamoro.comedy.db_manager.ComediensManager;
import niamoro.comedy.utilis.Comedien;
import niamoro.comedy.utilis.Utils;

public class Notifications extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private TextView title;
    private TextView message;
    private ListView listView;
    private List<Comedien> comediens;
    private NotificationAdapter adapter;
    private Switch aSwitch;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        title = findViewById(R.id.notifTitle);
        message = findViewById(R.id.notifMessage);
        aSwitch = findViewById(R.id.notifSwitch);
        listView = findViewById(R.id.list_view);
        aSwitch.setOnCheckedChangeListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ActionBar actionBar = getSupportActionBar();
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
    protected void onStart() {
        super.onStart();

        ComediensManager manager = new ComediensManager(this);
        comediens = manager.getFollowed(50);
        adapter = new NotificationAdapter(this,comediens);
        listView.setAdapter(adapter);
        aSwitch.setChecked(preferences.getBoolean(Utils.NOTIFICATIONS,true));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return  true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        preferences.edit().putBoolean(Utils.NOTIFICATIONS,b).apply();

        if(!b) {
            for (Comedien comedien : comediens)
                comedien.setFollowed(false);
            adapter.notifyDataSetChanged();
        }

    }

    public void setGlobalNotifications(boolean allowed){
        aSwitch.setChecked(allowed);
    }
}
