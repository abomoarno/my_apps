package afrimoov.tg.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import afrimoov.tg.db_manager.ReplaysManager;
import afrimoov.tg.utilis.Replay;

public class NewReplays extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction() != null && !intent.getAction().equals("crtv.crtvnews.NEW_REPLAYS"))
            return;
        getNewReplays(context);
    }

    public static void getNewReplays(final Context context){
        final ReplaysManager manager = new ReplaysManager(context);
        final List<String> ids = manager.getLast_Id();
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("videos");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    final String replay_id = snapshot.getKey();
                    int views = snapshot.child("vues").getValue(Integer.class);
                    if (!manager.verifyReplayId(replay_id)) {
                        String title = snapshot.child("titre").getValue(String.class);
                        String description = snapshot.child("description").getValue(String.class);
                        String plateforme = snapshot.child("plateforme").getValue(String.class);
                        String chaine = snapshot.child("channel").getValue(String.class);
                        String image = snapshot.child("image").getValue(String.class);
                        String date = snapshot.child("date").getValue(String.class);
                        String duree = snapshot.child("duration").getValue(String.class);
                        Replay replay = new Replay(replay_id,title);
                        replay.setDescription(description);
                        replay.setDate(date);
                        replay.setPlateforme(plateforme);
                        replay.setChaine(chaine);
                        replay.setImage(image);
                        replay.setLink(replay_id);
                        replay.setViews(views);
                        replay.setDuree(duree);
                        manager.insertReplay(replay);
                    }
                    else {
                        manager.updateViews(replay_id, views);
                        ids.remove(replay_id);
                    }
                }

                for (String id : ids){
                   manager.deleteReplay(id);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }
}
