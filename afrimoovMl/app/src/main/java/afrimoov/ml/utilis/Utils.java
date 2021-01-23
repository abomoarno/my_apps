package afrimoov.ml.utilis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import afrimoov.ml.db_manager.ReplaysManager;
import afrimoov.ml.db_manager.SourcesManager;
import afrimoov.ml.db_manager.TvsManager;

public class Utils {

    public static String NUMBER_OPEN = "number_open";
    public static String COMPACT_MODE = "compact_mode";
    public static String NOTIFICATIONS = "notifications";
    public static boolean ADS_REMOVED;
    public static String VIDEOS_SHOW_BEFORE_PUB = "videos_before_pub";
    public static int NUMBER_VIDEOS_BEFORE_PUB;
    public static String INTERVAL_BEFORE_PUB = "interval_before_pub";
    public static int TIME_BEFORE_PUB;
    public static String API_KEY = "AIzaSyCGOouicnfp41AxHpJqHRhkYvmerQt7H-w";

    public static String INTERST_IMAGE = "ca-app-pub-3611856516220986/6113019342";
    public static String INTERST_VIDEO = "ca-app-pub-3611856516220986/9122326067";

    private Context context;
    public Utils(Context cont){
        context = cont;
    }

    public boolean isNetworkReachable() {
        final ConnectivityManager mManager =
                (ConnectivityManager)context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = mManager.getActiveNetworkInfo();
        if(current == null) {
            return false;
        }
        return (current.isConnected());
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

    private static void getSources(final Context context){
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("replay_sources");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                    final String source_id = snapshot.getKey();
                    SourcesManager manager = new SourcesManager(context);
                    if (source_id != null) {
                        String nom = snapshot.child("nom").getValue(String.class);
                        String image = snapshot.child("image").getValue(String.class);
                        String pays = snapshot.child("pays").getValue(String.class);

                        Source source = new Source();
                        source.setImage(image);

                        source.setNom(nom);
                        source.setPays(pays);
                        source.setSource_id(source_id);

                        if (!manager.verifySourceId(source_id))
                            manager.insertSource(source);
                        else
                            manager.updateSource(source);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void getTvs(final Context context){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("liveTvs");
        final TvsManager manager = new TvsManager(context);
        final List<String> ids = manager.getIds();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
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
                        ids.remove(tv_id);
                    }
                }

                for (String st:ids)
                    manager.deleteTv(st);

                getSources(context);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                getSources(context);
            }
        });
    }

    public static void getParams(final Context context) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("params");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

                    preferences.edit()
                            .putInt(VIDEOS_SHOW_BEFORE_PUB,dataSnapshot.child("videos_before_pub").getValue(Integer.class))
                            .putInt(INTERVAL_BEFORE_PUB,dataSnapshot.child("interval_before_pub").getValue(Integer.class))
                            .apply();
                    NUMBER_VIDEOS_BEFORE_PUB = preferences.getInt(VIDEOS_SHOW_BEFORE_PUB,3);
                    TIME_BEFORE_PUB = preferences.getInt(INTERVAL_BEFORE_PUB,600);
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