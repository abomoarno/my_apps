package afrimoov.ke.alerts;

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
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import afrimoov.ke.R;
import afrimoov.ke.activities.Chargement;
import afrimoov.ke.db_manager.ReplaysManager;
import afrimoov.ke.db_manager.TvsManager;
import afrimoov.ke.utilis.Live_Tv;
import afrimoov.ke.utilis.Replay;

import static afrimoov.ke.alerts.AlarmInitialisation.NOTIFICATION_ID;

public class FirebaseService extends FirebaseMessagingService {

    private String CHANNEL_NOTIFS_ID = "notif_daily";
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
        String title = message.getData().get("titre");
        String id = message.getData().get("id");
        String type = message.getData().get("type");
        launchIntent.putExtra("id",id);
        launchIntent.putExtra("type",(type != null) ? type : "video");
        PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_NOTIFS_ID)
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

        if (type != null && type.equals("tv"))
            downloadTv(id,builder);
        else
            downloadVideo(id,builder);
    }
    private void downloadVideo(final String id, final NotificationCompat.Builder builder){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("videos").child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    ReplaysManager manager = new ReplaysManager(getApplicationContext());
                    int views = snapshot.child("vues").getValue(Integer.class);
                    String image = snapshot.child("image").getValue(String.class);
                    if (manager.verifyReplayId(snapshot.getKey())) {
                        String title = snapshot.child("titre").getValue(String.class);
                        String description = snapshot.child("description").getValue(String.class);
                        String plateforme = snapshot.child("plateforme").getValue(String.class);
                        String chaine = snapshot.child("channel").getValue(String.class);
                        String date = snapshot.child("date").getValue(String.class);
                        String duree = snapshot.child("duration").getValue(String.class);

                        Replay replay = new Replay(snapshot.getKey(), title);
                        replay.setDescription(description);
                        replay.setDate(date);
                        replay.setPlateforme(plateforme);
                        replay.setChaine(chaine);
                        replay.setImage(image);
                        replay.setLink(snapshot.getKey());
                        replay.setViews(views);
                        replay.setDuree(duree);

                        manager.insertReplay(replay);
                    } else {
                        manager.updateViews(snapshot.getKey(), views);
                    }
                    //Log.e("IN IT","OKIII");
                    sendNotify(builder, image);
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
    private void downloadTv(String id, final NotificationCompat.Builder builder){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("liveTvs").child(id);

        Log.e("TV DOWNLOAD","OKIII");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    Log.e("TV DOWNLOAD","OKIII");
                    TvsManager manager = new TvsManager(getApplicationContext());
                    String tv_id = snapshot.getKey();
                    String nom = snapshot.child("nom").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String pays = snapshot.child("pays").getValue(String.class);
                    String lien = snapshot.child("lien").getValue(String.class);
                    String plateforme = snapshot.child("platefome").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    String langue = snapshot.child("langue").getValue(String.class);
                    String categorie = snapshot.child("categories").getValue(String.class);
                    int vues  = snapshot.child("live_vues").getValue(Integer.class);
                    Live_Tv tv = new Live_Tv(tv_id,nom);
                    tv.setVues(vues);
                    tv.setCategorie(categorie);
                    tv.setLangue(langue);
                    tv.setImage(image);
                    tv.setPlateforme(plateforme);
                    tv.setDescription(description);
                    tv.setPays(pays);
                    tv.setLien(lien);
                    if (!manager.verifyTvId(tv_id))
                        manager.insertTv(tv);
                    else {
                        manager.updateTv(tv);
                    }

                    sendNotify(builder,"https://afrimoov.com/appsimages/" + image);

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
               // Log.e("IN IT","OKIII");
                Picasso.get().load(image).into(iv, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("IN IT","OKIII");
                        BitmapDrawable drawable = (BitmapDrawable)iv.getDrawable();
                        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(drawable.getBitmap()));
                        NotificationManager notManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        notManager.notify(NOTIFICATION_ID, builder.build());
                        Log.e("Finish","OKIII");

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