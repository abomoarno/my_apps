package niamoro.comedy.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import niamoro.comedy.R;
import niamoro.comedy.activities.Accueil;
import niamoro.comedy.activities.ReplayShow;
import niamoro.comedy.adaptor.DayWeekMonthtAdaptor;
import niamoro.comedy.db_manager.VideosManager;
import niamoro.comedy.utilis.Video;

public class VideosContainer extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private DayWeekMonthtAdaptor adaptor;
    private List<Video> videos;
    private Accueil activity;

    private VideosManager manager;
    private String type = "";
    private String column = "";
    private String comedien_id = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_week_month,null,true);
        listView = view.findViewById(R.id.list_view);
        getVideos();
        adaptor = new DayWeekMonthtAdaptor(activity,videos);
        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Accueil)getActivity();
        manager = new VideosManager(activity);
        Bundle bundle = getArguments();
        if (bundle != null){
            type = bundle.getString("type","tendance");
            column = bundle.getString("colonne","day");
            comedien_id = bundle.getString("comedien","");
        }
        videos = new ArrayList<>();
    }

    private void getVideos(){
        videos.clear();
        switch (type){
            case "tendance":
                videos.addAll(manager.getMorePopular(25,column));
                break;
            case "selected":
                videos.addAll(manager.getFromComedien(comedien_id,column));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(activity, ReplayShow.class);
        intent.putExtra("videoId",videos.get(i).getVideo_id());
        startActivity(intent);
    }
}
