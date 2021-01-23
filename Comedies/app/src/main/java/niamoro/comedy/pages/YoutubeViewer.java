package niamoro.comedy.pages;

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

import niamoro.comedy.R;
import niamoro.comedy.activities.ReplayShow;

public class YoutubeViewer extends Fragment implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView ytPlayer;
    private Page_Play parent;
    private ReplayShow context;
    private Bundle arguments;
    private FrameLayout mFrame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.youtube,null,true);
        mFrame = view.findViewById(R.id.yt_player);
        context = (ReplayShow)getActivity();
        //parent = context.getPlay_fragment();
        ytPlayer = new YouTubePlayerView(context);
        LinearLayout.LayoutParams ylp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        ytPlayer.setLayoutParams(ylp);
        mFrame.addView(ytPlayer);

        return view;
    }

    public void init(Bundle bundle){
        arguments = bundle;
        ytPlayer.initialize(getString(R.string.api_key),this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        youTubePlayer.loadVideo(arguments.getString("video"));
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
                parent.playNext();
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        });
       // parent.updateView();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
    }


}