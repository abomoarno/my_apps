package casalov.security.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import casalov.security.R;
import casalov.security.activities.Chargement;
import casalov.security.classes.Intervention;
import casalov.security.db_managment.InterventionsManager;
import casalov.security.utils.Utils;

public class FactureALert extends BroadcastReceiver {

    private Intervention intervention;

    @Override
    public void onReceive(Context context, Intent intent) {
        List<Intervention> interventions = new InterventionsManager(context).getUnpayInterventions();
        intervention = (interventions.size() >0)?interventions.get(0):null;

        if (intervention != null){
            NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification note = buildNotification(context);
            if (notManager != null) {
                notManager.cancel(Utils.NOTIFICATION_ID);
                notManager.notify(Utils.NOTIFICATION_ID, note);
            }
        }
    }

    private Notification buildNotification(Context context) {
        Intent launchIntent =
                new Intent(context, Chargement.class);
        Bundle b = new Bundle();
        b.putBoolean("notification",true);
        launchIntent.putExtras(b);
        PendingIntent contentIntent =
                PendingIntent.getActivity(context, 0, launchIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        String textNews = "NOUVELLE FACTURE";
        builder.setContentText(textNews);
        builder .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{100,100})
                .setContentTitle("Vous avez une facture à régler afin de continuer à utiliser notre service")
                .setContentIntent(contentIntent);
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle(builder);

        inboxStyle.addLine(intervention.getMotif());
        return inboxStyle.build();
    }
}
