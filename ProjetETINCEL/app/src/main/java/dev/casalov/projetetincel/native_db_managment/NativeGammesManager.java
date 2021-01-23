package dev.casalov.projetetincel.native_db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class NativeGammesManager {

    private static final String GAMME_ID = "gamme_id";
    private static final String GAMME_NOM = "gamme_nom";

    private static final String TABLE_GAMMES = "native_gammes";

    private static final String DB_NAME = "etincel_gammes";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase bdd;
    private NativeGammesHelper helper;


    public NativeGammesManager(Context context) {
        helper = new NativeGammesHelper(context, DB_NAME,null,DB_VERSION);
    }

    public void inserGamme(String id, String nom){
        if (verifyGamme(id))
            return;
        ContentValues values = new ContentValues();
        bdd = helper.getWritableDatabase();
        values.put(GAMME_ID,id);
        values.put(GAMME_NOM,nom);
        bdd.insert(TABLE_GAMMES, null, values);
        bdd.close();
    }

    public void updateGamme(String id, String nom){
        if (!verifyGamme(id))
            return;
        ContentValues values = new ContentValues();
        bdd = helper.getWritableDatabase();
        values.put(GAMME_NOM,nom);
        bdd.update(TABLE_GAMMES,values, GAMME_ID + " LIKE ?", new String[]{id});
        bdd.close();
    }

    public Map<String, String> getAllGammes(){
        Map<String, String> gammes = new HashMap<>();
        bdd = helper.getWritableDatabase();

        Cursor cursor = bdd.query(
                TABLE_GAMMES,
                new String[]{"*"},
                null,
                null,
                null,
                null,
                null
                );

        if (cursor != null){
            while (cursor.moveToNext()){
                gammes.put(cursor.getString(0),cursor.getString(1));
            }

            cursor.close();
        }
        bdd.close();
        return gammes;
    }

    public void deleteGamme(String id){
        if (!verifyGamme(id))
            return;
        bdd = helper.getWritableDatabase();
        bdd.delete(TABLE_GAMMES, GAMME_ID + " LIKE ?", new String[]{id});
        bdd.close();
    }

    private boolean verifyGamme(String id){
        bdd = helper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_GAMMES,
                new String[]{GAMME_ID},
                GAMME_ID + " LIKE ?",
                new String[]{id},null,null,null );
        if (cursor != null && cursor.moveToNext()){
            cursor.close();
            bdd.close();
            return true;
        }
        return false;
    }
}
