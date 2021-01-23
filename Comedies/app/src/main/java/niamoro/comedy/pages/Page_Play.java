package niamoro.comedy.pages;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import niamoro.comedy.R;
import niamoro.comedy.activities.ReplayShow;
import niamoro.comedy.adaptor.VideoListAdaptor;
import niamoro.comedy.db_manager.VideosManager;
import niamoro.comedy.utilis.Utils;
import niamoro.comedy.utilis.Video;

import static niamoro.comedy.utilis.Utils.NUMBER_PUB_SHOWN;
import static niamoro.comedy.utilis.Utils.NUMBER_VIDEOS_BEFORE_PUB;

public class Page_Play extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Video selectedVideo;

    private ListView listView;
    private VideoListAdaptor adaptor;
    private TextView titre;
    private TextView duree_vues;
    private TextView seeAlso;
    private ImageView share;
    private String link;
    private List<Video> videos;
    private View header;

    private ReplayShow context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_play,container,false);
        context = (ReplayShow)getActivity();
        initialization(view);
        init();
        NUMBER_PUB_SHOWN ++;
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        context.reload(videos.get(i-1).getVideo_id());
    }

    public void updateView(){
        if (!new Utils(context).isNetworkReachable())
            return;
        final String id = getArguments().getString("videoId");
        String database = "videos";
        final String column =  "vues";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(database).child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int views = dataSnapshot.child(column).getValue(Integer.class);
                    dataSnapshot.child(column).getRef().setValue(views+1);
                    try {
                        int day_views = dataSnapshot.child("today_views").getValue(Integer.class);
                        dataSnapshot.child("today_views").getRef().setValue(day_views + 1);
                    }
                    catch (Exception e){
                        dataSnapshot.child("today_views").getRef().setValue(1);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void playNext(){
        context.reload(videos.get(0).getVideo_id());
    }

    private void initialization(View view){
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.header_list_play_activity,listView,false);
        listView = view.findViewById(R.id.listseealso);
        titre = header.findViewById(R.id.nom);
        duree_vues = header.findViewById(R.id.duree_vues);
        share = header.findViewById(R.id.share);
        share.setOnClickListener(this);
        seeAlso = header.findViewById(R.id.see_also);
        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");
        titre.setTypeface(tf);
        duree_vues.setTypeface(tf);
        seeAlso.setTypeface(tf);
        videos = new VideosManager(context).getRandom(10);
        adaptor = new VideoListAdaptor(context, videos);
        listView.addHeaderView(header);
        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT,"https://www.youtube.com/watch?v=" + link +
                "\n\nRetrouvez plus de videos avec l'application Africa Comedy cliquez ici ==> " +
                "https://play.google.com/store/apps/details?id=" + context.getPackageName());
        startActivity(sendIntent);
    }

    private void init(){
        String id = getArguments().getString("videoId");
        String title;
        selectedVideo = new VideosManager(context).getVideo(id);
        link = selectedVideo.getLink();
        title = selectedVideo.getTitre();
        title = title.substring(0,1).toUpperCase() + title.substring(1);
        title = selectedVideo.getComedien() + " - " + title;
        title = Html.fromHtml(title).toString();
        titre.setText(title);
        String dv = selectedVideo.getVues()+" vues | "+ selectedVideo.getDuration();
        duree_vues.setText(dv);
        header.findViewById(R.id.details).setVisibility(View.VISIBLE);
        share.setVisibility(View.VISIBLE);
        context.setTopBar(title);

        YoutubeViewer ytubeFragment = (YoutubeViewer) getChildFragmentManager().findFragmentById(R.id.frame_youtube);
        Bundle arguments = new Bundle();
        arguments.putString("video",link);
        ytubeFragment.init(arguments);
    }
}