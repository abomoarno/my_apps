package wiredstartups.yakjayexpo.entry_exit;

import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
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
import static wiredstartups.yakjayexpo.entry_exit.Success.VERIFICATION_RESULT_ERROR;
import static wiredstartups.yakjayexpo.entry_exit.Success.VERIFICATION_RESULT_MULTIPLE_ENTRIES;
import static wiredstartups.yakjayexpo.entry_exit.Success.VERIFICATION_RESULT_OK;
import static wiredstartups.yakjayexpo.utils.Utils.APP_NAME_LABEL;
import static wiredstartups.yakjayexpo.utils.Utils.ENTER_OPERATION;
import static wiredstartups.yakjayexpo.utils.Utils.ENTRY_ID_LABEL;
import static wiredstartups.yakjayexpo.utils.Utils.EXIT_OPERATION;
import static wiredstartups.yakjayexpo.utils.Utils.OPERATION_LABEL;

public class EntryExit extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_exit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        findViewById(R.id.scan).setOnClickListener(this);
        findViewById(R.id.enter_code).setOnClickListener(this);

    }

    public void setPage(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.main_container, fragment);

        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (preferences.getInt(ENTRY_ID_LABEL,-1) == -1){
            setPage(new Choose_Entry());
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
            setPage(new Choose_Entry());
            return true;
        }

        else if (id == R.id.action_change_app){
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

    public void toggleFabs(boolean status){
        if (status){
            findViewById(R.id.scan).setVisibility(View.VISIBLE);
            findViewById(R.id.enter_code).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.scan).setVisibility(View.GONE);
            findViewById(R.id.enter_code).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        String operation = "";

        if (view.getId() == R.id.scan){
            operation = ENTER_OPERATION;

        }
        else if(view.getId() == R.id.enter_code){
            operation = EXIT_OPERATION;

        }

        Fragment fragment = new Scan();
        Bundle bundle = new Bundle();
        bundle.putString(OPERATION_LABEL,operation);
        fragment.setArguments(bundle);
        setPage(fragment);

    }

    public void setTitre(String titre) {
        actionBar.setTitle(titre);
    }

    private void sendError(String result){
        Bundle args = new Bundle();
        args.putString(CODE_VERIFICATION_RESULT,result);

        Fragment fragment = new Success();
        fragment.setArguments(args);

        setPage(fragment);
    }

    private void sendParticipant(){
        Bundle args = new Bundle();
        Participant participant = new Participant("");

        participant.setName("ABOMO ARNO");

        int warn = new Random().nextInt(2);

        if (warn == 1)
            args.putString(CODE_VERIFICATION_RESULT, VERIFICATION_RESULT_MULTIPLE_ENTRIES);
        else
            args.putString(CODE_VERIFICATION_RESULT, VERIFICATION_RESULT_OK);


        args.putParcelable("participant",participant);

        Fragment fragment = new Success();
        fragment.setArguments(args);
       setPage(fragment);
    }
    private void sendExit(){
        Bundle args = new Bundle();
        Participant participant = new Participant("");

        participant.setName("Participant(e) Sortie(e)");

        int warn = 0;

        args.putString(CODE_VERIFICATION_RESULT, VERIFICATION_RESULT_OK);


        args.putParcelable("participant",participant);

        Fragment fragment = new Success();
        fragment.setArguments(args);
       setPage(fragment);
    }

    public void toggleHomeButton(boolean status){
        actionBar.setDisplayHomeAsUpEnabled(status);
    }
}
