package afrimoov.gn.pages;

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

import afrimoov.gn.R;
import afrimoov.gn.activities.Accueil;
import afrimoov.gn.activities.ReplayShow;
import afrimoov.gn.adaptor.Pages_Adapter;
import afrimoov.gn.adaptor.ReplayRecyclerAdaptor;
import afrimoov.gn.adaptor.TvRecyclerAdaptor;
import afrimoov.gn.db_manager.ReplaysManager;
import afrimoov.gn.db_manager.TvsManager;
import afrimoov.gn.teaser_pages.Abonnement;
import afrimoov.gn.teaser_pages.Review_Us;
import afrimoov.gn.teaser_pages.Teaser;
import afrimoov.gn.utilis.Live_Tv;
import afrimoov.gn.utilis.Replay;
import afrimoov.gn.utilis.Utils;

public class Page_Accueil extends Fragment implements View.OnClickListener {

    private List<Live_Tv> tvs;
    private List<Replay> selection;
    private List<Replay> trending;
    private List<Replay> recents;

    private TvRecyclerAdaptor tv_adaptor;
    private ReplayRecyclerAdaptor populaire_adaptor;
    private ReplayRecyclerAdaptor recent_adaptor;
    private ReplayRecyclerAdaptor selection_adaptor;


    private RecyclerView recyclerTv;
    private RecyclerView recyclerPopulaire;
    private RecyclerView recyclerNew;
    private RecyclerView recyclerSelection;

    private TextView tv_title;
    private TextView populaire_title;
    private TextView nouveaute_title;
    private TextView favoris_title;
    private TextView voir_all_tv;

    private int currentPage = 0;
    private Runnable runnable;
    private AdView mAdview;


    private ReplaysManager manager;
    private SwipeRefreshLayout swiper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_accueil,null,true);
        manager = new ReplaysManager(getActivity());
        initialize(view);
        swiper = view.findViewById(R.id.swiper);

        mAdview = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        mAdview.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdview.setVisibility(View.VISIBLE);
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

        selection.addAll(manager.getRandom(5));
        trending.addAll(manager.getMorePopular(5));
        recents.addAll(manager.getMoreREcents(5));

        selection_adaptor.notifyDataSetChanged();
        recent_adaptor.notifyDataSetChanged();
        populaire_adaptor.notifyDataSetChanged();
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

        recyclerTv = view.findViewById(R.id.recyclertv);
        recyclerPopulaire = view.findViewById(R.id.recyclerpopulaire);
        recyclerSelection = view.findViewById(R.id.recyclerselection);
        recyclerNew = view.findViewById(R.id.recyclernew);

        recyclerTv.setLayoutManager(layouTv);
        recyclerPopulaire.setLayoutManager(layoutPopulaire);
        recyclerSelection.setLayoutManager(layoutSelection);
        recyclerNew.setLayoutManager(layoutRecent);


        tv_title = view.findViewById(R.id.tv_title);
        populaire_title = view.findViewById(R.id.populaire_title);
        nouveaute_title = view.findViewById(R.id.nouveaute_title);
        favoris_title = view.findViewById(R.id.favoris_title);

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
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}