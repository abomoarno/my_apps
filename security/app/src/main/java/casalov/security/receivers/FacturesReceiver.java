package casalov.security.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import casalov.security.activities.Chargement;
import casalov.security.activities.Connexion;
import casalov.security.classes.Intervention;
import casalov.security.db_managment.InterventionsManager;
import casalov.security.utils.Utils;

public class FacturesReceiver extends BroadcastReceiver {

    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        new GetUserInfos().execute();
    }

    private class GetUserInfos extends AsyncTask<String, Void, String> {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        @Override
        protected String doInBackground(String... strings) {
            String url = "http://www.afrimoov.com/casalovsecurity/getfactures.php?"
                    + Utils.USER_ID + "=" + preferences.getString(Utils.USER_ID,"")
                    + "last_id" + "=" + new InterventionsManager(context).getLastId();
            url = url.replace(" ","%20");
            URLConnection connection = null;
            try {
                connection = (new URL(url)).openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;

                while((line = br.readLine()) != null)
                    sb.append(line);
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String o) {
            Intervention intervention;

            InterventionsManager managment = new InterventionsManager(context);
            try {
                JSONArray js = new JSONObject(o).getJSONArray("resultat");
                for (int i = 0; i<js.length(); i++){
                    JSONObject new_recette = js.getJSONObject(i);
                    intervention = new Intervention(
                            new_recette.getString("intervention_id"),
                            new_recette.getString("motif"
                            )
                    );
                    intervention.setLieu(new_recette.getString("lieu"));
                    intervention.setDate(new_recette.getString("date"));
                    intervention.setStatut(Utils.STATUT_NOT_OK);
                    intervention.setPrix(new_recette.getDouble("prix"));
                    managment.insertIntervention(intervention);
                    preferences.edit().putBoolean(Utils.IS_PAY_OK,false).apply();
                }

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
