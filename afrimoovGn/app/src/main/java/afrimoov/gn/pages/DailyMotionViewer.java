package afrimoov.gn.pages;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dailymotion.android.player.sdk.PlayerWebView;

import java.util.HashMap;
import java.util.Map;

import afrimoov.gn.R;
import afrimoov.gn.activities.ReplayShow;
import afrimoov.gn.utilis.Replay;

public class DailyMotionViewer extends Fragment {

    private PlayerWebView mVideoView;
    private ProgressDialog progressDialog;
    private ReplayShow activity;
    private Replay replay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dailymotion,null,true);
        mVideoView = view.findViewById(R.id.dm_player_web_view);

        activity = (ReplayShow)getActivity();

        Map<String, String> playerParams = new HashMap<>();
        mVideoView.load(activity.getLink(), playerParams);

        mVideoView.setEventListener(new PlayerWebView.EventListener() {
            @Override
            public void onEvent(String event, HashMap<String, String> map) {
                switch (event) {
                    case "video_start":
                        activity.updateView();
                    case "ad_start":
                    case "playing":
                        break;
                    case PlayerWebView.EVENT_FULLSCREEN_TOGGLE_REQUESTED:
                        activity.onFullScreenToggleRequested(mVideoView);
                        break;
                    default:
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView !=null)
            mVideoView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mVideoView != null)
            mVideoView.onResume();
    }
}