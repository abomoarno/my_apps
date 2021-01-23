package niamoro.comedy.utilis;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import niamoro.comedy.db_manager.ComediensManager;

public class Utils {

    public static String NUMBER_OPEN = "number_open";
    public static String COMPACT_MODE = "compact_mode";
    public static String NOTIFICATIONS = "notifications";

    public static String VIDEOS_SHOW_BEFORE_PUB = "videos_before_pub";
    public static int NUMBER_VIDEOS_BEFORE_PUB;
    public static int NUMBER_PUB_SHOWN;

    private Context context;
    public Utils(Context cont){
        context = cont;
    }

    public boolean isNetworkReachable() {
        final ConnectivityManager mManager =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = mManager.getActiveNetworkInfo();
        return current != null && (current.getState() == NetworkInfo.State.CONNECTED);
    }

    public static void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }

    public static void getSources(final Context context){
        DatabaseReference reference;
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        reference = FirebaseDatabase.getInstance().getReference("comediens");
        final ComediensManager manager = new ComediensManager(context);
        final List<String> ids = manager.getLast_Id();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                    final String source_id = snapshot.getKey();
                    Comedien comedien = new Comedien();
                    if (!manager.verifyComedienId(source_id)) {
                        String nom = snapshot.child("nom").getValue(String.class);
                        String image = snapshot.child("image").getValue(String.class);
                        String pays = snapshot.child("pays").getValue(String.class);
                        comedien.setImage(image);
                        comedien.setNom(nom);
                        comedien.setPays(pays);
                        comedien.setComedien_id(source_id);
                        if (preferences.getBoolean(Utils.NOTIFICATIONS,true))
                            comedien.setFollowed(true);
                        manager.insertComedien(comedien);
                    }
                    else {
                        manager.updateComedien(comedien);
                        ids.remove(source_id);
                    }
                }

                for (String id : ids){
                    manager.deleteSource(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void getParams(final Context context) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("params");
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    preferences.edit()
                            .putInt(VIDEOS_SHOW_BEFORE_PUB,dataSnapshot.child("videos_before_pub").getValue(Integer.class))
                            .apply();
                    NUMBER_VIDEOS_BEFORE_PUB = preferences.getInt(VIDEOS_SHOW_BEFORE_PUB,3);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }
}