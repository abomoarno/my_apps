package niamoro.annonces.databases.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import niamoro.annonces.databases.helpers.PaysHelper;
import niamoro.annonces.utils.Pays;

public class PaysManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "afrimoov_annonces_pays";

    private static final String ID = "code";
    private static final String TITRE = "nom";


    private static final String TABLE_PAYS = "pays";

    private PaysHelper helper;
    private SQLiteDatabase db;

    public PaysManager(Context context){
        helper = new PaysHelper(context,NAME_DB,null,VERSION_BDD);
    }

    public void insertPays(Pays pays){
        if (pays == null)
            return;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID,pays.getCode());
        values.put(TITRE,pays.getNom());
        db.insert(TABLE_PAYS,null,values);
        db.close();
    }

    public void insertPays(List<Pays> pays){
        if (pays == null || pays.isEmpty())
            return;
        db = helper.getWritableDatabase();
        db.beginTransaction();

        for (Pays one_pays:pays) {
            ContentValues values = new ContentValues();

            values.put(ID, one_pays.getCode());
            values.put(TITRE, one_pays.getNom());
            db.insert(TABLE_PAYS, null, values);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void updatePays(List<Pays> pays){
        if (pays == null || pays.isEmpty())
            return;
        db = helper.getWritableDatabase();
        db.beginTransaction();

        for (Pays one_pays:pays) {
            ContentValues values = new ContentValues();

            values.put(TITRE, one_pays.getNom());
            db.update(TABLE_PAYS,values, ID + " LIKE ?", new String[]{one_pays.getCode()});
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void updatePays(Pays pays){
        if (pays == null)
            return;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITRE,pays.getNom());
        db.update(TABLE_PAYS,values, ID + " LIKE ?", new String[]{pays.getCode()});
        db.close();
    }

    public void deletePays(String pays){
        if (pays == null || pays.isEmpty())
            return;
        db = helper.getWritableDatabase();
        db.delete(TABLE_PAYS,ID + " LIKE ?", new String[]{pays});
        db.close();
    }

    public void deletePays(List<String> pays){
        if (pays == null || pays.isEmpty())
            return;
        db = helper.getWritableDatabase();
        db.beginTransaction();

        for (String st:pays){
            db.delete(TABLE_PAYS,ID + " LIKE ?", new String[]{st});
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public Pays getPays(String pays_id){
        Pays pays = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_PAYS,
                new String[]{"*"},
                ID + " LIKE ?",
                new String[]{pays_id},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToNext()){
            pays = new Pays();
            pays.setCode(cursor.getString(0));
            pays.setNom(cursor.getString(1));

            cursor.close();
        }
        db.close();
        return pays;
    }

    public List<Pays> getPays(){
        List<Pays> pays = new ArrayList<>();
        db = helper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_PAYS,new String[]{"*"}, null, null,
                null, null, null);

        if (cursor != null){
            Pays one_pays;
            while (cursor.moveToNext()){
                one_pays = new Pays();
                one_pays.setCode(cursor.getString(0));
                one_pays.setNom(cursor.getString(1));
                pays.add(one_pays);
            }
            cursor.close();
        }

        db.close();
        return pays;
    }

    public List<String> getAllIds(){
        db = helper.getWritableDatabase();
        List<String> ids = new ArrayList<>();
        Cursor cursor = db.query(TABLE_PAYS,new String[]{ID},null,
                null,null,null, null );
        if (cursor != null){
            while (cursor.moveToNext())
                ids.add(cursor.getString(0));
            cursor.close();
        }

        db.close();

        return ids;
    }
}
