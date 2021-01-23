package big.win.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.ArrayList;

import big.win.activities.Accueil;
import big.win.R;
import big.win.classes.Pronostique;
import big.win.db_managment.Pronos_Manager;

public class GoodLuck extends BroadcastReceiver {

    private ArrayList<Pronostique> pronostiques;
    public static int NOTIFICATION_ID = 241278;

    @Override
    public void onReceive(Context context, Intent intent) {
        pronostiques = new Pronos_Manager(context).getTodayPremium();
        pronostiques.addAll(new Pronos_Manager(context).getTodayBonus());
        pronostiques.addAll(new Pronos_Manager(context).getTodayFree());
        pronostiques.addAll(new Pronos_Manager(context).getTodayGoalGoal());
        if (pronostiques.size() > 0) {
            NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification note = buildNotification(context);
            if (notManager != null) {
                notManager.cancel(NOTIFICATION_ID);
                notManager.notify(NOTIFICATION_ID, note);
            }
        }

    }

    private Notification buildNotification(Context context) {
        Intent launchIntent =
                new Intent(context, Accueil.class);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.good_luck);
        String tipsAdded = pronostiques.size() + " Tips Added Today !";
        remoteViews.setTextViewText(R.id.todayTips,tipsAdded);
        PendingIntent contentIntent =
                PendingIntent.getActivity(context, 0, launchIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle(builder);
        builder.setCustomContentView(remoteViews);
        builder.setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logo)
                .setCustomBigContentView(remoteViews)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{500,400})
                .setContentIntent(contentIntent);

        return inboxStyle.build();
    }
}

