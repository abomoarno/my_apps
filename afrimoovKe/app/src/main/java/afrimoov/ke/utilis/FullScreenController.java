package afrimoov.ke.utilis;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;

import afrimoov.ke.R;
import afrimoov.ke.activities.ReplayShow;

public class FullScreenController extends MediaController {
    private ImageButton fullscreen;
    private boolean isFullScreen;
    private ReplayShow activity;
    public FullScreenController(Context context) {
        super(context);
    }

    public FullScreenController(Context context,ReplayShow activity) {
        this(context);
        this.activity = activity;
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);

        fullscreen = new ImageButton(super.getContext());

        LayoutParams params =
                new LayoutParams(
                        50,
                        50
                );
        params.gravity = Gravity.END;
        params.rightMargin = 50;
        params.topMargin = 45;
        addView(fullscreen,params);

        isFullScreen = activity.getFullscreen();

        if (isFullScreen)
            fullscreen.setImageResource(R.drawable.fullscreen_exit);
        else
            fullscreen.setImageResource(R.drawable.fullscreen);

        fullscreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onFullScreenToggleRequested();
                if(activity.getFullscreen()){
                    fullscreen.setImageResource(R.drawable.fullscreen_exit);
                }
                else {
                    fullscreen.setImageResource(R.drawable.fullscreen);
                }
            }
        });

    }
}