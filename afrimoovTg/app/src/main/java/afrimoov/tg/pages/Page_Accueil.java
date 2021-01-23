package afrimoov.tg.pages;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import afrimoov.tg.R;
import afrimoov.tg.activities.Accueil;
import afrimoov.tg.activities.ReplayShow;
import afrimoov.tg.adaptor.Pages_Adapter;
import afrimoov.tg.adaptor.ReplayRecyclerAdaptor;
import afrimoov.tg.adaptor.TvRecyclerAdaptor;
import afrimoov.tg.db_manager.ReplaysManager;
import afrimoov.tg.db_manager.TvsManager;
import afrimoov.tg.teaser_pages.Abonnement;
import afrimoov.tg.teaser_pages.Review_Us;
import afrimoov.tg.teaser_pages.Teaser;
import afrimoov.tg.utilis.Live_Tv;
import afrimoov.tg.utilis.Replay;
import afrimoov.tg.utilis.Utils;

public class Page_Accueil extends Fragment implements View.OnClickListener {

    private List<Live_Tv> tvs;
    private List<Replay> selection;
    private List<Replay> trending;
    private List<Replay> recents;
    private List<Replay> buzz;
    private List<Replay> sport;

    private TvRecyclerAdaptor tv_adaptor;
    private ReplayRecyclerAdaptor populaire_adaptor;
    private ReplayRecyclerAdaptor recent_adaptor;
    private ReplayRecyclerAdaptor selection_adaptor;
    private ReplayRecyclerAdaptor buzz_adaptor;
    private ReplayRecyclerAdaptor sport_adaptor;

    private RecyclerView recyclerTv;
    private RecyclerView recyclerPopulaire;
    private RecyclerView recyclerNew;
    private RecyclerView recyclerSelection;
    private RecyclerView recyclerBuzz;
    private RecyclerView recyclerSport;


    private TextView tv_title;
    private TextView populaire_title;
    private TextView nouveaute_title;
    private TextView favoris_title;
    private TextView buzz_title;
    private TextView sport_title;
    private TextView voir_all_tv;

    private Handler handler;
    private int delay = 6000;
    private int direction = 1;

    private ViewPager viewPager;
    private int currentPage = 0;
    private Runnable runnable;
    private AdView mAdview;
    private AdView mAdview2;

    private ReplaysManager manager;
    private SwipeRefreshLayout swiper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_accueil,null,true);
        manager = new ReplaysManager(getActivity());
        initialize(view);
        setupViewPager(viewPager);
        mAdview = view.findViewById(R.id.adView);
        mAdview2 = view.findViewById(R.id.adView2);
        swiper = view.findViewById(R.id.swiper);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        mAdview.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                mAdview.setVisibility(View.VISIBLE);
            }
        });
        mAdview2.loadAd(adRequest);
        mAdview2.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {

                if (buzz.size()>0 || sport.size()>0)
                    mAdview2.setVisibility(View.VISIBLE);
            }
        });
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiper.setRefreshing(true);
                (new Handler()).postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        swiper.setRefreshing(false);
                    }
                },2000);

            }
        });
        return view;
    }

    private void refresh(){
        trending.clear();
        recents.clear();
        selection.clear();
        buzz.clear();
        sport.clear();

        selection.addAll(manager.getRandom(5));
        trending.addAll(manager.getMorePopular(5));
        recents.addAll(manager.getMoreREcents(5));
        buzz.addAll(manager.getFromTv("buzz"));
        sport.addAll(manager.getFromTv("sport"));

        if (sport.size()>5)
            sport = sport.subList(0,5);
        if (buzz.size()>5)
            buzz = buzz.subList(0,5);

        selection_adaptor.notifyDataSetChanged();
        recent_adaptor.notifyDataSetChanged();
        populaire_adaptor.notifyDataSetChanged();
        buzz_adaptor.notifyDataSetChanged();
        sport_adaptor.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        Accueil accueil = (Accueil)getActivity();
        if (accueil != null) {
            accueil.activateFooter(1);
            accueil.selectedPage = 1;
            accueil.getSupportActionBar().setTitle(accueil.getString(R.string.app_name));

        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Accueil accueil = (Accueil)getActivity();
        accueil.activateFooter(1);
        accueil.selectedPage = 1;
        accueil.getSupportActionBar().setTitle(accueil.getString(R.string.app_name));
    }

    @Override
    public void onClick(View v) {
        Accueil accueil = (Accueil)getActivity();
        accueil.setFragment(new All_Direct_Tv());
    }

    private void initialize(View view){
        LinearLayoutManager layouTv = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutPopulaire = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutSelection = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutRecent = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutBuzz = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutSport = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerTv = view.findViewById(R.id.recyclertv);
        recyclerPopulaire = view.findViewById(R.id.recyclerpopulaire);
        recyclerSelection = view.findViewById(R.id.recyclerselection);
        recyclerBuzz = view.findViewById(R.id.recyclerbuzz);
        recyclerSport = view.findViewById(R.id.recyclersport);
        recyclerNew = view.findViewById(R.id.recyclernew);

        recyclerTv.setLayoutManager(layouTv);
        recyclerPopulaire.setLayoutManager(layoutPopulaire);
        recyclerSelection.setLayoutManager(layoutSelection);
        recyclerNew.setLayoutManager(layoutRecent);
        recyclerBuzz.setLayoutManager(layoutBuzz);
        recyclerSport.setLayoutManager(layoutSport);

        tv_title = view.findViewById(R.id.tv_title);
        populaire_title = view.findViewById(R.id.populaire_title);
        nouveaute_title = view.findViewById(R.id.nouveaute_title);
        favoris_title = view.findViewById(R.id.favoris_title);
        buzz_title = view.findViewById(R.id.buzz_title);
        sport_title = view.findViewById(R.id.sport_title);

        viewPager = view.findViewById(R.id.container);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        voir_all_tv = view.findViewById(R.id.voir_all_tv);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/opensans_regular.ttf");
        tv_title.setTypeface(tf);
        nouveaute_title.setTypeface(tf);
        populaire_title.setTypeface(tf);
        favoris_title.setTypeface(tf);
        voir_all_tv.setTypeface(tf);

        tvs = new TvsManager(getActivity()).getLimit(6);

        trending = manager.getMorePopular(5);
        selection = manager.getRandom(5);
        recents = manager.getMoreREcents(5);

        buzz = manager.getFromTv("buzz");
        sport = manager.getFromTv("sport");

        if (sport.size()>5)
            sport = sport.subList(0,5);
        if (buzz.size()>5)
            buzz = buzz.subList(0,5);

        if (buzz.isEmpty()){
            buzz_title.setVisibility(View.GONE);
            recyclerBuzz.setVisibility(View.GONE);
        }
        if (sport.isEmpty()){
            sport_title.setVisibility(View.GONE);
            recyclerSport.setVisibility(View.GONE);
        }
        tv_adaptor = new TvRecyclerAdaptor(getActivity(), tvs, new TvRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Live_Tv tv) {
                Intent intent = new Intent(getActivity(), ReplayShow.class);
                intent.putExtra("videoId",tv.getTv_id());
                intent.putExtra("videoPlateforme",tv.getPlateforme());
                intent.putExtra("videoType","live");
                startActivity(intent);
            }
        });
        recyclerTv.setAdapter(tv_adaptor);

        populaire_adaptor = new ReplayRecyclerAdaptor(getActivity(), trending, new ReplayRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Replay replay) {
                Intent intent = new Intent(getActivity(), ReplayShow.class);
                intent.putExtra("videoId",replay.getReplay_id());
                intent.putExtra("videoPlateforme",replay.getPlateforme());
                intent.putExtra("videoType","replay");
                startActivity(intent);
            }
        });
        recyclerPopulaire.setAdapter(populaire_adaptor);

        recent_adaptor = new ReplayRecyclerAdaptor(getActivity(), recents, new ReplayRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Replay replay) {
                Intent intent = new Intent(getActivity(), ReplayShow.class);
                intent.putExtra("videoId",replay.getReplay_id());
                intent.putExtra("videoPlateforme",replay.getPlateforme());
                intent.putExtra("videoType","replay");
                startActivity(intent);
            }
        });
        recyclerNew.setAdapter(recent_adaptor);

        selection_adaptor = new ReplayRecyclerAdaptor(getActivity(), selection, new ReplayRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Replay replay) {
                Intent intent = new Intent(getActivity(), ReplayShow.class);
                intent.putExtra("videoId",replay.getReplay_id());
                intent.putExtra("videoPlateforme",replay.getPlateforme());
                intent.putExtra("videoType","replay");
                startActivity(intent);
            }
        });
        recyclerSelection.setAdapter(selection_adaptor);

        buzz_adaptor = new ReplayRecyclerAdaptor(getActivity(), buzz, new ReplayRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Replay replay) {
                Intent intent = new Intent(getActivity(), ReplayShow.class);
                intent.putExtra("videoId",replay.getReplay_id());
                intent.putExtra("videoPlateforme",replay.getPlateforme());
                intent.putExtra("videoType","replay");
                startActivity(intent);
            }
        });
        recyclerBuzz.setAdapter(buzz_adaptor);

        sport_adaptor = new ReplayRecyclerAdaptor(getActivity(), sport, new ReplayRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Replay replay) {
                Intent intent = new Intent(getActivity(), ReplayShow.class);
                intent.putExtra("videoId",replay.getReplay_id());
                intent.putExtra("videoPlateforme",replay.getPlateforme());
                intent.putExtra("videoType","replay");
                startActivity(intent);
            }
        });
        recyclerSport.setAdapter(sport_adaptor);

        voir_all_tv.setOnClickListener(this);
        handler = new Handler();
    }

    private void setupViewPager(final ViewPager viewPager){
        final Pages_Adapter adapter = new Pages_Adapter(getFragmentManager());
        adapter.addFragment(new Teaser());
        if (!Utils.ADS_REMOVED)
            adapter.addFragment(new Abonnement());
        adapter.addFragment(new Review_Us());

       // viewPager.addOnPageChangeListener(getActivity());
        viewPager.setAdapter(adapter);

        runnable = new Runnable() {
            @Override
            public void run() {

                if (adapter.getCount() == currentPage)
                    direction =-1;
                else if (currentPage == 0)
                    direction = 1;

                currentPage += direction;
                viewPager.setCurrentItem(currentPage,true);
                handler.postDelayed(this,delay);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,delay);
    }

    @Override
    public void onPause() {
        super.onPause();

        handler.removeCallbacks(runnable);
    }
}