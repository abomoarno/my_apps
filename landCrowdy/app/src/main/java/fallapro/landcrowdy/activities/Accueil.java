package fallapro.landcrowdy.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.classes.Page_Accueil;
import fallapro.landcrowdy.classes.Page_Alerts;
import fallapro.landcrowdy.classes.Page_Compte;
import fallapro.landcrowdy.classes.Page_Connexion;
import fallapro.landcrowdy.classes.Page_New_Compte;
import fallapro.landcrowdy.classes.Page_Rechercher;
import fallapro.landcrowdy.classes.Pages_Adapter;
import fallapro.landcrowdy.utils.Utils;

public class Accueil extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener{

    private ViewPager viewPager;
    private int currentPage = 0;

    public ImageView accueil_image;
    public TextView accueil_text;
    public ImageView alerts_image;
    public TextView alerts_text;
    public ImageView compte_image;
    public TextView compte_text;
    public ImageView rechercher_image;
    public TextView rechercher_text;

    private NavigationView nav;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);
        setViewPager(getIntent().getIntExtra("fragment",0));
		
        findViewById(R.id.alerts).setOnClickListener(this);
        findViewById(R.id.compte).setOnClickListener(this);
        findViewById(R.id.rechercher).setOnClickListener(this);
        findViewById(R.id.accueil).setOnClickListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View header = nav.getHeaderView(0);

        header.findViewById(R.id.user_profil).setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.app_name));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.accueil:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rechercher:
                viewPager.setCurrentItem(1);
                break;
            case R.id.alerts:
                viewPager.setCurrentItem(2);
                break;
            case R.id.compte:
            case R.id.user_profil:
                viewPager.setCurrentItem(3);
                drawerLayout.closeDrawer(nav);
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager){
        Pages_Adapter adapter = new Pages_Adapter(getSupportFragmentManager());
        adapter.addFragment(new Page_Accueil());
        adapter.addFragment(new Page_Rechercher());
        adapter.addFragment(new Page_Alerts());
        adapter.addFragment(new Page_Compte());
        adapter.addFragment(new Page_Connexion());
        adapter.addFragment(new Page_New_Compte());

        accueil_text = findViewById(R.id.accueil_text);
        accueil_image = findViewById(R.id.accueil_image);

        alerts_text = findViewById(R.id.alerts_text);
        alerts_image = findViewById(R.id.alerts_image);

        compte_text = findViewById(R.id.compte_text);
        compte_image = findViewById(R.id.compte_image);

        rechercher_text = findViewById(R.id.rechercher_text);
        rechercher_image = findViewById(R.id.rechercher_image);

        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int num){
        viewPager.setCurrentItem(num);
    }

    public void activeColor(int fragment){

        switch (fragment){

            case 0:
                accueil_text.setTextColor(getResources().getColor(R.color.blu_title_donnees_personnelles));
                accueil_image.setImageDrawable(getResources().getDrawable(R.drawable.home_selected));
                break;
            case 1:
                rechercher_text.setTextColor(getResources().getColor(R.color.blu_title_donnees_personnelles));
                rechercher_image.setImageDrawable(getResources().getDrawable(R.drawable.magnifier_active));
                break;
            case 2:
                alerts_text.setTextColor(getResources().getColor(R.color.blu_title_donnees_personnelles));
                alerts_image.setImageDrawable(getResources().getDrawable(R.drawable.alarm_footer));
                break;
            case 3:
            case 4:
            case 5:
                compte_text.setTextColor(getResources().getColor(R.color.blu_title_donnees_personnelles));
                compte_image.setImageDrawable(getResources().getDrawable(R.drawable.profile_footer));
                break;
        }
    }

    public void desactiveColor(int fragment){

        switch (fragment){

            case 0:
                accueil_text.setTextColor(Color.BLACK);
                accueil_image.setImageDrawable(getResources().getDrawable(R.drawable.home));
                break;
            case 1:
                rechercher_text.setTextColor(Color.BLACK);
                rechercher_image.setImageDrawable(getResources().getDrawable(R.drawable.magnifier));
                break;
            case 2:
                alerts_text.setTextColor(Color.BLACK);
                alerts_image.setImageDrawable(getResources().getDrawable(R.drawable.alarm));
                break;
            case 3:
            case 4:
            case 5:
                compte_text.setTextColor(Color.BLACK);
                compte_image.setImageDrawable(getResources().getDrawable(R.drawable.profile));
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        desactiveColor(currentPage);
        activeColor(i);
        currentPage = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.menu_alerts:
                setViewPager(2);
                break;
            case R.id.menu_rechercher:
                setViewPager(1);
                break;
            case R.id.menu_changer_pays:
                startActivity(new Intent(getApplicationContext(),ChoixPays.class));
                break;
            case R.id.menu_quitter:
                finish();
                break;
        }

        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!Utils.USER_IS_CONNECTED) {
            startActivity(new Intent(getApplicationContext(), Presentation.class));
            finish();
        }
    }
}