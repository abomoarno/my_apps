package afrimoov.tg.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.List;
import afrimoov.tg.R;
import afrimoov.tg.alerts.AlarmInitialisation;
import afrimoov.tg.db_manager.ReplaysManager;
import afrimoov.tg.utilis.Replay;
import afrimoov.tg.utilis.Utils;

import static afrimoov.tg.utilis.Utils.ADS_REMOVED;
import static afrimoov.tg.utilis.Utils.INTERVAL_BEFORE_PUB;
import static afrimoov.tg.utilis.Utils.NUMBER_VIDEOS_BEFORE_PUB;
import static afrimoov.tg.utilis.Utils.TIME_BEFORE_PUB;
import static afrimoov.tg.utilis.Utils.VIDEOS_SHOW_BEFORE_PUB;

public class Chargement extends Activity implements PurchasesUpdatedListener {
    private BillingClient mBillingClient;
    private SharedPreferences preferences;
    private InterstitialAd openInterst;
    private InterstitialAd openInterstVideo;

    private TextView version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (new Utils(Chargement.this).isNetworkReachable()) {
                    getReplays();
                    Utils.getTvs(Chargement.this);
                    subscribeToTopic(preferences.getBoolean(Utils.NOTIFICATIONS,true));
                    Utils.getParams(Chargement.this);
                }
            }
        }).start();

        preferences.edit().putInt(Utils.NUMBER_OPEN,preferences.getInt(Utils.NUMBER_OPEN,0)+1).apply();
        verifyPremium();
        version = findViewById(R.id.app_version);
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
        initAds(this);
        loadAds();
    }
    private void getReplays(){
        final DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("videos");
        final ReplaysManager manager = new ReplaysManager(Chargement.this);
        final List<String> ids = manager.getLast_Id();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    final String replay_id = snapshot.getKey();
                    int views = snapshot.child("vues").getValue(Integer.class);

                    if (!manager.verifyReplayId(replay_id)) {
                        String title = snapshot.child("titre").getValue(String.class);
                        String description = snapshot.child("description").getValue(String.class);
                        String plateforme = snapshot.child("plateforme").getValue(String.class);
                        String chaine = snapshot.child("channel").getValue(String.class);
                        String image = snapshot.child("image").getValue(String.class);
                        String date = snapshot.child("date").getValue(String.class);
                        String duree = snapshot.child("duration").getValue(String.class);

                        Replay replay = new Replay(replay_id,title);
                        replay.setDescription(description);
                        replay.setDate(date);
                        replay.setPlateforme(plateforme);
                        replay.setChaine(chaine);
                        replay.setImage(image);
                        replay.setLink(replay_id);
                        replay.setViews(views);
                        replay.setDuree(duree);

                        manager.insertReplay(replay);
                    }
                    else {
                        manager.updateViews(replay_id, views);
                        ids.remove(replay_id);
                    }
                }
                for (String id : ids){
                    manager.deleteReplay(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void next(){
        startActivity(new Intent(this,Accueil.class));
        finish();
    }
    private void initAds(final Context context){
        MobileAds.initialize(context, "ca-app-pub-2082216950067064~7571373650");
        openInterst = new InterstitialAd(context);
        openInterstVideo = new InterstitialAd(context);
        openInterst.setAdUnitId("ca-app-pub-2082216950067064/2202006212");
        openInterstVideo.setAdUnitId("ca-app-pub-2082216950067064/6496474448");
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
                        next();
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        next();
                    }
                });
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                next();
            }
        });
    }
    private void loadAds(){
        if (! openInterstVideo.isLoaded())
            openInterstVideo.loadAd(new AdRequest.Builder().build());
    }
    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {

    }

    private void verifyPremium(){
        ADS_REMOVED = false;
        mBillingClient = BillingClient.newBuilder(this).setListener(this).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(int responseCode) {
                if (responseCode == BillingClient.BillingResponse.OK){
                    Purchase.PurchasesResult result = mBillingClient.queryPurchases(BillingClient.SkuType.SUBS);
                    if (!(result == null)){
                        List<Purchase> purchases = result.getPurchasesList();
                        if (purchases !=null) {
                            for (Purchase purchase : purchases) {
                                if ((purchase.getSku().equals(Utils.ABONNEMENT_1_MOIS)) ||
                                        (purchase.getSku().equals(Utils.ABONNEMENT_6_MOIS))||
                                        (purchase.getSku().equals(Utils.ABONNEMENT_12_MOIS))) {
                                    ADS_REMOVED = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                ReplayShow.initAds(getApplicationContext(),ADS_REMOVED);
            }

            @Override
            public void onBillingServiceDisconnected() {
                ReplayShow.initAds(getApplicationContext(),ADS_REMOVED);
            }
        });
    }


    public static void subscribeToTopic(boolean notif){
        if (notif) {
            FirebaseMessaging.getInstance().subscribeToTopic("dailynotif").addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Log.e("Subscription", "OK");
                        }
                    }
            );
        }
        else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("dailynotif").addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Log.e("UnSubscription", "OK");
                        }
                    }
            );
        }

    }
}