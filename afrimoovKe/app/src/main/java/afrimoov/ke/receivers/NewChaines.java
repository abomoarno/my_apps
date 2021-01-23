package afrimoov.ke.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import afrimoov.ke.db_manager.ReplaysManager;
import afrimoov.ke.utilis.Replay;
import afrimoov.ke.utilis.Utils;

public class NewChaines extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction() != null && !intent.getAction().equals("crtv.crtvnews.NEW_CHANNELS"))
            return;
        Utils.getTvs(context);
    }
}
