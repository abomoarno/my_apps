package big.win.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Utils {

    private Context context;
    public static String URL_RACINE = "http://www.bigwinner.eu/";

    public static int PREMIUM_1_MOIS;
    public static int PREMIUM_3_MOIS;
    public static int PREMIUM_6_MOIS;
    public static int PREMIUM_12_MOIS;

    public static int GOAL_12_MOIS;
    public static int GOAL_3_MOIS;
    public static int GOAL_6_MOIS;
    public static int GOAL_1_MOIS;

    public static final String GOAL_GOAL_EXPIRE = "goal_goal_expire";
    public static final String PREMIUM_EXPIRE = "premium_expire";

    public static final Boolean GOAL_GOAL_STATUS = false;
    public static final Boolean PREMIUM_STATUS = false;

    public Utils(Context context) {
        this.context = context;
    }


    public boolean isNetworkReachable() {
        final ConnectivityManager mManager =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = null;
        if (mManager != null) {
            current = mManager.getActiveNetworkInfo();
        }
        return current != null && (current.getState() == NetworkInfo.State.CONNECTED);
    }

}
