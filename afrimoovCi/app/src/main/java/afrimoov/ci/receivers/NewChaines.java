package afrimoov.ci.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import afrimoov.ci.db_manager.ReplaysManager;
import afrimoov.ci.utilis.Replay;
import afrimoov.ci.utilis.Utils;

public class NewChaines extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction() != null && !intent.getAction().equals("crtv.crtvnews.NEW_CHANNELS"))
            return;
        Utils.getTvs(context);
    }
}
