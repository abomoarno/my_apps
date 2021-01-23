package africanews.tv.fragments.fragments_replayer_replays;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dailymotion.android.player.sdk.PlayerWebView;

import java.util.HashMap;
import java.util.Map;

import africanews.tv.R;
import africanews.tv.activities.ReplayPlayer;
import africanews.tv.entities.Replay;

public class DailyMotionPlayer extends Fragment {

    private PlayerWebView mVideoView;
    private ReplayPlayer mContext;
    private Replay replay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dailymotion_player, container, false);

        mContext = (ReplayPlayer) getActivity();

        replay = getArguments().getParcelable("replay");

        mVideoView = view.findViewById(R.id.dm_player_web_view);

        Map<String, String> playerParams = new HashMap<>();
        mVideoView.load(replay.getLink(), playerParams);

        mVideoView.setEventListener(new PlayerWebView.EventListener() {
            @Override
            public void onEvent(String event, HashMap<String, String> map) {
                switch (event) {
                    case "video_start":
                        mContext.updateViews();
                        break;
                    case PlayerWebView.EVENT_FULLSCREEN_TOGGLE_REQUESTED:
                        mContext.onFullScreenToggleRequested(mVideoView);
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
