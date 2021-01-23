package dev.casalov.projetetincel.native_db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NativeElementsManager {

    private static final String ELEMENT_ID = "element_id";
    private static final String ELEMENT_NOM = "element_nom";
    private static final String GAMME_ID = "gamme_id";

    private static final String TABLE_NATIVE_ELEMENTS = "native_elements";
    private static final String DB_NAME = "native_elements";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase bdd;
    private NativeElementsHelper helper;

    public NativeElementsManager(Context context) {
        helper = new NativeElementsHelper(context,DB_NAME,null,DB_VERSION);
    }

    public List<Map<String, String>> getFromGamme(String gamme_id){
        List<Map<String,String>> elements = new ArrayList<>();
        bdd = helper.getWritableDatabase();
        Cursor cursor = bdd.query(
                TABLE_NATIVE_ELEMENTS,
                new String[]{"*"},
                GAMME_ID + " LIKE ?",
                new String[]{gamme_id},
                null,
                null,null
        );

        if (cursor != null){
            while (cursor.moveToNext()){
                Map<String, String> element = new HashMap<>();
                element.put("element_id",cursor.getString(0));
                element.put("element_nom",cursor.getString(1));
                element.put("gamme_id",cursor.getString(2));
                elements.add(element);
            }
            cursor.close();
        }
        bdd.close();
        return elements;
    }

    public Map<String, String> getElement(String element_id){

        bdd = helper.getWritableDatabase();
        Map<String, String> element = new HashMap<>();
        Cursor cursor = bdd.query(
                TABLE_NATIVE_ELEMENTS,
                new String[]{"*"},
                ELEMENT_ID + " LIKE ?",
                new String[]{element_id},
                null,
                null,null
        );

        if (cursor != null && cursor.moveToNext()){
            element.put("element_id",cursor.getString(0));
            element.put("element_nom",cursor.getString(1));
            element.put("gamme_id",cursor.getString(2));
            cursor.close();
        }
        bdd.close();
        return element;
    }

    public void insertElement(Map<String,String> element){

        Log.e(element.get("element_id"),element.get("element_nom")+" " + element.get("gamme_id"));
        bdd = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ELEMENT_ID,element.get("element_id"));
        values.put(ELEMENT_NOM,element.get("element_nom"));
        values.put(GAMME_ID,element.get("gamme_id"));

        bdd.insert(TABLE_NATIVE_ELEMENTS,null,values);

        bdd.close();
    }
    public void updateElement(Map<String,String> element){
        bdd = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ELEMENT_NOM,element.get("element_nom"));
        values.put(GAMME_ID,element.get("gamme_id"));
        bdd.update(
                TABLE_NATIVE_ELEMENTS,
                values,
                ELEMENT_ID + " LIKE ?",
                new String[]{element.get("element_id")}
        );
        bdd.close();
    }
    private void deleteElement(String element_id){
        bdd = helper.getWritableDatabase();

        bdd.delete(
                TABLE_NATIVE_ELEMENTS,
                ELEMENT_ID + " LIKE ?",
                new String[]{element_id}
        );
        bdd.close();
    }
}
