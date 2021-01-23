package niamoro.annonces.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static String PAYS;
    public static String TYPE_MAISON = "maison";
    public static String TYPE_TERRAIN = "terrain";
    public static String TYPE_VENTE = "achat";
    public static String TYPE_LOCATION = "location";

    public static Boolean NOTIFICATIONS;
    public static Boolean COMPACT_MODE;
    public static Boolean PRESENTATION;
    public static Boolean PRIVACY;

    private Context context;

    public Utils(Context context){
        this.context = context;
    }

    public boolean isNetworkReachable() {
        final ConnectivityManager mManager =
                (ConnectivityManager)context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = mManager.getActiveNetworkInfo();
        if(current == null) {
            return false;
        }
        return (current.getState() == NetworkInfo.State.CONNECTED);
    }

}