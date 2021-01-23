package niamoro.comedy.alerts;

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
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import niamoro.comedy.R;
import niamoro.comedy.activities.Chargement;
import niamoro.comedy.db_manager.VideosManager;
import niamoro.comedy.utilis.Video;

public class FirebaseService extends FirebaseMessagingService {

    private final int NOTIFICATION_ID = 237228212;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() == null || remoteMessage.getData().size()<=0)
            return;
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
        String title;
        if (Build.VERSION.SDK_INT >= 24)
        {
            title = Html.fromHtml(message.getData().get("titre") , Html.FROM_HTML_MODE_LEGACY).toString();
        }
        else
        {
            title = Html.fromHtml(message.getData().get("titre")).toString();
        }

        String id = message.getData().get("id");
        launchIntent.putExtra("id",id);
        launchIntent.putExtra("type","video");

        PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String CHANNEL_NOTIFS_ID = "niamoro_comedy_dailynotif";
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_NOTIFS_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.launcher_afrimoov)
                .setContentTitle(title)
                .setContentText(message.getData().get("message"))
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

        downloadVideo(id,builder);
    }
    private void downloadVideo(final String id, final NotificationCompat.Builder builder){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("videos").child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    VideosManager manager = new VideosManager(getApplicationContext());
                    GenericTypeIndicator<Video> t = new GenericTypeIndicator<Video>() {};
                    Video video = snapshot.getValue(t);
                    if (video != null) {
                        manager.insertVideo(video);
                        sendNotify(builder, video.getImage());
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotify(final NotificationCompat.Builder builder, final String image){
        final ImageView iv = new ImageView(this);
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
