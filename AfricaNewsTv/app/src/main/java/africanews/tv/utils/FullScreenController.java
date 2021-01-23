package africanews.tv.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import africanews.tv.R;
import africanews.tv.activities.ReplayPlayer;

public class FullScreenController extends MediaController {
    private ImageView fullscreen;
    private boolean isFullScreen;


    private ReplayPlayer mContext;
    //private ReplayShow activity;
    public FullScreenController(ReplayPlayer context) {
        super(context);
        mContext = context;
    }



    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);

        fullscreen = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.fullscreen_button, null, false);

        //isFullScreen = activity.getFullscreen();

        //fullscreen.setBackgroundColor(Color.TRANSPARENT);

        if (isFullScreen)
            fullscreen.setImageResource(R.drawable.ic_fullscreen_exit);
        else
            fullscreen.setImageResource(R.drawable.ic_fullscreen);

        LayoutParams params =
                new LayoutParams(
                        60,
                        60
                );
        params.gravity = Gravity.END;
        params.rightMargin = 45;
        params.topMargin = 50;
        addView(fullscreen,params);

        fullscreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setFullscreen();
            }
        });

    }

    private void setFullscreen(){
        mContext.onFullScreenToggleRequested();
        if(mContext.isFullScreen()){
            fullscreen.setImageResource(R.drawable.ic_fullscreen_exit);
        }
        else {
            fullscreen.setImageResource(R.drawable.ic_fullscreen);
        }

    }
}
