package afrimoov.tg.alerts;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import afrimoov.tg.R;
import afrimoov.tg.activities.Chargement;

import static afrimoov.tg.alerts.AlarmInitialisation.NOTIFICATION_ID;

public class FirebaseService extends FirebaseMessagingService {

    private String CHANNEL_NOTIFS_ID = "notif_crtv_daily";
    private String CHANNEL_PROGRAMS_ID = "notif_crtv_program";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationManager notManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (remoteMessage.getData().size()<=0)
            return;

        String notifType = remoteMessage.getData().get("type");

        Notification note = (notifType.equals("daily")) ? buildNotification(remoteMessage): buildProgramNotification(remoteMessage);
        notManager.cancel(NOTIFICATION_ID);
        notManager.notify(NOTIFICATION_ID, note);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("TOKEN",s);
    }

    private Notification buildNotification(RemoteMessage message) {

        Intent launchIntent =
                new Intent(this, Chargement.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.replay_notif);
        String title = getString(R.string.not_miss);
        remoteViews.setTextViewText(R.id.notifText,getString(R.string.occupy));
        PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, launchIntent, PendingIntent.FLAG_ONE_SHOT);
        remoteViews.setTextViewText(R.id.text1,message.getData().get("replay1"));
        remoteViews.setTextViewText(R.id.text2,message.getData().get("replay2") );
        remoteViews.setTextViewText(R.id.text3,message.getData().get("replay3"));

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_NOTIFS_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomBigContentView(remoteViews)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{800,100,800})
                .setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channelNotif = new NotificationChannel(
                    CHANNEL_NOTIFS_ID,
                    getString(R.string.daily_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
            );
            channelNotif.setShowBadge(true);
            channelNotif.setVibrationPattern(new long[]{800,100,800});
            channelNotif.setDescription(getString(R.string.daily_channel_description));
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channelNotif);
        }

        return builder.build();
    }

    private Notification buildProgramNotification(RemoteMessage message) {
        Intent launchIntent =
                new Intent(this, Chargement.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        launchIntent.putExtra("motif","program");
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.program_notif);
        String title = getString(R.string.not_miss);
        remoteViews.setTextViewText(R.id.notifText,title);
        PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, launchIntent, PendingIntent.FLAG_ONE_SHOT);
        remoteViews.setTextViewText(R.id.title,message.getData().get("title"));
        remoteViews.setTextViewText(R.id.chaine,message.getData().get("chaine") );
        remoteViews.setTextViewText(R.id.heure,message.getData().get("heure"));

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_PROGRAMS_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomBigContentView(remoteViews)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{800,100,800})
                .setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channelNotif = new NotificationChannel(
                    CHANNEL_PROGRAMS_ID,
                    getString(R.string.program_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
            );
            channelNotif.setShowBadge(true);
            channelNotif.setVibrationPattern(new long[]{800,100,800});
            channelNotif.setDescription(getString(R.string.program_channel_description));
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channelNotif);
        }

        return builder.build();
    }
}
