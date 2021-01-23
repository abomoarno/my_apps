package afrimoov.ml.pages;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import afrimoov.ml.R;
import afrimoov.ml.activities.ReplayShow;
import afrimoov.ml.utilis.Utils;

public class YoutubeViewer extends Fragment implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView ytPlayer;
    private ReplayShow activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.youtube,null,true);
        FrameLayout frame = view.findViewById(R.id.yt_player);
        activity = (ReplayShow) getActivity();
        ytPlayer = new YouTubePlayerView(getActivity());
        LinearLayout.LayoutParams ylp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        ytPlayer.setLayoutParams(ylp);
        frame.addView(ytPlayer);

        return view;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        youTubePlayer.loadVideo(activity.getLink());
        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(String s) {

            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {

            }

            @Override
            public void onVideoEnded() {
                activity.playNext();
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        });
        activity.updateView();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
       // youTubeInitializationResult.getErrorDialog(activity,youTubeInitializationResult.ordinal()).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        ytPlayer.initialize(Utils.API_KEY,this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}