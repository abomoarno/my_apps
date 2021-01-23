package arnoarobase.coiffuresafricaines.calsses;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {

    private String CHANNEL_NOTIFS_ID = "notif_coiffures";


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("TOKEN",s);
        buildNotification();
    }

    private void buildNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channelNotif = new NotificationChannel(
                    CHANNEL_NOTIFS_ID,
                    "Channel Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channelNotif.setShowBadge(true);
            channelNotif.setVibrationPattern(new long[]{800,100,800});
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channelNotif);
        }

    }
}
