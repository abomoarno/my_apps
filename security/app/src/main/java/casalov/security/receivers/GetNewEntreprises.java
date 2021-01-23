package casalov.security.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import casalov.security.classes.Entreprise;
import casalov.security.classes.Intervention;
import casalov.security.db_managment.EntreprisesManager;
import casalov.security.db_managment.InterventionsManager;
import casalov.security.utils.Utils;

public class GetNewEntreprises extends BroadcastReceiver {

    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        new GetEntreprises().execute();
    }

    private class GetEntreprises extends AsyncTask<String, Void, String> {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        @Override
        protected String doInBackground(String... strings) {
            String url = "http://www.afrimoov.com/casalovsecurity/getEntreprises.php?"
                    + Utils.USER_ID + "=" + preferences.getString(Utils.USER_ID,"")
                    + "last_id" + "=" + new EntreprisesManager(context).getLastId();
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
            Entreprise entreprise;

            EntreprisesManager managment = new EntreprisesManager(context);
            try {
                JSONArray js = new JSONObject(o).getJSONArray("resultat");
                for (int i = 0; i<js.length(); i++){
                    JSONObject new_recette = js.getJSONObject(i);
                    entreprise = new Entreprise(
                            new_recette.getString("nom"),
                            new_recette.getString("entreprise_id"
                            )
                    );
                    entreprise.setPhone(new_recette.getString("phone"));
                    managment.insertEntreprise(entreprise);

                }

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
