package dev.casalov.projetetincel.activities;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.pages.DetailsRapport.AccueilDetailsRapport;

public class DetailsRapport extends AppCompatActivity {
    private ActionBar actionBar;
    public static int selectedPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_rapport);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        selectedPage = 1;
        String project_id = getIntent().getStringExtra("project_id");
        Fragment fragment = new AccueilDetailsRapport();
        Bundle bundle = new Bundle();
        bundle.putString("project_id",project_id);
        fragment.setArguments(bundle);
        setFragment(fragment);
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        if (selectedPage != 1)
            ft.addToBackStack(null);
        ft.commit();
    }

    public void setTitle(String title){
        if (actionBar != null){
            actionBar.setTitle(title);
        }
    }
    public void setSubTitle(String subTitle){
        if (actionBar != null){
            actionBar.setSubtitle(subTitle);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }
    public String getPath(Uri uri){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,proj, null, null, null);

        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(index);
    }
}