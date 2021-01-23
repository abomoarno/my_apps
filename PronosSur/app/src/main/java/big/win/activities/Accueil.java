package big.win.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import big.win.R;
import big.win.classes.AlarmInitialisation;
import big.win.pages.AvantagesGoal;
import big.win.pages.AvantagesPremium;
import big.win.pages.Bonus;
import big.win.pages.Free;
import big.win.pages.Goal;
import big.win.pages.Premium;

import static big.win.classes.Utils.GOAL_GOAL_STATUS;
import static big.win.classes.Utils.PREMIUM_STATUS;

public class Accueil extends AppCompatActivity implements View.OnClickListener{

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    public ImageView free_image;
    public TextView free_text;
    public ImageView bonus_image;
    public TextView bonus_text;
    public ImageView premium_image;
    public TextView premium_text;
    public ImageView goal_image;
    public TextView goal_text;

    private int selectedPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        free_text = findViewById(R.id.free_text);
        free_image = findViewById(R.id.free_image);

        bonus_text = findViewById(R.id.bonus_text);
        bonus_image = findViewById(R.id.bonus_image);

        premium_text = findViewById(R.id.premium_text);
        premium_image = findViewById(R.id.premium_image);

        goal_text = findViewById(R.id.goal_text);
        goal_image = findViewById(R.id.goal_image);

        findViewById(R.id.free).setOnClickListener(this);
        findViewById(R.id.bonus).setOnClickListener(this);
        findViewById(R.id.premium).setOnClickListener(this);
        findViewById(R.id.goal).setOnClickListener(this);

        setFragment(new Free());

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
            }
        });

        initdAds(this);
        loadAds();
    }

    public void activateFooter(int number){
        if (number == selectedPage)
            return;
        desactivateFooter(selectedPage);
        switch (number){
            case 1:
                free_text.setVisibility(View.VISIBLE);
                free_image.setImageDrawable(getResources().getDrawable(R.drawable.free_selected));
                break;
            case 2:
                bonus_text.setVisibility(View.VISIBLE);
                bonus_image.setImageDrawable(getResources().getDrawable(R.drawable.bonus_selected));
                break;
            case 3:
            case 5:
            case 7:
                premium_text.setVisibility(View.VISIBLE);
                premium_image.setImageDrawable(getResources().getDrawable(R.drawable.premium_selected));
                break;
            case 4:
            case 6:
            case 8:
                goal_text.setVisibility(View.VISIBLE);
                goal_image.setImageDrawable(getResources().getDrawable(R.drawable.goal_selected));
                break;
        }
        selectedPage = number;
    }

    private void desactivateFooter(int number){
        switch (number){
            case 1:
                free_text.setVisibility(View.GONE);
                free_image.setImageDrawable(getResources().getDrawable(R.drawable.free_unselected));
                break;
            case 2:
                bonus_text.setVisibility(View.GONE);
                bonus_image.setImageDrawable(getResources().getDrawable(R.drawable.bonus_unselected));
                break;
            case 3:
            case 5:
            case 7:
                premium_text.setVisibility(View.GONE);
                premium_image.setImageDrawable(getResources().getDrawable(R.drawable.premium_unselected));
                break;
            case 4:
            case 6:
            case 8:
                goal_text.setVisibility(View.GONE);
                goal_image.setImageDrawable(getResources().getDrawable(R.drawable.goal_unselected));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        AlarmInitialisation.initAll(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.free:
                setFragment(new Free());
                break;
            case R.id.bonus:
                if (mInterstitialAd.isLoaded())
                    mInterstitialAd.show();
                setFragment(new Bonus());
                break;
            case R.id.premium:
                setFragment((PREMIUM_STATUS) ? (new Premium()) : new AvantagesPremium());
                break;
            case R.id.goal:
                setFragment((GOAL_GOAL_STATUS) ? (new Goal()) : new AvantagesGoal());
                break;
        }
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.commit();
    }


    public void initdAds(final Context context){
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //mInterstitialAd.setAdUnitId("ca-app-pub-8287372312965391/2065470268");
    }
    public void loadAds(){
        if (! mInterstitialAd.isLoaded())
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void setTitre(String titre){

    }
}