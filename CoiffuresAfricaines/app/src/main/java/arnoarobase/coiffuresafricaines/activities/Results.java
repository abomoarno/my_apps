package arnoarobase.coiffuresafricaines.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import arnoarobase.coiffuresafricaines.R;
import arnoarobase.coiffuresafricaines.adaptors.PageAdapter;
import arnoarobase.coiffuresafricaines.pages.Femmes;
import arnoarobase.coiffuresafricaines.pages.Hommes;

public class Results extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ViewPager container;
    private AdView mAdView;
    private String parameter;
    private int bothSingle;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        parameter = getIntent().getStringExtra("parameter");
        bothSingle = getIntent().getIntExtra("bothSingle",0);
        container = findViewById(R.id.container);
        container.addOnPageChangeListener(this);
        if (bothSingle == 0){
            initBothFragments();
        }
        else
            initSingleFragment();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        mAdView.loadAd(adRequest);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initBothFragments(){
        Femmes femmes = new Femmes();
        Hommes hommes = new Hommes();
        Bundle bundle = new Bundle();
        bundle.putString("parameter",parameter);
        hommes.setArguments(bundle);
        femmes.setArguments(bundle);
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        adapter.addFragment(hommes,getString(R.string.hommes));
        adapter.addFragment(femmes,getString(R.string.femmes));
        container.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(container);
        tabLayout.setVisibility(View.VISIBLE);
    }

    private void initSingleFragment(){
        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString("parametre","");

        if (bothSingle == 1){
            fragment = new Hommes();
        }
        else
            fragment = new Femmes();

        fragment.setArguments(bundle);

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        adapter.addFragment(fragment);
        container.setAdapter(adapter);
    }

    public void setTitre(String titre){
        actionBar.setTitle(titre);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0){
            setTitre(getString(R.string.men));
        }
        else
            setTitre(getString(R.string.women));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }
}
