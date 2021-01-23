package afrimoov.tg.pages;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.dailymotion.android.player.sdk.PlayerWebView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import afrimoov.tg.R;
import afrimoov.tg.activities.ReplayShow;
import afrimoov.tg.utilis.FullScreenController;
import afrimoov.tg.utilis.Replay;

public class VideoViewer extends Fragment {

    private VideoView mVideoView;
    private ReplayShow activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_simple,null,true);
        mVideoView = view.findViewById(R.id.player_view);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mVideoView.start();
                activity.updateView();
            }
        });
        activity = (ReplayShow)getActivity();

        MediaController mc = new FullScreenController(activity,activity);
        mc.setAnchorView(mVideoView);
        mVideoView.setMediaController(mc);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mVideoView.setVideoURI(Uri.parse(activity.getLink()));
            }
        });
        return view;
    }
}