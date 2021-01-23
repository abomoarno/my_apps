package big.win.classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;


import java.util.Calendar;

import big.win.notifications.ClearDb;
import big.win.notifications.GoodLuck;
import big.win.notifications.NewMatchs;
import big.win.notifications.UpdateMatchs;

import static big.win.notifications.GoodLuck.NOTIFICATION_ID;

public class AlarmInitialisation extends BroadcastReceiver {
    public static int CLEAR_INTERVAL = 1800000;
    public static int UPDATE_INTERVAL = 600000;
    public static int NEW_MATCHS_INTERVAL = 900000;
    @Override
    public void onReceive(Context context, Intent intent) {
        initAll(context);
    }

    public static void initUpdateMatchs(Context context){
        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent(context, UpdateMatchs.class);
        mAlarmIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if (manager != null) {
            manager.cancel(mAlarmIntent);
            manager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    UPDATE_INTERVAL,
                    mAlarmIntent);
        }
    }

    public static void initGoodLuck(Context context){
        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent(context, GoodLuck.class);
        mAlarmIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID + 4, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,11);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,0);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        if (manager != null) {
            manager.cancel(mAlarmIntent);
            manager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    mAlarmIntent);
        }
    }

    public static void initNewMatchs(Context context){
        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent(context, NewMatchs.class);
        mAlarmIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID + 5, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if (manager != null) {
            manager.cancel(mAlarmIntent);
            manager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime()+NEW_MATCHS_INTERVAL,
                    NEW_MATCHS_INTERVAL,
                    mAlarmIntent);
        }
    }

    public static void initClearDb(Context context){
        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent(context, ClearDb.class);
        mAlarmIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID + 7, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(mAlarmIntent);
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + CLEAR_INTERVAL,
                    CLEAR_INTERVAL,
                    mAlarmIntent);
        }
    }

    public static void initAll(Context context){
        initUpdateMatchs(context);
        initNewMatchs(context);
        initClearDb(context);
        initGoodLuck(context);
    }
}
