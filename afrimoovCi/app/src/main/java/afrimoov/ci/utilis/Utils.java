package afrimoov.ci.utilis;

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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import afrimoov.ci.db_manager.SourcesManager;
import afrimoov.ci.db_manager.TvsManager;

public class Utils {

    public static String NUMBER_OPEN = "number_open";
    public static String COMPACT_MODE = "compact_mode";
    public static String NOTIFICATIONS = "notifications";
    public static String VIDEOS_SHOW_BEFORE_PUB = "videos_before_pub";
    public static int NUMBER_VIDEOS_BEFORE_PUB;
    public static String INTERVAL_BEFORE_PUB = "interval_before_pub";
    public static int TIME_BEFORE_PUB;
    public static String API_KEY = "AIzaSyCGOouicnfp41AxHpJqHRhkYvmerQt7H-w";

    //public static String INTERST_IMAGE = "ca-app-pub-3611856516220986/6113019342";
    public static String INTERST_IMAGE = "ca-app-pub-3940256099942544/1033173712";
    //public static String INTERST_VIDEO = "ca-app-pub-3611856516220986/9122326067";
    public static String INTERST_VIDEO = "ca-app-pub-3940256099942544/8691691433";

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
        return (current.getState() == NetworkInfo.State.CONNECTED);
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

                SourcesManager manager = new SourcesManager(context);
                List<Source> toAdd = new ArrayList<>();
                List<Source> toUpdate = new ArrayList<>();
                List<String> ids = manager.getAll_Ids();

                GenericTypeIndicator<HashMap<String,Source>> t = new GenericTypeIndicator<HashMap<String, Source>>() {};
                HashMap<String,Source> sources = dataSnapshot.getValue(t);

                if (sources != null){
                    for(String key:sources.keySet()) {
                        Source sc = sources.get(key);
                        if (sc !=null) {
                            sc.setSource_id(key);
                            if (ids.contains(key)){
                                toUpdate.add(sc);
                                ids.remove(key);
                            }
                            else
                            {
                                toAdd.add(sc);
                            }
                        }
                    }

                    manager.insertSources(toAdd);
                    manager.updateSources(toUpdate);
                    manager.deleteSources(ids);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void getTvs(final Context context){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("liveTvs");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TvsManager manager = new TvsManager(context);

                List<Live_Tv> toAdd = new ArrayList<>();
                List<Live_Tv> toUpdate = new ArrayList<>();
                List<String> ids = manager.getAll_Ids();

                GenericTypeIndicator<HashMap<String,Live_Tv>> t = new GenericTypeIndicator<HashMap<String, Live_Tv>>() {};
                HashMap<String,Live_Tv> tvs = dataSnapshot.getValue(t);
                if (tvs !=null) {
                    for (String key : tvs.keySet()) {
                        Live_Tv tv = tvs.get(key);
                        if (tv != null)
                        {
                            tv.setTv_id(key);
                           if (ids.contains(key)){
                               toUpdate.add(tv);
                               ids.remove(key);
                           }
                           else
                           {
                               toAdd.add(tv);
                           }
                        }
                    }

                    manager.insertTvs(toAdd);
                    manager.updateTvs(toUpdate);
                    manager.deleteTvs(ids);
                }
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