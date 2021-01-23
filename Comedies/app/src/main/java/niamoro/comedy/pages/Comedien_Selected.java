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

public class Comedien_Selected extends Fragment{

    private Pages_Adapter adapter;
    private ViewPager viewPager;
    private Accueil activity;

    private String comedian_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tendances,null,true);
        viewPager = view.findViewById(R.id.container);
        activity = (Accueil)getActivity();
        activity.setTitre(getArguments().getString("comedien_nom"));
        comedian_id = getArguments().getString("comedien");
        adapter = new Pages_Adapter(getFragmentManager());
        setupViewPager(viewPager);
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

        args.putString("comedien",comedian_id);
        args.putString("type","selected");

        args2.putString("comedien",comedian_id);
        args2.putString("type","selected");

        args3.putString("comedien",comedian_id);
        args3.putString("type","selected");

        args.putString("colonne","date");
        container.setArguments(args);
        adapter.addFragment(container,getString(R.string.new_videos));

        args2.putString("colonne","views");
        container2.setArguments(args2);
        adapter.addFragment(container2,getString(R.string.populaire));

        args3.putString("colonne","all");
        container3.setArguments(args3);
        adapter.addFragment(container3,getString(R.string.all));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (activity != null){
            activity.activateFooter(3);
            activity.toggleIndicator(true);
            activity.selectedPage = 5;
            activity.toogleIcone(false);
            activity.toggleTitle(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (activity != null){
            activity.desactivateFooter(3);
        }
    }
}
