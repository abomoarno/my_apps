package niamoro.annonces.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

import niamoro.annonces.R;
import niamoro.annonces.activities.Chargement;
import niamoro.annonces.databases.managers.AnnonceManager;
import niamoro.annonces.utils.Annonce;


public class FirebaseService extends FirebaseMessagingService {

    private static int NOTIFICATION_ID ;
    private String CHANNEL_NOTIFS_ID = "notif_daily";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() == null || remoteMessage.getData().size()<=0)
            return;
        NOTIFICATION_ID = (15 + new Random().nextInt(1232))*233;
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

        Annonce annonce = new Annonce();
        annonce.setId(message.getData().get("id"));
        annonce.setTitre(message.getData().get("titre"));
        annonce.setLien(message.getData().get("lien"));
        annonce.setImage(message.getData().get("image"));
        annonce.setVille(message.getData().get("ville"));
        annonce.setPays(message.getData().get("pays"));
        annonce.setTypeBien(message.getData().get("typeBien"));
        annonce.setTypeOperation(message.getData().get("typeOperation"));
        annonce.setPrix(message.getData().get("prix"));
        annonce.setDate(message.getData().get("date"));

        new AnnonceManager(this).insertAnnonce(annonce);

        launchIntent.putExtra("id",annonce.getId());

        PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_NOTIFS_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(annonce.getTitre())
                .setContentText("Une nouvelle annonce est disponible")
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.getBoolean("notifications",true))
            sendNotify(builder,annonce.getImage());

    }

    private void sendNotify(final NotificationCompat.Builder builder, final String image){
        final ImageView iv = new ImageView(this);
        Log.e("Image",image);
        Handler uihandler = new Handler(Looper.getMainLooper());
        uihandler.post(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(image).into(iv, new Callback() {
                    @Override
                    public void onSuccess() {
                        BitmapDrawable drawable = (BitmapDrawable)iv.getDrawable();
                        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(drawable.getBitmap()));
                        NotificationManager notManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        notManager.notify(NOTIFICATION_ID, builder.build());
                    }
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });

    }
}
