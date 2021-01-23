package afrimoov.nigeria.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

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
import afrimoov.nigeria.R;
import afrimoov.nigeria.adaptor.ReplayListAdaptor;
import afrimoov.nigeria.alerts.AlarmInitialisation;
import afrimoov.nigeria.db_manager.ReplaysManager;
import afrimoov.nigeria.db_manager.TvsManager;
import afrimoov.nigeria.pages.DailyMotionViewer;
import afrimoov.nigeria.pages.VideoViewer;
import afrimoov.nigeria.pages.YoutubeViewer;
import afrimoov.nigeria.utilis.Live_Tv;
import afrimoov.nigeria.utilis.Replay;
import afrimoov.nigeria.utilis.Utils;

import static afrimoov.nigeria.utilis.Utils.NUMBER_VIDEOS_BEFORE_PUB;

public class ReplayShow extends YouTubeBaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    public static int NUMBER_PUB_SHOWN = 0;
    private static InterstitialAd playInterst;
    private static InterstitialAd playInterstVideo;
    private ListView listView;
    private LinearLayout mActionLayout;
    private TextView titre;
    private TextView duree_vues;
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
        TextView seeAlso = header.findViewById(R.id.see_also);
        mActionLayout = findViewById(R.id.toggleLayout);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/opensans_regular.ttf");
        titre.setTypeface(tf);
        duree_vues.setTypeface(tf);
        seeAlso.setTypeface(tf);

        replays = new ReplaysManager(this).getRandom(10);
        ReplayListAdaptor adaptor = new ReplayListAdaptor(this, replays);

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
        if ((NUMBER_VIDEOS_BEFORE_PUB != 0) && ((NUMBER_PUB_SHOWN % NUMBER_VIDEOS_BEFORE_PUB) == 0)) {
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
            default:
                setFragment(new VideoViewer());
                break;
        }
        NUMBER_PUB_SHOWN++;
        if (!plateforme.equals("youtube")) {
            mAdview = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdview.loadAd(adRequest);
            mAdview.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mAdview.setVisibility(View.VISIBLE);
                }
            });
        }
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
                Replay selectedReplay = new ReplaysManager(this).getReplay(id);
                link = selectedReplay.getLink();
                title = selectedReplay.getTitle();
                title = title.substring(0,1).toUpperCase() + title.substring(1);
                titre.setText(title);
                titre.setText(title);
                String dv = selectedReplay.getViews()+" vues | "+ selectedReplay.getDuree();
                duree_vues.setText(dv);
                header.findViewById(R.id.details).setVisibility(View.VISIBLE);
                share.setVisibility(View.VISIBLE);
                if (actionBar != null){
                    actionBar.setTitle(title);
                }
                break;
            case "live":
                Live_Tv selectedTv = new TvsManager(this).getTv(id);
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
        onBackPressed();
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
        intent.removeExtra("from");
        recreate();
    }

    public static void initAds(final Context context){
        playInterst = new InterstitialAd(context);
        playInterstVideo = new InterstitialAd(context);
        playInterst.setAdUnitId(Utils.INTERST_IMAGE_KEY);
        playInterstVideo.setAdUnitId(Utils.INTERST_VIDEO_KEY);
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

        AdRequest adRequest = new AdRequest.Builder().build();

        if (! playInterst.isLoaded())
            playInterst.loadAd(adRequest);
        if (! playInterstVideo.isLoaded())
            playInterstVideo.loadAd(adRequest);
    }

    private void showAds(){
        if (getIntent().getStringExtra("from")!=null)
            return;
        if(playInterstVideo != null && playInterstVideo.isLoaded())
            playInterstVideo.show();
        else if(playInterst != null && playInterst.isLoaded())
            playInterst.show();
    }

    public void updateView(){
        if (!new Utils(this).isNetworkReachable())
            return;

        final String id = getIntent().getStringExtra("videoId");
        final String type = getIntent().getStringExtra("videoType");
        String database = (type.equals("replay")) ? "videos" : "liveTvs";
        final String column = (type.equals("replay")) ? "vues" : "live_vues";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(database).child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int views = dataSnapshot.child(column).getValue(Integer.class);
                    dataSnapshot.child(column).getRef().setValue(views+1);
                    if (type.equals("replay"))
                        new ReplaysManager(getApplicationContext()).updateViews(id,views+1);
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
        AlarmInitialisation.clearPubInterval(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlarmInitialisation.initPubInterval(getApplicationContext());
    }

    public void playNext(){
        reload(replays.get(0));
    }

    public boolean getFullscreen(){
        return mFullscreen;
    }

    @Override
    public void onBackPressed() {
        String from = getIntent().getStringExtra("from");
        if (from != null){
            startActivity(new Intent(this,Accueil.class));
            finish();
        }
        else
            super.onBackPressed();
    }
}