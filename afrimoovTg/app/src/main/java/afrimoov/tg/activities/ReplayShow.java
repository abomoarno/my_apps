package afrimoov.tg.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.dailymotion.android.player.sdk.PlayerWebView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import afrimoov.tg.R;
import afrimoov.tg.adaptor.ReplayListAdaptor;
import afrimoov.tg.alerts.AlarmInitialisation;
import afrimoov.tg.db_manager.ReplaysManager;
import afrimoov.tg.db_manager.TvsManager;
import afrimoov.tg.pages.DailyMotionViewer;
import afrimoov.tg.pages.IframeViewer;
import afrimoov.tg.pages.VideoViewer;
import afrimoov.tg.pages.YoutubeViewer;
import afrimoov.tg.utilis.Live_Tv;
import afrimoov.tg.utilis.Replay;
import afrimoov.tg.utilis.Utils;

import static afrimoov.tg.utilis.Utils.NUMBER_VIDEOS_BEFORE_PUB;

public class ReplayShow extends YouTubeBaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private Replay selectedReplay;
    private Live_Tv selectedTv;
    public static int NUMBER_PUB_SHOWN = 0;
    private static InterstitialAd playInterst;
    private static InterstitialAd playInterstVideo;
    private ListView listView;
    private ReplayListAdaptor adaptor;
    private LinearLayout mActionLayout;
    private TextView titre;
    private TextView duree_vues;
    private TextView seeAlso;
    private ImageView share;
    private boolean mFullscreen = false;
    private Toolbar mToolbar;
    private ActionBar actionBar;
    private String link;
    private String plateforme;
    private List<Replay> replays;
    private View header;
    private AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_replay);
        LayoutInflater inflater = getLayoutInflater();
        header = inflater.inflate(R.layout.header,listView,false);
        listView = findViewById(R.id.listseealso);
        titre = header.findViewById(R.id.titre);
        duree_vues = header.findViewById(R.id.duree_vues);
        share = header.findViewById(R.id.share);
        share.setOnClickListener(this);
        seeAlso = header.findViewById(R.id.see_also);
        mActionLayout = findViewById(R.id.toggleLayout);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/opensans_regular.ttf");
        titre.setTypeface(tf);
        duree_vues.setTypeface(tf);
        seeAlso.setTypeface(tf);

        replays = new ReplaysManager(this).getRandom(5);
        adaptor = new ReplayListAdaptor(this,replays);

        listView.addHeaderView(header);
        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(this);

        mToolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(mToolbar);
        }
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            }
            actionBar = getActionBar();

            if (actionBar !=  null) {
                actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        if ((NUMBER_VIDEOS_BEFORE_PUB != 0) && ((NUMBER_PUB_SHOWN % NUMBER_VIDEOS_BEFORE_PUB) == 0) && !Utils.ADS_REMOVED) {
            showAds();
        }
        else if(NUMBER_VIDEOS_BEFORE_PUB == 0)
            NUMBER_VIDEOS_BEFORE_PUB = 3;
        init();
        switch (plateforme) {
            case "youtube":
                setFragment(new YoutubeViewer());
                break;
            case "dailymotion":
                setFragment(new DailyMotionViewer());
                break;
            case "iframe":
                setFragment(new IframeViewer());
                break;
            default:
                setFragment(new VideoViewer());
                break;
        }
        NUMBER_PUB_SHOWN++;
        mAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        mAdview.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                mAdview.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT,"https://www.youtube.com/watch?v=" + link +
                "\n\nRetrouvez plus de videos avec l'application TV Guinée cliquez ici ==> " +
                "https://play.google.com/store/apps/details?id=" + getPackageName());
        startActivity(sendIntent);
    }

    public String getLink(){
        return link;
    }

    public void setFragment(android.app.Fragment fragment){
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }

    private void setFullScreenInternal(boolean fullScreen,PlayerWebView mVideoView) {
        mFullscreen = fullScreen;
        if (mActionLayout != null) {
            if (mFullscreen) {
                mActionLayout.setVisibility(View.GONE);
            } else {
                mActionLayout.setVisibility(View.VISIBLE);
            }
        }
        mVideoView.setFullscreenButton(mFullscreen);
    }

    private void setFullScreenInternal(boolean fullScreen) {
        mFullscreen = fullScreen;
        if (mActionLayout != null) {
            if (mFullscreen) {
                mActionLayout.setVisibility(View.GONE);
            } else {
                mActionLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onFullScreenToggleRequested(PlayerWebView mVideoView) {
        setFullScreenInternal(!mFullscreen,mVideoView);

        if (mFullscreen) {
            mToolbar.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    public void onFullScreenToggleRequested() {
        setFullScreenInternal(!mFullscreen);

        if (mFullscreen) {
            mToolbar.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    private void init(){
        plateforme = getIntent().getStringExtra("videoPlateforme");
        String id = getIntent().getStringExtra("videoId");
        String type = getIntent().getStringExtra("videoType");
        String title;
        switch (type){
            case "replay":
                selectedReplay = new ReplaysManager(this).getReplay(id);
                link = selectedReplay.getLink();
                title = selectedReplay.getTitle();
                title = title.substring(0,1).toUpperCase() + title.substring(1);
                titre.setText(title);
                titre.setText(title);
                String dv = selectedReplay.getViews()+" vues | "+selectedReplay.getDuree();
                duree_vues.setText(dv);
                header.findViewById(R.id.details).setVisibility(View.VISIBLE);
                share.setVisibility(View.VISIBLE);
                if (actionBar != null){
                    actionBar.setTitle(title);
                }
                break;
            case "live":
                selectedTv = new TvsManager(this).getTv(id);
                link = selectedTv.getLien();
                title = selectedTv.getNom();
                title = title.substring(0,1).toUpperCase() + title.substring(1);
                titre.setText(title);
                header.findViewById(R.id.details).setVisibility(View.GONE);
                share.setVisibility(View.GONE);
                if (actionBar != null){
                    actionBar.setTitle(title);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        reload(replays.get(i-1));
    }
    private void reload(Replay replay){
        Intent intent = getIntent();
        intent.putExtra("videoId",replay.getReplay_id());
        intent.putExtra("videoPlateforme",replay.getPlateforme());
        intent.putExtra("videoType","replay");
        recreate();
    }

    public static void initAds(final Context context,boolean remove_pubs){
        if (remove_pubs)
            return;
        playInterst = new InterstitialAd(context);
        playInterstVideo = new InterstitialAd(context);
        playInterst.setAdUnitId("ca-app-pub-2082216950067064/2202006212");
        playInterstVideo.setAdUnitId("ca-app-pub-2082216950067064/6496474448");
        playInterst.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                loadAds();
            }
        });
        playInterstVideo.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                loadAds();
            }
        });
        loadAds();
    }

    public static void loadAds( ){
        if (! playInterst.isLoaded())
            playInterst.loadAd(new AdRequest.Builder().build());
        if (! playInterstVideo.isLoaded())
            playInterstVideo.loadAd(new AdRequest.Builder().build());
    }

    private void showAds(){
        if(playInterstVideo.isLoaded())
            playInterstVideo.show();
        else if(playInterst.isLoaded())
            playInterst.show();
    }

    public void updateView(){
        if (!new Utils(this).isNetworkReachable())
            return;
        final String id = getIntent().getStringExtra("videoId");
        String type = getIntent().getStringExtra("videoType");

        String database = (type.equals("replay")) ? "videos" : "liveTvs";
        final String column = (type.equals("replay")) ? "vues" : "live_vues";
        final boolean isReplay = (type.equals("replay"));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(database).child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int views = dataSnapshot.child(column).getValue(Integer.class);
                    dataSnapshot.child(column).getRef().setValue(views+1);

                    Log.w(id,(views+1) + " views");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!Utils.ADS_REMOVED)
            AlarmInitialisation.clearPubInterval(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Utils.ADS_REMOVED)
            AlarmInitialisation.initPubInterval(this);
    }

    public void playNext(){
        reload(replays.get(0));
    }

    public boolean getFullscreen(){
        return mFullscreen;
    }
}