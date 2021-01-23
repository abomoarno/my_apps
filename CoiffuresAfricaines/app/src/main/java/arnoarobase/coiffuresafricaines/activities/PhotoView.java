package arnoarobase.coiffuresafricaines.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import arnoarobase.coiffuresafricaines.R;
import arnoarobase.coiffuresafricaines.calsses.OnSwipeTouchListener;
import arnoarobase.coiffuresafricaines.calsses.Photo;
import arnoarobase.coiffuresafricaines.db_mangers.PhotosManager;
import arnoarobase.coiffuresafricaines.pages.PhotoViewer;

public class PhotoView extends AppCompatActivity {

    private AdView mAdView;
    private PhotoViewer fragment;
    private ActionBar actionBar;
    private List<Photo> photos;
    private int currentPhoto;

    public static String photo_id;
    public static String photo_genre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);
        photos = new ArrayList<>();
        photo_id = getIntent().getStringExtra("photo_id");
        photo_genre = getIntent().getStringExtra("photo_genre");
        photos.add(new PhotosManager(this).getPhoto(photo_id));
        photos.addAll(new PhotosManager(this).getFromGrid(photo_id,photo_genre));
        currentPhoto = 0;
        setFragment(photo_id);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        mAdView.loadAd(adRequest);
    }

    public void setFragment(String id){
        if (id == null)
            return;
        Bundle bundle = new Bundle();
        bundle.putString("photo_id",id);
        fragment = new PhotoViewer();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public String getNextId(){
        if(currentPhoto >= photos.size()-1)
            return null;
        currentPhoto++;
        return photos.get(currentPhoto).getId();
    }
    public String getPrevId(){
        if(currentPhoto <= 0)
            return null;
        currentPhoto--;
        return photos.get(currentPhoto).getId();
    }
}
