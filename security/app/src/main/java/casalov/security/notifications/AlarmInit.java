package casalov.security.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import casalov.security.receivers.FacturesReceiver;
import casalov.security.receivers.GetNewEntreprises;
import casalov.security.utils.Utils;

public class AlarmInit extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        initGetFactures(context);
        initNewFacture(context);
    }
    public static void initNewFacture(Context context){
        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent(context, FactureALert.class);
        mAlarmIntent = PendingIntent.getBroadcast(context, Utils.NOTIFICATION_ID, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(mAlarmIntent);
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + Utils.NOTIFICATION_INTERVAL,
                    Utils.NOTIFICATION_INTERVAL,
                    mAlarmIntent);
        }
    }
    public static void initGetFactures(Context context){
        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent(context, FacturesReceiver.class);
        mAlarmIntent = PendingIntent.getBroadcast(context, Utils.NOTIFICATION_ID, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(mAlarmIntent);
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + Utils.UPDATE_INTERVAL,
                    Utils.UPDATE_INTERVAL,
                    mAlarmIntent);
        }
    }

    public static void initGetEntreprise(Context context){
        PendingIntent mAlarmIntent;
        Intent launchIntent = new Intent(context, GetNewEntreprises.class);
        mAlarmIntent = PendingIntent.getBroadcast(context, Utils.NOTIFICATION_ID, launchIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(mAlarmIntent);
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + Utils.GET_ENTREPRISES_INTERVAL,
                    Utils.GET_ENTREPRISES_INTERVAL,
                    mAlarmIntent);
        }
    }
}