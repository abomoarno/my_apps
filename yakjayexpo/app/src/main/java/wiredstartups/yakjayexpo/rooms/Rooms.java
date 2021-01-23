package wiredstartups.yakjayexpo.rooms;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;

import java.util.Random;

import wiredstartups.yakjayexpo.R;
import wiredstartups.yakjayexpo.utils.Participant;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.entry_exit.Success.CODE_VERIFICATION_RESULT;
import static wiredstartups.yakjayexpo.entry_exit.Success.VERIFICATION_RESULT_MULTIPLE_ENTRIES;
import static wiredstartups.yakjayexpo.entry_exit.Success.VERIFICATION_RESULT_OK;
import static wiredstartups.yakjayexpo.rooms.Success.VERIFICATION_RESULT_BAD_QR;
import static wiredstartups.yakjayexpo.rooms.Success.VERIFICATION_RESULT_CLUB;
import static wiredstartups.yakjayexpo.rooms.Success.VERIFICATION_RESULT_INVALID_CODE;
import static wiredstartups.yakjayexpo.rooms.Success.VERIFICATION_RESULT_PREMIUM;
import static wiredstartups.yakjayexpo.rooms.Success.VERIFICATION_RESULT_VIP;
import static wiredstartups.yakjayexpo.utils.Utils.APP_NAME_LABEL;
import static wiredstartups.yakjayexpo.utils.Utils.ROOM_ID_LABEL;

public class Rooms extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        findViewById(R.id.scan).setOnClickListener(this);

    }

    public void setPage(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.main_container, fragment);

        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (preferences.getInt(ROOM_ID_LABEL,-1) == -1){
            setPage(new Choose_Room());
        }
        else{
            setPage(new HomePage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entry_exit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            setPage(new Choose_Room());
            return true;
        }
        else if (id == android.R.id.home){
            super.onBackPressed();
            return true;
        }

        else if (id == R.id.action_change_app){
            preferences.edit().remove(APP_NAME_LABEL).apply();
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        Fragment fragment = new Scan();

        setPage(fragment);

        //sendTicket(VERIFICATION_RESULT_BAD_QR);

        //sendError(VERIFICATION_RESULT_ERROR);
        // sendParticipant();
    }

    public void setTitre(String titre) {
        actionBar.setTitle(titre);
    }

    public void toggleFabs(boolean status){
        if (status){
            findViewById(R.id.scan).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.scan).setVisibility(View.GONE);
        }
    }

    private void sendError(String result){
        Bundle args = new Bundle();
        args.putString(CODE_VERIFICATION_RESULT,result);

        Fragment fragment = new Success();
        fragment.setArguments(args);

        setPage(fragment);
    }

    private void sendTicket(String ticket){
        Bundle args = new Bundle();
        args.putString(CODE_VERIFICATION_RESULT, ticket);

        Fragment fragment = new Success();
        fragment.setArguments(args);
        setPage(fragment);
    }

    public void toggleHomeButton(boolean status){
        actionBar.setDisplayHomeAsUpEnabled(status);
    }
}
