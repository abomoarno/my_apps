package africanews.tv.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import africanews.tv.R;
import africanews.tv.entities.Replay;
import africanews.tv.entities.TvChannel;


public class Utils {

    public static String NUMBER_OPEN = "number_open";
    public static String COMPACT_MODE = "compact_mode";
    public static String NOTIFICATIONS = "notifications";
    public static boolean ADS_REMOVED;
    public static String VIDEOS_SHOW_BEFORE_PUB = "videos_before_pub";
    public static int NUMBER_VIDEOS_BEFORE_PUB;
    public static String INTERVAL_BEFORE_PUB = "interval_before_pub";
    public static int TIME_BEFORE_PUB;

    public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy H:m:s", Locale.FRENCH);

    public static String API_KEY = "AIzaSyCGOouicnfp41AxHpJqHRhkYvmerQt7H-w";


    private Context context;
    public static final String SERVER_RESULT = "camertoday_serverResult";

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

    private static String readStream(InputStream iStream){

        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bReader.readLine()) != null) { //Read till end
                builder.append(line);
            }
            return builder.toString();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private static String getRemoteStream(String lien){
        InputStream in;
        String queryResult = "";
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(lien);
            urlConnection = (HttpURLConnection) url.openConnection();

            in = urlConnection.getInputStream();
            queryResult = readStream( in );
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return queryResult;
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
    public static String getDureeFromStart(Date start_date){

        long diff = Math.abs((new Date().getTime() - start_date.getTime())/1000);

        long result;
        String msg;

        if ((diff/60) < 1)
            msg =  "1 min";
        else if ((result = diff/60)<60)
            msg =  result + " min";
        else if ((result = diff/3600)<24)
            msg =  result + " hrs";
        else if ((result = diff/86400)<30)
            msg =  result + " jours";
        else if ((result = diff/2592000)<12)
            msg =  result + " mois";
        else {
            result = diff/31104000;
            msg =  result + " ans";
        }

        return "Membre depuis " + msg;
    }

    public static class SendToServer extends AsyncTask<String, Void, String> {

        private Context context;
        private String action;
        private String link;

        public SendToServer(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            link = strings[0];
            if (strings.length > 1)
                action = strings[1];

            return getRemoteStream(link);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (action != null){
                Intent intent = new Intent(action);
                intent.putExtra(SERVER_RESULT,s);
                context.sendBroadcast(intent);
            }
        }
    }

    /*
    * Cette fonction permet de construire un objet replay à partir d'un JSONobject qui vient
    * du serveur;
    * */

    public static Replay decodeReplayFromObject(JSONObject object, Context context){
        if (object == null) {
            return null;
        }
        try {

            Replay replay = new Replay();

            replay.setReplay_id(object.getInt(context.getString(R.string.replay_id)));
            replay.setTitle(Html.fromHtml(object.getString(context.getString(R.string.replay_title))).toString());
            replay.setIllustration(object.getString(context.getString(R.string.replay_illustration)));
            replay.setLink(object.getString(context.getString(R.string.replay_link)));
            replay.setCategory(object.getInt(context.getString(R.string.replay_category)));
            replay.setLanguage(object.getString(context.getString(R.string.replay_language)));
            replay.setChannel_id(object.getInt(context.getString(R.string.replay_channel_id)));
            replay.setChannel_name(Html.fromHtml(object.getString(context.getString(R.string.replay_channel_name))).toString());
            replay.setPlateform(object.getString(context.getString(R.string.replay_plateform)));
            replay.setDuration(object.getString(context.getString(R.string.replay_duration)));
            replay.setQuery_date(object.getString(context.getString(R.string.replay_query_date)));
            replay.setDisplay_date(object.getString(context.getString(R.string.replay_display_date)));

            return replay;
        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }


    public static TvChannel decodeTvFromObject(JSONObject object, Context context){
        if (object == null) {
            return null;
        }
        try {

            TvChannel tv = new TvChannel();
            tv.setIllustration(object.getString(context.getString(R.string.tv_illustration)));
            tv.setTv_id(object.getInt(context.getString(R.string.tv_id)));
            tv.setName(object.getString(context.getString(R.string.tv_name)));
            tv.setChannel(object.getInt(context.getString(R.string.tv_channel)));
            tv.setPlateform(object.getString(context.getString(R.string.tv_plateform)));
            tv.setLink(object.getString(context.getString(R.string.tv_link)));
            tv.setLanguage(object.getInt(context.getString(R.string.tv_language)));
            return tv
                    ;
        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}