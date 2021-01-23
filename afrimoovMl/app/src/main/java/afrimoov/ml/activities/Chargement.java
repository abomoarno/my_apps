package afrimoov.ml.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import afrimoov.ml.R;
import afrimoov.ml.db_manager.ReplaysManager;
import afrimoov.ml.db_manager.TvsManager;
import afrimoov.ml.utilis.Live_Tv;
import afrimoov.ml.utilis.Replay;
import afrimoov.ml.utilis.Utils;

import static afrimoov.ml.utilis.Utils.INTERVAL_BEFORE_PUB;
import static afrimoov.ml.utilis.Utils.NUMBER_VIDEOS_BEFORE_PUB;
import static afrimoov.ml.utilis.Utils.TIME_BEFORE_PUB;
import static afrimoov.ml.utilis.Utils.VIDEOS_SHOW_BEFORE_PUB;

public class Chargement extends Activity {
    private SharedPreferences preferences;
    private InterstitialAd openInterst;
    private InterstitialAd openInterstVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (new Utils(Chargement.this).isNetworkReachable()) {
                    Utils.getTvs(Chargement.this);
                    subscribeToTopic(preferences.getBoolean(Utils.NOTIFICATIONS,true));
                    Utils.getParams(Chargement.this);
                }
            }
        }).start();
        preferences.edit().putInt(Utils.NUMBER_OPEN,preferences.getInt(Utils.NUMBER_OPEN,0)+1).apply();
        TextView version = findViewById(R.id.app_version);
        String vers = "2019 Afrimoov TV v" ;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            vers += packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            vers += "1.0.0";
            e.printStackTrace();
        }
        version.setText(vers);
        NUMBER_VIDEOS_BEFORE_PUB = preferences.getInt(VIDEOS_SHOW_BEFORE_PUB,3);
        TIME_BEFORE_PUB = preferences.getInt(INTERVAL_BEFORE_PUB,600);

    }

    @Override
    protected void onStart() {
        super.onStart();

        ReplayShow.initAds(getApplicationContext());
        initAds(getApplicationContext());
    }

    private void getReplays(){
        if (!new Utils(this).isNetworkReachable())
        {
            next();
            return;
        }

        final DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("videos");
        final ReplaysManager manager = new ReplaysManager(Chargement.this);
        final List<String> ids = manager.getLast_Id();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Replay> replays = new ArrayList<>();
                Map<String,Integer> viewToUpdate = new HashMap<>();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    final String replay_id = snapshot.getKey();
                    try {
                        int views = snapshot.child("vues").getValue(Integer.class);

                        if (!manager.verifyReplayId(replay_id)) {
                            String title = snapshot.child("titre").getValue(String.class);
                            String description = snapshot.child("description").getValue(String.class);
                            String plateforme = snapshot.child("plateforme").getValue(String.class);
                            String chaine = snapshot.child("channel").getValue(String.class);
                            String image = snapshot.child("image").getValue(String.class);
                            String date = snapshot.child("date").getValue(String.class);
                            String duree = snapshot.child("duration").getValue(String.class);

                            Replay replay = new Replay(replay_id, title);
                            replay.setDescription(description);
                            replay.setDate(date);
                            replay.setPlateforme(plateforme);
                            replay.setChaine(chaine);
                            replay.setImage(image);
                            replay.setLink(replay_id);
                            replay.setViews(views);
                            replay.setDuree(duree);

                            replays.add(replay);
                        } else {
                            viewToUpdate.put(replay_id,views);
                            ids.remove(replay_id);
                        }
                    }
                    catch (Exception e){
                        Log.e("PROBLEM",replay_id);
                    }
                }
                manager.insertReplays(replays);
                manager.updateViews(viewToUpdate);
                manager.deleteReplays(ids);
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

            String type = getIntent().getStringExtra("type");
            String id = getIntent().getStringExtra("id");

            if (type.equals("tv")){
                Live_Tv tv = new TvsManager(this).getTv(id);
                intent.putExtra("videoId",tv.getTv_id());
                intent.putExtra("videoPlateforme",tv.getPlateforme());
                intent.putExtra("videoType","live");
            }
            else {
                Replay replay = new ReplaysManager(this).getReplay(id);
                intent.putExtra("videoId",replay.getReplay_id());
                intent.putExtra("videoPlateforme",replay.getPlateforme());
                intent.putExtra("videoType","replay");
            }
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
        MobileAds.initialize(context, "ca-app-pub-3611856516220986~4253142764");
        openInterst = new InterstitialAd(context);
        openInterstVideo = new InterstitialAd(context);
        openInterst.setAdUnitId(Utils.INTERST_IMAGE);
        openInterstVideo.setAdUnitId(Utils.INTERST_VIDEO);
        openInterstVideo.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                openInterstVideo.show();
            }
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                openInterst.loadAd(new AdRequest.Builder().build());
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
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                getReplays();
            }
        });
        loadAds();
    }
    private void loadAds(){
        if (! openInterstVideo.isLoaded())
            openInterstVideo.loadAd(new AdRequest.Builder().build());
    }

    public static void subscribeToTopic(boolean notif){
        if (notif) {
            FirebaseMessaging.getInstance().subscribeToTopic("appnotif");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("dailynotif");
        }
        else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("dailynotif");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("appnotif");
        }

    }
}