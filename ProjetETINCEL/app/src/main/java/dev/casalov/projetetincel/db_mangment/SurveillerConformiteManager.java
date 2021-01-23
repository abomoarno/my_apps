package dev.casalov.projetetincel.db_mangment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveillerConformiteManager {

    private static final String ELEMENT_ID = "element_id";
    private static final String ELEMENT_VALEUR = "element_valeur";
    private static final String PROJECT_ID = "project_id";
    private static final String ELEMENT_NOM = "element_nom";
    private static final String ELEMENT_TYPE = "element_type";

    private static final String TABLE_SURVEILLER = "surveiller_conformite";
    private static final String DB_NAME = "surveiller_conformite";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase bdd;
    private SurveillerConformiteHelper helper;

    public SurveillerConformiteManager(Context context) {
        helper = new SurveillerConformiteHelper(context,DB_NAME,null,DB_VERSION);
    }

    public List<Map<String, String>> getFromProject(String project_id,String type){
        List<Map<String,String>> elements = new ArrayList<>();

        bdd = helper.getWritableDatabase();

        Cursor cursor = bdd.query(
                TABLE_SURVEILLER,
                new String[]{"*"},
                PROJECT_ID + " LIKE ? AND " + ELEMENT_TYPE + " LIKE ?",
                new String[]{project_id, type},
                null,
                null,null
        );

        if (cursor != null){
            while (cursor.moveToNext()){
                Map<String, String> element = new HashMap<>();
                element.put("element_id",cursor.getString(0));
                element.put("element_valeur",cursor.getString(1));
                element.put("project_id",cursor.getString(2));
                element.put("element_nom",cursor.getString(3));
                element.put("element_type",cursor.getString(4));
                elements.add(element);
            }
            cursor.close();
        }
        bdd.close();
        return elements;
    }
    public List<Map<String, String>> getFromProject(String project_id){
        List<Map<String,String>> elements = new ArrayList<>();

        bdd = helper.getWritableDatabase();

        Cursor cursor = bdd.query(
                TABLE_SURVEILLER,
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
                element.put("element_valeur",cursor.getString(1));
                element.put("project_id",cursor.getString(2));
                element.put("element_nom",cursor.getString(3));
                element.put("element_type",cursor.getString(4));
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
        values.put(ELEMENT_VALEUR,element.get("element_valeur"));
        values.put(PROJECT_ID,element.get("project_id"));
        values.put(ELEMENT_NOM,element.get("element_nom"));
        values.put(ELEMENT_TYPE,element.get("element_type"));

        bdd.insert(TABLE_SURVEILLER,null,values);

        bdd.close();
    }
    public void deleteElement(String element_id, String project_id){
        bdd = helper.getWritableDatabase();

        bdd.delete(
                TABLE_SURVEILLER,
                ELEMENT_ID + " LIKE ? AND " + PROJECT_ID + " LIKE ?",
                new String[]{element_id, project_id}
        );

        bdd.close();
    }

    public void deleteElement(String project_id){
        bdd = helper.getWritableDatabase();

        bdd.delete(
                TABLE_SURVEILLER,
                PROJECT_ID + " LIKE ?",
                new String[]{project_id}
        );

        bdd.close();
    }
}
