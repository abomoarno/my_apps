package niamoro.comedy.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import niamoro.comedy.R;
import niamoro.comedy.db_manager.VideosManager;
import niamoro.comedy.utilis.Video;
import niamoro.comedy.utilis.Utils;

import static niamoro.comedy.utilis.Utils.NUMBER_VIDEOS_BEFORE_PUB;
import static niamoro.comedy.utilis.Utils.VIDEOS_SHOW_BEFORE_PUB;

public class Chargement extends Activity{
    private SharedPreferences preferences;
    private InterstitialAd openInterst;
    private InterstitialAd openInterstVideo;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement);
        MobileAds.initialize(this, getString(R.string.pub_id));
        adRequest = new AdRequest.Builder().build();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putInt(Utils.NUMBER_OPEN,preferences.getInt(Utils.NUMBER_OPEN,0)+1).apply();
        NUMBER_VIDEOS_BEFORE_PUB = preferences.getInt(VIDEOS_SHOW_BEFORE_PUB,3);
        authUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initAds(Chargement.this);
        loadAds();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getReplays(){
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            next();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (new Utils(Chargement.this).isNetworkReachable()) {
                    Utils.getSources(Chargement.this);
                    Utils.getParams(Chargement.this);
                }
            }
        }).start();

        final DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("videos");
        final VideosManager manager = new VideosManager(Chargement.this);
        final List<String> ids = manager.getAllIds();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Video> toAdd = new ArrayList<>();
                List<Video> toUpdate = new ArrayList<>();

                GenericTypeIndicator<HashMap<String,Video>> t = new GenericTypeIndicator<HashMap<String, Video>>() {};
                HashMap<String,Video> videos = dataSnapshot.getValue(t);

                if (videos != null){
                    for (String key:videos.keySet()){
                        Video video = videos.get(key);
                        if (video != null){
                            video.setVideo_id(key);
                            video.setLink(key);
                            if (ids.contains(key)){
                                toUpdate.add(videos.get(key));
                                ids.remove(key);
                            }
                            else {
                                toAdd.add(video);
                            }
                        }
                    }
                }
                else

                manager.deleteVideos(ids);
                manager.updateViews(toUpdate);
                manager.insertVideos(toAdd);
                next();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                next();
            }
        });
    }
    private void next(){
        if (getIntent().getStringExtra("type") != null){

            Intent intent = new Intent(this,ReplayShow.class);
            intent.putExtra("from","notification");
            String id = getIntent().getStringExtra("id");
            intent.putExtra("videoId",id);

            startActivity(intent);
        }
        else {
            if(!preferences.getBoolean("privacy",false)){
                Intent intent = new Intent(getApplicationContext(),Privacy.class);
                startActivity(intent);
            }
            else
                startActivity(new Intent(this, Accueil.class));
        }

        finish();
    }
    private void initAds(final Context context){
        openInterst = new InterstitialAd(context);
        openInterstVideo = new InterstitialAd(context);
        openInterst.setAdUnitId(getString(R.string.pub_image));
        openInterstVideo.setAdUnitId(getString(R.string.pub_video));
        openInterstVideo.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                openInterstVideo.show();
            }
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                openInterst.loadAd(adRequest);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                getReplays();
            }
        });
        openInterst.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                openInterst.show();
            }
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                getReplays();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                getReplays();
            }
        });
    }
    private void loadAds(){
        if (! openInterstVideo.isLoaded())
            openInterstVideo.loadAd(adRequest);
    }


    private void authUser(){
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            FirebaseAuth.getInstance().signInAnonymously();
        }
    }
}