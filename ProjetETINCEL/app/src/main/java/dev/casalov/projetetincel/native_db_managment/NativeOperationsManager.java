package dev.casalov.projetetincel.native_db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NativeOperationsManager {

    private static final String OPERATION_ID = "operation_id";
    private static final String OPERATION_NOM = "operation_nom";
    private static final String COMPARTIMENT_ID = "compartiment_id";

    private static final String TABLE_OPERATIONS = "native_operations";

    private static final String DB_NAME = "native_operations";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase bdd;
    private NativeOperationsHelper helper;

    public NativeOperationsManager(Context context){
        helper = new NativeOperationsHelper(context,DB_NAME,null,DB_VERSION);
    }

    public List<Map<String, String>> getFromCompartiment(String compartiment_id){
        List<Map<String,String>> operations = new ArrayList<>();

        bdd = helper.getWritableDatabase();

        Cursor cursor = bdd.query(
                TABLE_OPERATIONS,
                new String[]{"*"},
                COMPARTIMENT_ID + " LIKE ?",
                new String[]{compartiment_id},
                null,
                null,null
        );

        if (cursor != null){
            while (cursor.moveToNext()){
                Map<String, String> operation = new HashMap<>();
                operation.put("operation_id",cursor.getString(0));
                operation.put("operation_nom",cursor.getString(2));
                operation.put("compartiment_id",cursor.getString(1));
                operations.add(operation);
            }
            cursor.close();
        }
        bdd.close();
        return operations;
    }

    public void insertOperation(Map<String,String> element){
        bdd = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OPERATION_ID,element.get("operation_id"));
        values.put(OPERATION_NOM,element.get("operation_nom"));
        values.put(COMPARTIMENT_ID,element.get("compartiment_id"));

        bdd.insert(TABLE_OPERATIONS,null,values);

        bdd.close();
    }
    public void updateOperation(Map<String,String> operation){
        bdd = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OPERATION_NOM,operation.get("operation_nom"));
        values.put(COMPARTIMENT_ID,operation.get("compartiment_id"));

        bdd.update(
                TABLE_OPERATIONS,
                values,
                OPERATION_ID + " LIKE ?",
                new String[]{operation.get("operation_id")}
        );

        bdd.close();
    }

    private void deleteOperation(String operation_id){
        bdd = helper.getWritableDatabase();

        bdd.delete(
                TABLE_OPERATIONS,
                OPERATION_ID + " LIKE ?",
                new String[]{operation_id}
        );

        bdd.close();
    }
}
