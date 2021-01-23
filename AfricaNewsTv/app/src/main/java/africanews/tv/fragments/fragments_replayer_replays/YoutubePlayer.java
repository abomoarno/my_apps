package africanews.tv.fragments.fragments_replayer_replays;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import africanews.tv.R;
import africanews.tv.activities.ReplayPlayer;
import africanews.tv.entities.Replay;
import africanews.tv.utils.Utils;


public class YoutubePlayer extends Fragment implements YouTubePlayer.OnInitializedListener {



    private Replay replay;
    private ReplayPlayer mContext;
    private YouTubePlayerSupportFragment youTubePlayerFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.youtube_player, container, false);
        mContext = (ReplayPlayer) getActivity();

        replay = getArguments().getParcelable("replay");

        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(Utils.API_KEY,this);

        return view;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        youTubePlayer.loadVideo(replay.getLink());

        mContext.updateViews();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        // youTubeInitializationResult.getErrorDialog(activity,youTubeInitializationResult.ordinal()).show();

    }

}
