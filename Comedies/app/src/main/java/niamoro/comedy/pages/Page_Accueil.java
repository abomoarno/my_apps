package niamoro.comedy.pages;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import niamoro.comedy.R;
import niamoro.comedy.activities.Accueil;
import niamoro.comedy.activities.ReplayShow;
import niamoro.comedy.adaptor.ComedienRecyclerAdaptor;
import niamoro.comedy.adaptor.DayWeekMonthtAdaptor;
import niamoro.comedy.db_manager.ComediensManager;
import niamoro.comedy.db_manager.VideosManager;
import niamoro.comedy.utilis.Comedien;
import niamoro.comedy.utilis.Video;

public class Page_Accueil extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private List<Comedien> comediens;
    private List<Video> selection;

    private ComedienRecyclerAdaptor comedien_adaptor;
    private DayWeekMonthtAdaptor adaptor;

    private RecyclerView recyclerComedien;

    private TextView comedien_title;
    private TextView favoris_title;
    private TextView voir_all_tv;


    private ListView listView;
    private View header;

    private VideosManager manager;
    private SwipeRefreshLayout swiper;

    private Accueil accueil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_accueil,null,true);
        listView = view.findViewById(R.id.list_view);
        header = inflater.inflate(R.layout.accueil_list_header,listView,false);
        accueil = (Accueil)getActivity();
        manager = new VideosManager(getActivity());
        initialize();
        listView.setOnItemClickListener(this);
        View emptyView = view.findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
        emptyView.findViewById(R.id.reload).setOnClickListener(this);

        swiper = view.findViewById(R.id.swiper);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                accueil.toggleDesable(false);
                swiper.setRefreshing(true);
                (new Handler()).postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        swiper.setRefreshing(false);
                        accueil.toggleDesable(true);

                    }
                },2000);

            }
        });
        return view;
    }

    private void refresh(){
        selection.clear();
        selection.addAll(manager.getMoreREcents(25));
        adaptor.notifyDataSetChanged();
        comediens.clear();
        comediens.addAll(new ComediensManager(accueil).getAll(5));
        comedien_adaptor.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        accueil.activateFooter(1);
        accueil.toggleIndicator(false);
        accueil.toogleIcone(true);
        accueil.toggleTitle(false);
        accueil.selectedPage = 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.voir_all_tv:
                accueil.setFragment(new Home_Comediens());
                break;
            case R.id.reload:
                refresh();
                break;
        }
    }

    private void initialize(){
        LinearLayoutManager layouTv = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerComedien = header.findViewById(R.id.recyclercomediens);
        recyclerComedien.setLayoutManager(layouTv);
        comedien_title = header.findViewById(R.id.comediens_title);

        favoris_title = header.findViewById(R.id.favoris_title);

        voir_all_tv = header.findViewById(R.id.voir_all_tv);

        Typeface tf = Typeface.createFromAsset(accueil.getAssets(),"fonts/opensans_regular.ttf");
        comedien_title.setTypeface(tf);

        favoris_title.setTypeface(tf);
        voir_all_tv.setTypeface(tf);

        comediens = new ComediensManager(getActivity()).getAll(5);
        selection = manager.getMoreREcents(20);

        adaptor = new DayWeekMonthtAdaptor(accueil,selection);
        listView.addHeaderView(header);
        listView.setAdapter(adaptor);

        comedien_adaptor = new ComedienRecyclerAdaptor(accueil, comediens, new ComedienRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Comedien com) {
                Comedien_Selected selected = new Comedien_Selected();
                Bundle bundle = new Bundle();
                bundle.putString("comedien", com.getComedien_id());
                bundle.putString("comedien_nom", com.getNom());
                selected.setArguments(bundle);
                accueil.setFragment(selected);
            }
        });
        recyclerComedien.setAdapter(comedien_adaptor);

        voir_all_tv.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        accueil.desactivateFooter(1);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(accueil, ReplayShow.class);
        intent.putExtra("videoId",selection.get(i-1).getVideo_id());
        startActivity(intent);
    }
}