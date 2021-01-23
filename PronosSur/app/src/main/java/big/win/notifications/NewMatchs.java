package big.win.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import big.win.activities.Accueil;
import big.win.R;
import big.win.classes.Pronostique;
import big.win.classes.Utils;
import big.win.db_managment.Pronos_Manager;

import static big.win.notifications.GoodLuck.NOTIFICATION_ID;


public class NewMatchs extends BroadcastReceiver {

    private ArrayList<Pronostique> pronostiques;
    @Override
    public void onReceive(Context context, Intent intent) {

        String url = Utils.URL_RACINE + "getMatchsDay.php?last_id=" + new Pronos_Manager(context).getLastId();
        new GetMatchDay(context).execute(url);
    }

    private class GetMatchDay extends AsyncTask<String, Void, String> {

        private Context context;
        GetMatchDay(Context context){
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
            Pronostique p;
            Pronos_Manager managment = new Pronos_Manager(context);
            try {
                JSONArray js = new JSONObject(s).getJSONArray("resultat");
                pronostiques = new ArrayList<>();
                for (int i = 0; i<js.length(); i++){
                    JSONObject match = js.getJSONObject(i);
                    p = new Pronostique(
                            match.getInt("id"),
                            match.getString("pays"),
                            match.getString("heure"),
                            match.getString("cote"),
                            match.getString("pronostic"),
                            match.getString("category"),
                            match.getString("resultat")
                    );
                    p.setName_teamA(match.getString("equipe1"));
                    p.setName_teamB(match.getString("equipe2"));
                    p.setScoreA(match.getString("score1"));
                    p.setScoreB(match.getString("score2"));
                    p.setNotified(0);
                    managment.insertProno(p);

                    if (isTodayMatch(p.getHours().substring(0,10)))
                        pronostiques.add(p);
                }

                if (pronostiques.size() > 0) {
                    NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification note = buildNotification(context);
                    if (notManager != null) {
                        notManager.notify(NOTIFICATION_ID, note);
                    }
                }

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private Notification buildNotification(Context context) {
        Intent launchIntent =
                new Intent(context, Accueil.class);
        Bundle b = new Bundle();
        b.putBoolean("notification",true);
        launchIntent.putExtras(b);
        PendingIntent contentIntent =
                PendingIntent.getActivity(context, 0, launchIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        String textNews = context.getString(R.string.new_matchs);
        textNews = " "+textNews;
        builder.setContentText(pronostiques.size()+textNews);
        builder.setTicker(context.getString(R.string.new_match_alert))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentTitle(textNews)
                .setContentIntent(contentIntent);
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle(builder);
        for(int i = 0; i < pronostiques.size(); i++){
            Pronostique p = pronostiques.get(i);
            inboxStyle.addLine(p.getName_teamA() + " - " + p.getName_teamB());
        }
        return inboxStyle.build();
    }

    private boolean isTodayMatch(String d){
        Date date = new Date();
        String today = (1900+date.getYear()) + "-";
        today += (date.getMonth()+1>9)? (date.getMonth()+1): "0" + (date.getMonth()+1);
        today += "-";
        today += (date.getDate()>9)? date.getDate(): "0" + date.getDate();
        return d.compareTo(today) == 0;
    }
}
