package niamoro.annonces.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import niamoro.annonces.R;
import niamoro.annonces.adapters.PageAdapter;
import niamoro.annonces.pages.pages_presentation.CreerAlerts;
import niamoro.annonces.pages.pages_presentation.Personnaliser;

public class Presentation extends AppCompatActivity {

    private ViewPager viewPager;
    private SpringDotsIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation);

        viewPager = findViewById(R.id.container);

        indicator = findViewById(R.id.spring_dots_indicator);

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());

        adapter.addFragment(new Personnaliser());
        adapter.addFragment(new CreerAlerts());
        viewPager.setAdapter(adapter);

        indicator.setViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
