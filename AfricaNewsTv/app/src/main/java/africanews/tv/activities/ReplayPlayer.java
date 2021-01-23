package africanews.tv.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dailymotion.android.player.sdk.PlayerWebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import africanews.tv.R;
import africanews.tv.adapters.ReplayListAdapter;
import africanews.tv.entities.Replay;
import africanews.tv.fragments.fragments_replayer_replays.DailyMotionPlayer;
import africanews.tv.fragments.fragments_replayer_replays.VideoPlayer;
import africanews.tv.fragments.fragments_replayer_replays.YoutubePlayer;
import africanews.tv.utils.Utils;

import static africanews.tv.utils.Utils.SERVER_RESULT;
import static africanews.tv.utils.Utils.decodeReplayFromObject;

public class ReplayPlayer extends AppCompatActivity implements View.OnClickListener {

    private List<Replay> replays;

    private ListView relatedVideos;
    private ReplayListAdapter adapter;

    private Fragment player;

    private boolean isFullScreen;

    private LinearLayout related_videos_title;

    public ProgressDialog dialog;

    private Replay selectedReplay;

    private ActionBar mActionBar;
    private Toolbar mToolbar;
    private TextView titre, channel, date_pub;

    private ImageView bookmarked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay_player);

        relatedVideos = findViewById(R.id.related_videos);
        related_videos_title = findViewById(R.id.bloc_title_related_videos);
        titre = findViewById(R.id.titre_video);
        channel = findViewById(R.id.titre_channel);
        date_pub = findViewById(R.id.date_pub);
        bookmarked.setOnClickListener(this);

        findViewById(R.id.share).setOnClickListener(this);

        replays = new ArrayList<>();

        adapter = new ReplayListAdapter(this, replays);

        relatedVideos.setAdapter(adapter);

        relatedVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectedReplay = replays.get(i);

                playReplay();

            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle("Chargement");
        dialog.setCancelable(false);
        dialog.setMessage("Veuillez patienter pendant que le système charge les informations ...");

        selectedReplay = getIntent().getParcelableExtra("replay");

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mActionBar = getSupportActionBar();

        if (mActionBar != null)
            mActionBar.setDisplayHomeAsUpEnabled(true);

        playReplay();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private void getRelatedReplays(){

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String data = intent.getStringExtra(SERVER_RESULT);

                Log.e("SERVER RESPONSE",data);

                try {
                    JSONArray array = new JSONArray(data);
                    replays.clear();

                    for (int position=0;position<array.length();position++){
                        JSONObject object = array.getJSONObject(position);

                        Replay replay = decodeReplayFromObject(object, getApplicationContext());

                        replays.add(replay);
                    }
                    adapter.notifyDataSetChanged();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    dialog.dismiss();
                    context.unregisterReceiver(this);
                }
            }
        }, new IntentFilter("get_related_replays"));

        String url = "http://192.168.1.114/crtvnews/server_responses/enter_point.php?sce=replays&rep_cmd=get_replays_for_replay";


        new Utils.SendToServer(this).execute(
                url + "&rep_id=" + selectedReplay.getReplay_id(), "get_related_replays"
        );

    }

    private void addPlayer(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.player,player);
        ft.commit();
    }

    private void playReplay(){
        if (!dialog.isShowing())
            dialog.show();

        switch (selectedReplay.getPlateform()){
            case "youtube":
                player = new YoutubePlayer();
                break;
            case "dailymotion":
                player = new DailyMotionPlayer();
                break;
            default:
                player = new VideoPlayer();
                break;
        }

        mActionBar.setTitle(selectedReplay.getTitle());
        mActionBar.setSubtitle(selectedReplay.getChannel_name());
        titre.setText(selectedReplay.getTitle());
        channel.setText(selectedReplay.getChannel_name());
        date_pub.setText(selectedReplay.getQuery_date());

        Bundle args = new Bundle();

        args.putParcelable("replay", selectedReplay);

        player.setArguments(args);

        addPlayer();

        getRelatedReplays();

    }

    public void onFullScreenToggleRequested(PlayerWebView mVideoView) {
        setFullScreenInternal(!isFullScreen,mVideoView);

        if (isFullScreen) {
            toggleToolBar(false);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            toggleToolBar(true);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    public void onFullScreenToggleRequested() {
        setFullScreenInternal(!isFullScreen);

        if (isFullScreen) {
           toggleToolBar(false);
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            toggleToolBar(true);
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    private void setFullScreenInternal(boolean fullScreen,PlayerWebView mVideoView) {
        toggleAllViews();
        isFullScreen = fullScreen;
        mVideoView.setFullscreenButton(isFullScreen);
    }

    private void setFullScreenInternal(boolean fullScreen) {
        toggleAllViews();
        isFullScreen = fullScreen;
    }

    private void toggleAllViews(){
        related_videos_title.setVisibility((isFullScreen) ? View.VISIBLE : View.GONE);
        relatedVideos.setVisibility((isFullScreen) ? View.VISIBLE : View.GONE);
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void toggleToolBar(boolean visibility){
        mToolbar.setVisibility((visibility) ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }

    public void updateViews() {
        new Utils.SendToServer(this)
                .execute("http://192.168.1.114/crtvnews/server_responses/enter_point.php?sce=replays&rep_cmd=update_views&rep_id=" + selectedReplay.getReplay_id(), null);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.share:
                //Todo: Implémenter Share
                break;
        }
    }
}