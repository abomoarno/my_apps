package arnoarobase.coiffuresafricaines.calsses;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Random;

public class AlarmInitialisation {

   private static int MANAGER_ID;

    public AlarmInitialisation() {

        Random random = new Random();
        MANAGER_ID = random.nextInt(10000)*125;

    }

    public static void initPubInterval(Context context){

        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent("arnoarobase.coiffuresafricaines.PUB_INTERVAL");
        mAlarmIntent = PendingIntent.getBroadcast(context, MANAGER_ID, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(mAlarmIntent);
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+160000,
                    200000,
                    mAlarmIntent);
        }
    }
    public static void clearPubInterval(Context context){
        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent("arnoarobase.coiffuresafricaines.PUB_INTERVAL");
        mAlarmIntent = PendingIntent.getBroadcast(context, MANAGER_ID, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(mAlarmIntent);
        }
    }
}