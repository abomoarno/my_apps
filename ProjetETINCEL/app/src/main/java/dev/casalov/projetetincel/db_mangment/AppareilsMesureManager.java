package dev.casalov.projetetincel.db_mangment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppareilsMesureManager {

    private static final String ELEMENT_ID = "element_id";
    private static final String ELEMENT_SERIE = "element_serie";
    private static final String PROJECT_ID = "project_id";
    private static final String ELEMENT_NOM = "element_nom";
    private static final String ELEMENT_VALIDITE = "element_validite";

    private static final String TABLE_APPAREILS = "appareils";
    private static final String DB_NAME = "appareils";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase bdd;
    private AppareilsMesureHelper helper;

    public AppareilsMesureManager(Context context) {
        helper = new AppareilsMesureHelper(context,DB_NAME,null,DB_VERSION);
    }
    public List<Map<String, String>> getFromProject(String project_id){
        List<Map<String,String>> elements = new ArrayList<>();

        bdd = helper.getWritableDatabase();

        Cursor cursor = bdd.query(
                TABLE_APPAREILS,
                new String[]{"*"},
                PROJECT_ID + " LIKE ?" ,
                new String[]{project_id},
                null,
                null,null
        );

        if (cursor != null){
            while (cursor.moveToNext()){
                Map<String, String> element = new HashMap<>();
                element.put("element_id",cursor.getString(0));
                element.put("element_serie",cursor.getString(1));
                element.put("project_id",cursor.getString(2));
                element.put("element_nom",cursor.getString(3));
                element.put("element_validite",cursor.getString(4));
                elements.add(element);
            }
            cursor.close();
        }
        bdd.close();
        return elements;
    }

    public void insertElement(Map<String,String> element){
        bdd = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ELEMENT_ID,element.get("element_id"));
        values.put(ELEMENT_SERIE,element.get("element_serie"));
        values.put(PROJECT_ID,element.get("project_id"));
        values.put(ELEMENT_NOM,element.get("element_nom"));
        values.put(ELEMENT_VALIDITE,element.get("element_validite"));

        bdd.insert(TABLE_APPAREILS,null,values);

        bdd.close();
    }
    public void deleteElement(String element_id, String project_id){
        bdd = helper.getWritableDatabase();

        bdd.delete(
                TABLE_APPAREILS,
                ELEMENT_ID + " LIKE ? AND " + PROJECT_ID + " LIKE ?",
                new String[]{element_id, project_id}
        );

        bdd.close();
    }

    public void deleteElement(String project_id){
        bdd = helper.getWritableDatabase();

        bdd.delete(
                TABLE_APPAREILS,
                PROJECT_ID + " LIKE ?",
                new String[]{project_id}
        );

        bdd.close();
    }
}