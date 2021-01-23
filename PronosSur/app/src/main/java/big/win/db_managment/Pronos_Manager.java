package big.win.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import big.win.classes.Pronostique;

public class Pronos_Manager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "pronosur";

    private static final String TABLE_PRONOSTICS = "pronostics";
    private static final String id ="id";
    private static final String pays = "pays";
    private static final String hour = "hour";
    private static final String cote = "cote";
    private static final String pronostic = "pronostic";
    private static final String result = "result";
    private static final String category = "category";
    private static final String name_teamA = "name_A";
    private static final String scoreA = "scoreA";
    private static final String name_teamB = "name_B";
    private static final String scoreB = "scoreB";
    private static final String notify = "notified";

    private SQLiteDatabase bdd;
    private Db_Helper myHelper;

    public Pronos_Manager(Context context
    ) {
        myHelper = new Db_Helper(context,NAME_DB,null,VERSION_BDD);
    }

    public void insertProno(Pronostique prono){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(id,prono.getId());
        values.put(pays,prono.getPays());
        values.put(hour,prono.getHours());
        values.put(pronostic,prono.getPronostic());
        values.put(result,prono.getResult());
        values.put(category,prono.getPremium());
        values.put(cote,prono.getCote());
        values.put(name_teamA,prono.getName_teamA());
        values.put(scoreA,prono.getScoreA());
        values.put(name_teamB,prono.getName_teamB());
        values.put(scoreB,prono.getScoreB());
        values.put(notify,prono.getNotified());

        bdd.insert(TABLE_PRONOSTICS,null,values);
        bdd.close();
    }

    public void insertPronos(List<Pronostique> pronos){
        bdd = myHelper.getWritableDatabase();
        bdd.beginTransaction();

        for (Pronostique prono:pronos) {
            ContentValues values = new ContentValues();
            values.put(id, prono.getId());
            values.put(pays, prono.getPays());
            values.put(hour, prono.getHours());
            values.put(pronostic, prono.getPronostic());
            values.put(result, prono.getResult());
            values.put(category, prono.getPremium());
            values.put(cote, prono.getCote());
            values.put(name_teamA, prono.getName_teamA());
            values.put(scoreA, prono.getScoreA());
            values.put(name_teamB, prono.getName_teamB());
            values.put(scoreB, prono.getScoreB());
            values.put(notify, prono.getNotified());
            bdd.insert(TABLE_PRONOSTICS, null, values);

        }
        bdd.setTransactionSuccessful();
        bdd.endTransaction();
        bdd.close();
    }

    public void updateProno(Pronostique prono){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(pays,prono.getPays());
        values.put(hour,prono.getHours());
        values.put(pronostic,prono.getPronostic());
        values.put(result,prono.getResult());
        values.put(category,prono.getPremium());
        values.put(cote,prono.getCote());
        values.put(name_teamA,prono.getName_teamA());
        values.put(scoreA,prono.getScoreA());
        values.put(name_teamB,prono.getName_teamB());
        values.put(scoreB,prono.getScoreB());
        bdd.update(TABLE_PRONOSTICS,values,id +" LIKE "+ prono.getId(),null);
        bdd.close();
    }

    public void updatePronos(List<Pronostique> pronos){
        bdd = myHelper.getWritableDatabase();
        bdd.beginTransaction();
        for (Pronostique prono:pronos) {
            ContentValues values = new ContentValues();

            values.put(pays, prono.getPays());
            values.put(hour, prono.getHours());
            values.put(pronostic, prono.getPronostic());
            values.put(result, prono.getResult());
            values.put(category, prono.getPremium());
            values.put(cote, prono.getCote());
            values.put(name_teamA, prono.getName_teamA());
            values.put(scoreA, prono.getScoreA());
            values.put(name_teamB, prono.getName_teamB());
            values.put(scoreB, prono.getScoreB());
            bdd.update(TABLE_PRONOSTICS, values, id + " LIKE " + prono.getId(), null);
        }
        bdd.setTransactionSuccessful();
        bdd.endTransaction();

        bdd.close();
    }

    public ArrayList<Pronostique> getAll(){

        ArrayList<Pronostique> results = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_PRONOSTICS, new String[]{"*"},null,null,null,
                null, id + " DESC");
        while (cursor.moveToNext()){
            Pronostique p = new Pronostique(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            p.setName_teamA(cursor.getString(7));
            p.setScoreA(cursor.getString(8));
            p.setName_teamB(cursor.getString(9));
            p.setScoreB(cursor.getString(10));
            p.setNotified(cursor.getInt(11));
            results.add(p);
        }
        bdd.close();
        cursor.close();
        return results;
    }

    public ArrayList<Pronostique> getHistory(){
        ArrayList<Pronostique> results = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Date date = new Date();
        String today = (1900+date.getYear()) + "-";
        today += (date.getMonth()+1>9)? (date.getMonth()+1): "0" + (date.getMonth()+1);
        today += "-";
        today += (date.getDate()>9)? date.getDate(): "0" + date.getDate();
        Cursor cursor = bdd.query(TABLE_PRONOSTICS, new String[]{"*"},null,null,null,
                null, id + " DESC");
        while (cursor.moveToNext()){
            Pronostique p = new Pronostique(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            p.setName_teamA(cursor.getString(7));
            p.setScoreA(cursor.getString(8));
            p.setName_teamB(cursor.getString(9));
            p.setScoreB(cursor.getString(10));
            p.setNotified(cursor.getInt(11));
            if (p.getHours().substring(0,10).compareTo(today)<0)
                results.add(p);
        }
        bdd.close();
        cursor.close();
        return results;
    }
    public ArrayList<Pronostique> getTodayFree(){
        ArrayList<Pronostique> results = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Date date = new Date();
        String today = (1900+date.getYear()) + "-";
        today += (date.getMonth()+1>9)? (date.getMonth()+1): "0" + (date.getMonth()+1);
        today += "-";
        today += (date.getDate()>9)? date.getDate(): "0" + date.getDate();
        Cursor cursor = bdd.query(TABLE_PRONOSTICS, new String[]{"*"},category + " LIKE ?",new String[]{"gratuit"},null,
                null, id + " DESC");
        while (cursor.moveToNext()){
            Pronostique p = new Pronostique(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            p.setName_teamA(cursor.getString(7));
            p.setScoreA(cursor.getString(8));
            p.setName_teamB(cursor.getString(9));
            p.setScoreB(cursor.getString(10));
            p.setNotified(cursor.getInt(11));
            if (p.getHours().substring(0,10).compareTo(today) == 0)
                results.add(p);
        }
        bdd.close();
        cursor.close();
        return results;
    }

    public ArrayList<Pronostique> getTodayPremium(){
        ArrayList<Pronostique> results = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Date date = new Date();
        String today = (1900+date.getYear()) + "-";
        today += (date.getMonth()+1>9)? (date.getMonth()+1): "0" + (date.getMonth()+1);
        today += "-";
        today += (date.getDate()>9)? date.getDate(): "0" + date.getDate();
        Cursor cursor = bdd.query(TABLE_PRONOSTICS, new String[]{"*"},category + " LIKE ?",new String[]{"premium"},null,
                null, id + " DESC");
        while (cursor.moveToNext()){
            Pronostique p = new Pronostique(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            p.setName_teamA(cursor.getString(7));
            p.setScoreA(cursor.getString(8));
            p.setName_teamB(cursor.getString(9));
            p.setScoreB(cursor.getString(10));
            p.setNotified(cursor.getInt(11));
            if (p.getHours().substring(0,10).compareTo(today) == 0)
                results.add(p);
        }
        bdd.close();
        cursor.close();
        return results;
    }
    public ArrayList<Pronostique> getTodayGoalGoal(){
        ArrayList<Pronostique> results = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Date date = new Date();
        String today = (1900+date.getYear()) + "-";
        today += (date.getMonth()+1>9)? (date.getMonth()+1): "0" + (date.getMonth()+1);
        today += "-";
        today += (date.getDate()>9)? date.getDate(): "0" + date.getDate();
        Cursor cursor = bdd.query(TABLE_PRONOSTICS, new String[]{"*"},category + " LIKE ?",new String[]{"goal_goal"},null,
                null, id + " DESC");
        while (cursor.moveToNext()){
            Pronostique p = new Pronostique(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            p.setName_teamA(cursor.getString(7));
            p.setScoreA(cursor.getString(8));
            p.setName_teamB(cursor.getString(9));
            p.setScoreB(cursor.getString(10));
            p.setNotified(cursor.getInt(11));
            if (p.getHours().substring(0,10).compareTo(today) == 0)
                results.add(p);
        }
        bdd.close();
        cursor.close();
        return results;
    }
    public ArrayList<Pronostique> getTodayBonus(){
        ArrayList<Pronostique> results = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Date date = new Date();
        String today = (1900+date.getYear()) + "-";
        today += (date.getMonth()+1>9)? (date.getMonth()+1): "0" + (date.getMonth()+1);
        today += "-";
        today += (date.getDate()>9)? date.getDate(): "0" + date.getDate();
        Cursor cursor = bdd.query(TABLE_PRONOSTICS, new String[]{"*"},category + " LIKE ?",new String[]{"bonus"},null,
                null, id + " DESC");
        while (cursor.moveToNext()){
            Pronostique p = new Pronostique(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            p.setName_teamA(cursor.getString(7));
            p.setScoreA(cursor.getString(8));
            p.setName_teamB(cursor.getString(9));
            p.setScoreB(cursor.getString(10));
            p.setNotified(cursor.getInt(11));
            if (p.getHours().substring(0,10).compareTo(today) == 0)
                results.add(p);
        }
        bdd.close();
        cursor.close();
        return results;
    }

    public int getLastId(){

        ArrayList<Pronostique> pronostiques = getAll();
        if (pronostiques.size() >0) {
            return pronostiques.get(0).getId();
        }
        return 0;
    }
    public List<Integer> getAllIds(){

        List<Integer> ids = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_PRONOSTICS,new String[]{id},
                null,null,
                null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                ids.add(cursor.getInt(0));
            }

            cursor.close();
        }
        bdd.close();
        return ids;
    }

    public void updateNotify(Pronostique prono){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(notify,prono.getNotified());
        bdd.update(TABLE_PRONOSTICS,values,id +" LIKE "+ prono.getId(),null);
        bdd.close();
    }

    public void deleteProno(int prono_id){
        bdd = myHelper.getWritableDatabase();
        bdd.delete(TABLE_PRONOSTICS,id + " LIKE ?", new String[]{""+prono_id});
        bdd.close();
    }

    public void deletePronos(List<Integer> prono_ids){
        bdd = myHelper.getWritableDatabase();
        bdd.beginTransaction();

        for (Integer prono_id:prono_ids)
            bdd.delete(TABLE_PRONOSTICS,id + " LIKE ?", new String[]{""+prono_id});
        bdd.setTransactionSuccessful();
        bdd.endTransaction();

        bdd.close();
    }
}
