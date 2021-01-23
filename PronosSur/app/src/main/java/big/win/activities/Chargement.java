package big.win.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import big.win.R;
import big.win.classes.Pronostique;
import big.win.classes.Utils;
import big.win.db_managment.Pronos_Manager;

public class Chargement extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement);

        //MobileAds.initialize(this, "ca-app-pub-8287372312965391~8773406899");
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (new Utils(this).isNetworkReachable()){
            initdAds(this);
        }
        else
            next();
    }

    private void next(){
        startActivity(new Intent(this,Accueil.class));
        finish();
    }

    private void getMatchs(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("matchs");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pronos_Manager manager = new Pronos_Manager(getApplicationContext());
                List<Pronostique> toAdd = new ArrayList<>();
                List<Pronostique> toUpdate = new ArrayList<>();
                List<Integer> ids =manager.getAllIds();

                GenericTypeIndicator<ArrayList<Pronostique>> t = new GenericTypeIndicator<ArrayList<Pronostique>>() {};
                ArrayList<Pronostique> pronos = dataSnapshot.getValue(t);

                if (pronos != null){
                    for (Pronostique pronostique:pronos){
                        if (pronostique != null){
                            if (ids.contains(pronostique.getId())){
                                ids.remove(Integer.valueOf(pronostique.getId()));
                                toUpdate.add(pronostique);
                            }
                            else {
                                toAdd.add(pronostique);
                            }
                        }
                    }
                }

                manager.insertPronos(toAdd);
                manager.updatePronos(toUpdate);
                manager.deletePronos(ids);

                next();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                next();
            }
        });
    }

    private void initdAds(final Context context){
        mInterstitialAd = new InterstitialAd(context);
        //mInterstitialAd.setAdUnitId("ca-app-pub-8287372312965391/2065470268");
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                getMatchs();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();

                getMatchs();
            }
        });

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

}
