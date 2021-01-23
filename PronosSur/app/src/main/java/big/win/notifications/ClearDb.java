package big.win.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import big.win.classes.Pronostique;
import big.win.classes.Utils;
import big.win.db_managment.Pronos_Manager;

public class ClearDb extends BroadcastReceiver {

    private ArrayList<Pronostique> pronostiques;
    @Override
    public void onReceive(Context context, Intent intent) {
        pronostiques = new Pronos_Manager(context).getAll();

        new GetMatchClear(context).execute(Utils.URL_RACINE + "getAvailableMatchs.php");
    }

    private class GetMatchClear extends AsyncTask<String, Void, String> {

        private Context context;
        GetMatchClear(Context context){
            this.context = context;
        }
        @Override
        protected String doInBackground(String... strings) {

            String url = strings[0];
            try {

                URLConnection connection = (new URL(url)).openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;

                while((line = br.readLine()) != null)
                    sb.append(line);
                return sb.toString();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if (s!= null) {
                    JSONArray js = new JSONObject(s).getJSONArray("resultat");
                    for (int i = 0; i < js.length(); i++) {
                        int id = js.getInt(i);

                        for (int j = 0; j < pronostiques.size(); j++) {
                            if (pronostiques.get(j).getId() == id) {
                                pronostiques.remove(j);
                                break;
                            }
                        }
                    }
                }

                Pronos_Manager manager = new Pronos_Manager(context);

                for (Pronostique pronostique:pronostiques
                     ) {
                    manager.deleteProno(pronostique.getId());
                }

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
