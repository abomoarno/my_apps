package wiredstartups.yakjayexpo.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {

    public static final String APP_NAME_LABEL = "app_name";
    public static final String ENTRY_ID_LABEL = "entry_id";
    public static final String ROOM_ID_LABEL = "room_id";
    public static final String PARTNER_ID_LABEL = "partner_id";
    public static final String EXIT_OPERATION = "exit";
    public static final String ENTER_OPERATION = "enter";
    public static final String OPERATION_LABEL = "operation";
    public static final String APP_ENTER_EXIT = "enter_exit";
    public static final String APP_ROOMS = "app_rooms";
    public static final String APP_VENDORS = "app_vendors";
    public static final String APP_BADGES = "app_badges";
    public static final String APP_RESTO = "app_resto";
    public static final String APP_CHECKS = "app_checks";
    public static final String APP_ADMIN = "app_admin";

    public static final String MESSAGE_ENTRY_EXIT = "Cette version doit être utilisée à l'entrée du lieu de l'événement.\n\nVoulez-vous vraiment choisir cette version ?";
    public static final String MESSAGE_VALIDATE_BADGES = "Cette version doit être utilisée pour la validation des Badges.\n\nVoulez-vous vraiment choisir cette version ?";
    public static final String MESSAGE_SCAN_RESTO = "Cette version doit être utilisée pour donner accès au Resto à un participant.\n\nVoulez-vous vraiment choisir cette version ?";
    public static final String MESSAGE_ROOMS = "Cette version doit être utilisée pour vérifier l'identité des participants à l'entrée des salles ateliers.\n\nVoulez-vous vraiment choisir cette version ?";
    public static final String MESSAGE_VENDOR = "Cette version doit être utilisée par les partenaires pour scanner les visiteurs dans leurs stands\n\nVoulez-vous vraiment choisir cette version ?";
    public static final String MESSAGE_CHECKS = "Cette version doit être utilisée pour des contrôles de routine au sein du site de l'événement\n\nVoulez-vous vraiment choisir cette version ?";
    public static final String MESSAGE_ADMIN = "Cette version doit être utilisée pour toutes les taches admin du système\n\nVoulez-vous vraiment choisir cette version ?";


    public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

    private static String readStream(InputStream iStream){

        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bReader.readLine()) != null) {
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

    public static class SendToServer extends AsyncTask<String, Void, String> {
        private String lien;
        private String action;
        private Context context;

        public SendToServer(Context context){
            this.context = context;
        }
        @Override
        protected String doInBackground(String... strings) {
            lien = strings[0];
            if (strings.length > 1)
                action = strings[1];
            return getRemoteStream(lien);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent intent = new Intent(action);
            intent.putExtra("result",s);

            context.sendBroadcast(intent);
        }
    }
}
