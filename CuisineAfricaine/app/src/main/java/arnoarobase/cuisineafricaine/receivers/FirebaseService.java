package arnoarobase.cuisineafricaine.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import arnoarobase.cuisineafricaine.R;

import java.util.Random;

import arnoarobase.cuisineafricaine.activities.Chargement;


public class FirebaseService extends FirebaseMessagingService {

    private String CHANNEL_NOTIFS = "notif_cuisine";
    private int NOTIFICATION_ID;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() == null || remoteMessage.getData().size()<=0)
            return;
        NOTIFICATION_ID = new Random().nextInt(10000);
        buildNotification(remoteMessage);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("TOKEN",s);
    }

    private void buildNotification(RemoteMessage message) {
        Intent launchIntent = new Intent(this, Chargement.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        String title = message.getData().get("titre");
        String id = message.getData().get("id");
        String type = message.getData().get("type");

        launchIntent.putExtra("type",type);
        launchIntent.putExtra("id",id);

        PendingIntent contentIntent = PendingIntent.getActivity(this,NOTIFICATION_ID , launchIntent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_NOTIFS)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message.getData().get("message"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{800,100,800})
                .setContentIntent(contentIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channelNotif = new NotificationChannel(
                    CHANNEL_NOTIFS,
                    getString(R.string.daily_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
            );
            channelNotif.setVibrationPattern(new long[]{800,100,800});
            channelNotif.setDescription(getString(R.string.daily_channel_description));
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channelNotif);
        }
        final String img = message.getData().get("image");
        final ImageView iv = new ImageView(this);
        Handler uihandler = new Handler(Looper.getMainLooper());
        uihandler.post(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(img).into(iv, new Callback() {
                    @Override
                    public void onSuccess() {
                        BitmapDrawable drawable = (BitmapDrawable)iv.getDrawable();
                        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(drawable.getBitmap()));
                        NotificationManager notManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        notManager.notify(NOTIFICATION_ID, builder.build());
                    }
                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });

    }
}
