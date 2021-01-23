package arnoarobase.coiffuresafricaines.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import arnoarobase.coiffuresafricaines.R;
import arnoarobase.coiffuresafricaines.adaptors.PhotoListAdater;
import arnoarobase.coiffuresafricaines.adaptors.PhotoRecyclerAdaptor;
import arnoarobase.coiffuresafricaines.calsses.Photo;
import arnoarobase.coiffuresafricaines.db_mangers.PhotosManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Accueil extends AppCompatActivity implements View.OnClickListener, PhotoRecyclerAdaptor.OnItemClickListener{

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        RecyclerView femmes = findViewById(R.id.reclycler_women);
        RecyclerView hommes = findViewById(R.id.reclycler_men);
        RecyclerView liked = findViewById(R.id.reclycler_liked);
        RecyclerView recents = findViewById(R.id.reclycler_news);
        List<Photo> women = new PhotosManager(this).getGenre("f",5);
        List<Photo> men = new PhotosManager(this).getGenre("m",5);
        List<Photo> recent = new PhotosManager(this).getMostRecents(5);
        List<Photo> popular = new PhotosManager(this).getMostPopular(5);

        femmes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hommes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        liked.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        femmes.setAdapter(new PhotoRecyclerAdaptor(this, women, this));

        hommes.setAdapter(new PhotoRecyclerAdaptor(this, men, this));

        liked.setAdapter(new PhotoRecyclerAdaptor(this, popular, this));

        recents.setAdapter(new PhotoRecyclerAdaptor(this, recent, this));

        findViewById(R.id.see_all_liked).setOnClickListener(this);
        findViewById(R.id.see_all_women).setOnClickListener(this);
        findViewById(R.id.see_all_men).setOnClickListener(this);
        findViewById(R.id.see_all_news).setOnClickListener(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        mAdView.loadAd(adRequest);
        remindReview();
    }

    @Override
    public void onClick(View view) {
        String parameter = "liked";
        int bothSingle = 0;

        switch (view.getId()){

            case R.id.see_all_liked:
                parameter = "liked";
                bothSingle = 0;
                break;
            case R.id.see_all_news:
                parameter = "news";
                bothSingle = 0;
                break;
            case R.id.see_all_women:
                parameter = "women";
                bothSingle = 2;
                break;
            case R.id.see_all_men:
                parameter = "men";
                bothSingle = 1;
                break;
        }

        Intent intent = new Intent(this,Results.class);

        intent.putExtra("parameter",parameter);
        intent.putExtra("bothSingle",bothSingle);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Photo photo) {
        Intent intent = new Intent(this, PhotoView.class);
        intent.putExtra("photo_id",photo.getId());
        intent.putExtra("photo_genre",photo.getGenre());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final String appName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                    "market://details?id=" + appName)));
        }
        catch (Exception e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                    "https://play.google.com/store/apps/details?id=" + appName)));
        }

        return true;
    }

    private void remindReview(){
        Random random = new Random();

        if (random.nextInt(100) % 3 == 0){

            final AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this);
            builder.setMessage(getString(R.string.review));
            builder.setTitle(getString(R.string.like_app));
            builder.setCancelable(false);
            builder.setPositiveButton(getString(R.string.noter), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String appName = getPackageName();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                                "market://details?id=" + appName)));
                    }
                    catch (Exception e){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                                "https://play.google.com/store/apps/details?id=" + appName)));
                    }
                }
            });
            builder.setNegativeButton(getString(R.string.annuler), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }});
            builder.create();
            builder.show();
        }
    }
}