package niamoro.comedy.pages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import niamoro.comedy.R;

import niamoro.comedy.activities.Accueil;
import niamoro.comedy.adaptor.Pages_Adapter;

public class Tendances extends Fragment {
    private Pages_Adapter adapter;
    private ViewPager viewPager;
    Accueil activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tendances,null,true);
        viewPager = view.findViewById(R.id.container);
        adapter = new Pages_Adapter(getFragmentManager());
        setupViewPager(viewPager);
        activity = (Accueil)getActivity();
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager){
        Pages_Adapter adapter = new Pages_Adapter(getFragmentManager());

        VideosContainer container = new VideosContainer();
        VideosContainer container2 = new VideosContainer();
        VideosContainer container3 = new VideosContainer();

        Bundle args = new Bundle();
        Bundle args2 = new Bundle();
        Bundle args3 = new Bundle();

        args.putString("type","tendance");
        args2.putString("type","tendance");
        args3.putString("type","tendance");

        args.putString("colonne","day");
        container.setArguments(args);
        adapter.addFragment(container,getString(R.string.today));

        args2.putString("colonne","week");
        container2.setArguments(args2);
        adapter.addFragment(container2,getString(R.string.this_week));

        args3.putString("colonne","month");
        container3.setArguments(args3);
        adapter.addFragment(container3,getString(R.string.this_month));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (activity != null){
            activity.activateFooter(2);
            activity.toggleIndicator(false);
            activity.selectedPage = 2;
            activity.toogleIcone(true);
            activity.toggleTitle(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (activity != null){
            activity.desactivateFooter(2);
        }
    }
}
