package afrimoov.gn.pages;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.dailymotion.android.player.sdk.PlayerWebView;

import java.util.HashMap;
import java.util.Map;

import afrimoov.gn.R;
import afrimoov.gn.activities.ReplayShow;
import afrimoov.gn.utilis.Replay;

public class IframeViewer extends Fragment {

    private WebView mVideoView;
    private ReplayShow activity;
    private Replay replay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iframe,null,true);
        mVideoView = view.findViewById(R.id.webview);
        activity = (ReplayShow)getActivity();
        mVideoView.loadData(activity.getLink(),"text/html",null);
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