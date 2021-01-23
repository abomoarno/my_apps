package niamoro.comedy.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;

import niamoro.comedy.R;
import niamoro.comedy.pages.Page_Play;
import niamoro.comedy.utilis.Video;

import static niamoro.comedy.utilis.Utils.NUMBER_PUB_SHOWN;
import static niamoro.comedy.utilis.Utils.NUMBER_VIDEOS_BEFORE_PUB;

public class ReplayShow extends YouTubeBaseActivity {


    private Toolbar mToolbar;
    private ActionBar actionBar;

    private InterstitialAd playInterst;
    private InterstitialAd playInterstVideo;
    private AdRequest adRequest;
    private ProgressDialog dialog;

    private String videoToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        adRequest = new AdRequest.Builder().build();
        Fragment play_fragment = new Page_Play();
        play_fragment.setArguments(getIntent().getExtras());
        setFragment(play_fragment);
    }

    private void initAds(){
        playInterst = new InterstitialAd(this);
        playInterstVideo = new InterstitialAd(this);
        playInterst.setAdUnitId(getString(R.string.pub_image));
        playInterstVideo.setAdUnitId(getString(R.string.pub_video));
        playInterstVideo.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                dialog.dismiss();
                playInterstVideo.show();
            }
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                playInterst.loadAd(adRequest);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                playNext(videoToShow);
            }
        });
        playInterst.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                dialog.dismiss();
                playInterst.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                playNext(videoToShow);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                dialog.dismiss();
                playNext(videoToShow);
            }
        });
    }
    private void loadAds(){
        if (!(playInterstVideo == null ||  playInterstVideo.isLoaded()))
            playInterstVideo.loadAd(adRequest);
    }

    public void setFragment(android.app.Fragment fragment){
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void reload(String video_id){
        videoToShow = video_id;
        if ((NUMBER_VIDEOS_BEFORE_PUB != 0) && (NUMBER_PUB_SHOWN != 0)) {
            if ((NUMBER_PUB_SHOWN % NUMBER_VIDEOS_BEFORE_PUB) == 0) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(false);
                dialog.setTitle("Chargement");
                dialog.setMessage("Patientez quelques instants ...");
                dialog.show();
                initAds();
                loadAds();
            }
            else
                playNext(videoToShow);
        }
        else {
            NUMBER_VIDEOS_BEFORE_PUB = 3;
            playNext(videoToShow);
        }

    }

    private void playNext(String video_id){
        Intent intent = getIntent();
        intent.putExtra("videoId", video_id);
        intent.removeExtra("from");

        Fragment play_fragment = new Page_Play();
        play_fragment.setArguments(getIntent().getExtras());
        setFragment(play_fragment);
    }

    public void setTopBar(String title){
        mToolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(mToolbar);
        }
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mToolbar.setTitleTextColor(Color.WHITE);
            }
            actionBar = getActionBar();
            if (actionBar !=  null) {
                actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(title);
            }
        }
    }

    @Override
    public void onBackPressed() {
        String from = getIntent().getStringExtra("from");
        if (from != null){
            startActivity(new Intent(this,Accueil.class));
            finish();
        }
        else
            super.onBackPressed();
    }
}