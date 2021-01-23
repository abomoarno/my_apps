package dev.casalov.projetetincel.native_db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NativeCompartimentsManager {

    private static final String COMPARTIMENT_ID = "compartiment_id";
    private static final String COMPARTIMENT_NOM = "compartiment_nom";
    private static final String GAMME_ID = "gamme_id";

    private static final String TABLE_NATIVE_COMPARTIMENT = "native_compartiment";
    private static final String DB_NAME = "native_compartiment";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase bdd;
    private NativeCompartimentsHelper helper;

    public NativeCompartimentsManager(Context context) {
        helper = new NativeCompartimentsHelper(context,DB_NAME,null,DB_VERSION);
    }

    public List<Map<String, String>> getFromGamme(String gamme_id){
        List<Map<String,String>> compartiments = new ArrayList<>();

        bdd = helper.getWritableDatabase();

        Cursor cursor = bdd.query(
                TABLE_NATIVE_COMPARTIMENT,
                new String[]{"*"},
                GAMME_ID + " LIKE ?",
                new String[]{gamme_id},
                null,
                null,null
        );

        if (cursor != null){
            while (cursor.moveToNext()){
                Map<String, String> compartiment = new HashMap<>();
                compartiment.put("compartiment_id",cursor.getString(0));
                compartiment.put("compartiment_nom",cursor.getString(1));
                compartiment.put("gamme_id",cursor.getString(2));
                compartiments.add(compartiment);
            }
            cursor.close();
        }
        bdd.close();
        return compartiments;
    }

    public Map<String, String> getCompartiment(String compartiment_id){

        bdd = helper.getWritableDatabase();
        Map<String, String> compartiment = new HashMap<>();
        Cursor cursor = bdd.query(
                TABLE_NATIVE_COMPARTIMENT,
                new String[]{"*"},
                COMPARTIMENT_ID + " LIKE ?",
                new String[]{compartiment_id},
                null,
                null,null
        );

        if (cursor != null && cursor.moveToNext()){
            compartiment.put("compartiment_id",cursor.getString(0));
            compartiment.put("compartiment_nom",cursor.getString(1));
            compartiment.put("gamme_id",cursor.getString(2));
            cursor.close();
        }
        bdd.close();
        return compartiment;
    }

    public void insertCompartiment(Map<String,String> compartiment){
        bdd = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COMPARTIMENT_ID,compartiment.get("compartiment_id"));
        values.put(COMPARTIMENT_NOM,compartiment.get("compartiment_nom"));
        values.put(GAMME_ID,compartiment.get("gamme_id"));

        bdd.insert(TABLE_NATIVE_COMPARTIMENT,null,values);

        bdd.close();
    }
    public void updateCompartiment(Map<String,String> compartiment){
        bdd = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COMPARTIMENT_NOM,compartiment.get("compartiment_nom"));
        values.put(GAMME_ID,compartiment.get("gamme_id"));

        bdd.update(
                TABLE_NATIVE_COMPARTIMENT,
                values,
                COMPARTIMENT_ID + " LIKE ?",
                new String[]{compartiment.get("compartiment_id")}
                );

        bdd.close();
    }

    private void deleteCompartiment(String compartiment_id){
        bdd = helper.getWritableDatabase();

        bdd.delete(
                TABLE_NATIVE_COMPARTIMENT,
                COMPARTIMENT_ID + " LIKE ?",
                new String[]{compartiment_id}
        );

        bdd.close();
    }
}