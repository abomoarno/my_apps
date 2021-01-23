package arnoarobase.coiffuresafricaines.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import arnoarobase.coiffuresafricaines.R;
import arnoarobase.coiffuresafricaines.calsses.Photo;
import arnoarobase.coiffuresafricaines.calsses.Pub_Interval;
import arnoarobase.coiffuresafricaines.db_mangers.PhotosManager;

public class Chargement extends AppCompatActivity {

    private InterstitialAd intrsVideo;
    private InterstitialAd interstImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement);
        MobileAds.initialize(this,"ca-app-pub-3611856516220986~9659765774");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getPhotos();
            }
        }).start();

        initAds(this);
        Pub_Interval.initAds(this);
    }

    private void getPhotos(){
        final DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("images");
        final PhotosManager manager = new PhotosManager(Chargement.this);
        final List<String> ids = manager.getIds();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    final String photo_id = snapshot.getKey();
                    String title = snapshot.child("titre").getValue(String.class);
                    int note = snapshot.child("likes").getValue(Integer.class);
                    String description = snapshot.child("description").getValue(String.class);
                    Photo photo = new Photo(photo_id, title);
                    photo.setLiks(note);
                    photo.setLiked(0);
                    photo.setDescription(description);
                    String genre = snapshot.child("genre").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String url = snapshot.child("url").getValue(String.class);
                    photo.setGenre(genre);
                    photo.setUrl(url);
                    photo.setDate(date);

                    if (ids.contains(photo_id)) {
                        manager.updatePhoto(photo);
                        ids.remove(photo_id);
                    }
                    else {

                        manager.insertPhoto(photo);
                        ids.remove(photo_id);
                    }
                }
                for (String id : ids){
                    manager.deletePhoto(id);
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

    private void initAds(Context context){
        interstImage = new InterstitialAd(context);
        interstImage.setAdUnitId("ca-app-pub-3611856516220986/9235479545");
        intrsVideo = new InterstitialAd(context);
        intrsVideo.setAdUnitId("ca-app-pub-3611856516220986/2328070660");
        intrsVideo.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                intrsVideo.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                next();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                interstImage.loadAd(new AdRequest.Builder().build());
                interstImage.setAdListener(new AdListener(){
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        interstImage.show();
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
        });
        intrsVideo.loadAd(new AdRequest.Builder().build());
    }

}
