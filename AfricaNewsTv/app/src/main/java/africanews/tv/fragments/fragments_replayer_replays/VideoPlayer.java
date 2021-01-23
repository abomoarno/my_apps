package africanews.tv.fragments.fragments_replayer_replays;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import africanews.tv.R;
import africanews.tv.activities.ReplayPlayer;
import africanews.tv.entities.Replay;
import africanews.tv.utils.FullScreenController;

public class VideoPlayer extends Fragment {

    private Replay replay;

    private ReplayPlayer mContext;

    private VideoView mVideoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_player, container, false);

        mContext = (ReplayPlayer) getActivity();


        replay = getArguments().getParcelable("replay");

        mVideoView = view.findViewById(R.id.player_view);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mVideoView.start();
                mContext.updateViews();
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });

        MediaController mc = new FullScreenController(mContext);
        mc.setAnchorView(mVideoView);
        mVideoView.setMediaController(mc);

        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mVideoView.setVideoURI(Uri.parse(replay.getLink()));
            }
        });

        return view;
    }
}
