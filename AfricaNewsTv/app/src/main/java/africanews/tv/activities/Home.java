package africanews.tv.activities;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import africanews.tv.R;
import africanews.tv.adapters.PageAdapter;
import africanews.tv.fragments.fragments_home.Cinema;
import africanews.tv.fragments.fragments_home.News;
import africanews.tv.fragments.fragments_home.Others;
import africanews.tv.fragments.fragments_home.Sports;

public class Home extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private PageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tablayout);

        adapter = new PageAdapter(getSupportFragmentManager());

        adapter.addFragment(new News(), "Infos");
        adapter.addFragment(new Sports(), "Sports");
        adapter.addFragment(new Cinema(), "Films");
        adapter.addFragment(new Others(), "Autres");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
