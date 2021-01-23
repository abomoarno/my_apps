package afrimoov.nigeria.db_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import afrimoov.nigeria.R;
import afrimoov.nigeria.utilis.Live_Tv;
import afrimoov.nigeria.utilis.Replay;

public class ReplaysManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "afrimoov_crtv_replays";

    private static final String TABLE_REPLAYS = "replays";

    private static final String id ="replay_id";
    private static final String title = "title";
    private static final String description = "description";
    private static final String link = "link";
    private static final String plateforme = "plateforme";
    private static final String image = "image";
    private static final String views = "views";
    private static final String chaine = "chaine";
    private static final String duration = "duration";
    private static final String date = "date";

    private SQLiteDatabase bdd;
    private ReplayHelper myHelper;

    public ReplaysManager(Context context) {
        myHelper = new ReplayHelper(context,NAME_DB,null,VERSION_BDD);
    }

    public void insertReplay(Replay replay){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(title,replay.getTitle());
        values.put(description,replay.getDescription());
        values.put(link,replay.getLink());
        values.put(plateforme,replay.getPlateforme());
        values.put(image,replay.getImage());
        values.put(views,replay.getViews());
        values.put(id,replay.getReplay_id());
        values.put(chaine,replay.getChaine());
        values.put(duration,replay.getDuree());
        values.put(date,replay.getDate());

        bdd.insert(TABLE_REPLAYS,null,values);
        bdd.close();
    }
    public void insertReplays(List<Replay> replays){
        bdd = myHelper.getWritableDatabase();
        bdd.beginTransaction();
        for (Replay replay:replays) {
            ContentValues values = new ContentValues();
            values.put(title, replay.getTitle());
            values.put(description, replay.getDescription());
            values.put(link, replay.getLink());
            values.put(plateforme, replay.getPlateforme());
            values.put(image, replay.getImage());
            values.put(views, replay.getViews());
            values.put(id, replay.getReplay_id());
            values.put(chaine, replay.getChaine());
            values.put(duration, replay.getDuree());
            values.put(date, replay.getDate());
            bdd.insert(TABLE_REPLAYS, null, values);
        }
        bdd.setTransactionSuccessful();
        bdd.endTransaction();
        bdd.close();
    }
    public Replay getReplay(String replay_id){
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_REPLAYS, new String[]{"*"},id + " LIKE ?",new String[]{replay_id}, null,
                null, null);
        if (cursor.moveToNext()){
            Replay replay = new Replay(cursor.getString(0),cursor.getString(1));
            replay.setDescription(cursor.getString(2));
            replay.setChaine(cursor.getString(3));
            replay.setLink(cursor.getString(4));
            replay.setPlateforme(cursor.getString(5));
            replay.setImage(cursor.getString(6));
            replay.setViews(cursor.getInt(7));
            replay.setDuree(cursor.getString(8));
            replay.setDate(cursor.getString(9));
            return replay;
        }
        cursor.close();
        bdd.close();

        return null;
    }
    public List<Replay> getAll(){
        List<Replay> replays = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Replay replay;
        Cursor cursor = bdd.query(TABLE_REPLAYS, new String[]{"*"},null,null,null,
                null, null);
        while (cursor.moveToNext()){
            replay = new Replay(cursor.getString(0),cursor.getString(1));
            replay.setDescription(cursor.getString(2));
            replay.setChaine(cursor.getString(3));
            replay.setLink(cursor.getString(4));
            replay.setPlateforme(cursor.getString(5));
            replay.setImage(cursor.getString(6));
            replay.setViews(cursor.getInt(7));
            replay.setDuree(cursor.getString(8));
            replay.setDate(cursor.getString(9));
            replays.add(replay);
        }
        cursor.close();
        bdd.close();

        return replays;
    }

    public List<Replay> getMoreREcents(int nbr){
        List<Replay> replays = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Replay replay;
        Cursor cursor = bdd.query(TABLE_REPLAYS, new String[]{"*"},null,null,null,
                null, date + " DESC");
        while (cursor.moveToNext() && replays.size()<25){
            replay = new Replay(cursor.getString(0),cursor.getString(1));
            replay.setDescription(cursor.getString(2));
            replay.setChaine(cursor.getString(3));
            replay.setLink(cursor.getString(4));
            replay.setPlateforme(cursor.getString(5));
            replay.setImage(cursor.getString(6));
            replay.setViews(cursor.getInt(7));
            replay.setDuree(cursor.getString(8));
            replay.setDate(cursor.getString(9));
            replays.add(replay);
        }
        cursor.close();
        bdd.close();

        Collections.shuffle(replays);

        return (replays.size()>=nbr)? replays.subList(0,nbr):replays;
    }

    public List<Replay> getMorePopular(int nbr){
        List<Replay> replays = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Replay replay;
        Cursor cursor = bdd.query(TABLE_REPLAYS, new String[]{"*"},null,null,null,
                null, views + " DESC");
        while (cursor.moveToNext() && replays.size()<25){
            replay = new Replay(cursor.getString(0),cursor.getString(1));
            replay.setDescription(cursor.getString(2));
            replay.setChaine(cursor.getString(3));
            replay.setLink(cursor.getString(4));
            replay.setPlateforme(cursor.getString(5));
            replay.setImage(cursor.getString(6));
            replay.setViews(cursor.getInt(7));
            replay.setDuree(cursor.getString(8));
            replay.setDate(cursor.getString(9));
            replays.add(replay);
        }
        cursor.close();
        bdd.close();
        Collections.shuffle(replays);
        return (replays.size()>=nbr)? replays.subList(0,nbr):replays;

    }

    public List<Replay> getFromTv(String tv_id){
        List<Replay> replays = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Replay replay;
        Cursor cursor = bdd.query(TABLE_REPLAYS, new String[]{"*"},chaine + " LIKE ?",new String[]{tv_id},
                null, null, views + " DESC");
        while (cursor.moveToNext()){
            replay = new Replay(cursor.getString(0),cursor.getString(1));
            replay.setDescription(cursor.getString(2));
            replay.setChaine(cursor.getString(3));
            replay.setLink(cursor.getString(4));
            replay.setPlateforme(cursor.getString(5));
            replay.setImage(cursor.getString(6));
            replay.setViews(cursor.getInt(7));
            replay.setDuree(cursor.getString(8));
            replay.setDate(cursor.getString(9));
            replays.add(replay);
        }
        cursor.close();
        bdd.close();
        return replays;
    }

    public List<Replay> getRandom(int nbr){
        List<Replay> replays = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Replay replay;
        Cursor cursor = bdd.query(TABLE_REPLAYS, new String[]{"*"},null,null,null,
                null, views + " ASC");
        while (cursor.moveToNext() && replays.size()<25){
            replay = new Replay(cursor.getString(0),cursor.getString(1));
            replay.setDescription(cursor.getString(2));
            replay.setChaine(cursor.getString(3));
            replay.setLink(cursor.getString(4));
            replay.setPlateforme(cursor.getString(5));
            replay.setImage(cursor.getString(6));
            replay.setViews(cursor.getInt(7));
            replay.setDuree(cursor.getString(8));
            replay.setDate(cursor.getString(9));
            replays.add(replay);
        }
        cursor.close();
        bdd.close();
        Collections.shuffle(replays);
        return (replays.size()>=nbr)? replays.subList(0,nbr):replays;
    }

    public void updateViews(String replay_id,int new_vues){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(views,new_vues);
        bdd.update(TABLE_REPLAYS,values,id + " LIKE ?",new String[]{replay_id});
        bdd.close();
    }

    public void updateViews(Map<String,Integer> updates){
        bdd = myHelper.getWritableDatabase();
        bdd.beginTransaction();
        for (String replay_id:updates.keySet()) {
            ContentValues values = new ContentValues();
            values.put(views, updates.get(replay_id));
            bdd.update(TABLE_REPLAYS, values, id + " LIKE ?", new String[]{replay_id});
        }
        bdd.setTransactionSuccessful();
        bdd.endTransaction();
    }

    public List<String> getLast_Id(){
        List<String> ids = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_REPLAYS,new String[]{id},null,null,
                null,null,null);

        if (cursor !=null) {
            while (cursor.moveToNext()) {
                ids.add(cursor.getString(0));
            }
            cursor.close();
        }
        bdd.close();
        return ids;
    }

    public boolean verifyReplayId(String replay_id){
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_REPLAYS,new String[]{id}, id + " LIKE ?", new String[]{replay_id},
                null,null,null);

        if (cursor != null && cursor.moveToNext() && cursor.getString(0).equals(replay_id)){
            cursor.close();
            return true;
        }
        bdd.close();
        return false;
    }
    public void deleteReplay(String replay_id){
        bdd = myHelper.getWritableDatabase();
        bdd.delete(TABLE_REPLAYS, id + " LIKE ?", new String[]{replay_id});
        bdd.close();
    }

    public void deleteReplays(List<String> replay_ids){
        bdd = myHelper.getWritableDatabase();
        bdd.beginTransaction();
        for (String replay_id:replay_ids)
            bdd.delete(TABLE_REPLAYS, id + " LIKE ?", new String[]{replay_id});
        bdd.setTransactionSuccessful();
        bdd.endTransaction();
        bdd.close();
    }

    public Map<String,Integer> getSourceInfos(String source_id){

        HashMap<String,Integer> infos = new HashMap<>();
        bdd = myHelper.getWritableDatabase();

        int nbr_views = 0;

        Cursor cursor = bdd.query(TABLE_REPLAYS,new String[]{views},chaine + " LIKE ?",new String[]{source_id},
                null,null,null);

        if (cursor !=null) {
            while (cursor.moveToNext())
                nbr_views += cursor.getInt(0);
            infos.put("views",nbr_views);
            infos.put("total",cursor.getCount());
            cursor.close();
        }
        bdd.close();

        return infos;
    }
}