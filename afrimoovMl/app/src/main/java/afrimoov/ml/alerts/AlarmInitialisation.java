package afrimoov.ml.alerts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import static afrimoov.ml.utilis.Utils.TIME_BEFORE_PUB;

public class AlarmInitialisation {

   private static int MANAGER_ID = 221212237;
   static int NOTIFICATION_ID = 237212223;

    public static void initPubInterval(Context context){
        int pub_interval = TIME_BEFORE_PUB * 1000;
        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent("crtv.crtvnews.PUB_INTERVAL");
        mAlarmIntent = PendingIntent.getBroadcast(context, MANAGER_ID, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(mAlarmIntent);
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+pub_interval,
                    pub_interval,
                    mAlarmIntent);
        }
    }
    public static void clearPubInterval(Context context){
        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent("crtv.crtvnews.PUB_INTERVAL");
        mAlarmIntent = PendingIntent.getBroadcast(context, MANAGER_ID, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(mAlarmIntent);
        }
    }
}