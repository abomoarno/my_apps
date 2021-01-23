package dev.casalov.projetetincel.db_mangment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.casalov.projetetincel.native_db_managment.NativeOperationsHelper;
import dev.casalov.projetetincel.utils.Operation;

public class OperationsManager {

    private static final String OPERATION_ID = "operation_id";
    private static final String OPERATION_STATUT = "operation_statut";
    private static final String RAPPORT_ID = "rapport_id";
    private static final String GAMME_ID = "GAMME_ID";
    private static final String OBSERVATION = "OBSERVATION";
    private static final String COMPARTIMENT_ID = "COMPARTIMENT_ID";
    private static final String NOM = "nom";

    private static final String TABLE_OPERATIONS = "operations";

    private static final String DB_NAME = "operations";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase bdd;
    private OperationsHelper helper;


    public OperationsManager(Context context){
        helper = new OperationsHelper(context,DB_NAME,null,DB_VERSION);
    }

    public List<Operation> getFromCompartiment(String compartiment_id,String maintenance_id, String project_id){
        List<Operation> operations = new ArrayList<>();
        bdd = helper.getWritableDatabase();

        Cursor cursor = bdd.query(
                TABLE_OPERATIONS,
                new String[]{"*"},
                COMPARTIMENT_ID + " LIKE ? AND " + RAPPORT_ID + " LIKE ? AND " + GAMME_ID + " LIKE ?",
                new String[]{compartiment_id, project_id, maintenance_id},
                null,
                null,null
        );

        if (cursor != null){
            while (cursor.moveToNext()){
                Operation operation = new Operation(cursor.getString(0),cursor.getString(1));
                operation.setProjet_id(cursor.getString(2));
                operation.setGamme_id(cursor.getString(3));
                operation.setCompartiment_id(cursor.getString(4));
                operation.setStatut(cursor.getInt(5));
                operation.setObservations(cursor.getString(6));
                operations.add(operation);
            }
            cursor.close();
        }
        bdd.close();
        return operations;
    }

    public void insertOperation(Operation operation){
        bdd = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OPERATION_ID, operation.getOperation_id());
        values.put(RAPPORT_ID, operation.getProjet_id());
        values.put(GAMME_ID, operation.getGamme_id());
        values.put(COMPARTIMENT_ID, operation.getCompartiment_id());
        values.put(NOM, operation.getNom());
        values.put(OBSERVATION, operation.getObservations());
        values.put(OPERATION_STATUT, operation.isStatut() );

        bdd.insert(TABLE_OPERATIONS,null,values);

        bdd.close();
    }

    public List<String> getGammes(String project_id){
        List<String> gammes = new ArrayList<>();
        bdd = helper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_OPERATIONS, new String[]{GAMME_ID}, RAPPORT_ID + " LIKE ?",
                new String[]{project_id}, GAMME_ID, null, null);
        if (cursor !=null)
        {
            while (cursor.moveToNext())
                gammes.add(cursor.getString(0));
            cursor.close();
        }

        bdd.close();
        return gammes;
    }

    public List<String> getCompartiments(String project_id, String gamme_id){
        List<String> gammes = new ArrayList<>();
        bdd = helper.getWritableDatabase();

        Cursor cursor = bdd.query(
                TABLE_OPERATIONS,
                new String[]{COMPARTIMENT_ID},
                RAPPORT_ID + " LIKE ? AND " + GAMME_ID + " LIKE ?",
                new String[]{project_id, gamme_id}, COMPARTIMENT_ID, null, null);
        if (cursor !=null)
        {
            while (cursor.moveToNext())
                gammes.add(cursor.getString(0));
            cursor.close();
        }

        bdd.close();
        return gammes;
    }
    public void updateOperation(Operation operation){
        bdd = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OPERATION_STATUT,operation.isStatut());
        values.put(OBSERVATION,operation.getObservations());
        bdd.update(
                TABLE_OPERATIONS,
                values,
                OPERATION_ID + " LIKE ?",
                new String[]{operation.getOperation_id()}
        );

        bdd.close();
    }
    public void deleteOperation(String gamme_id,String project_id){
        bdd = helper.getWritableDatabase();
        bdd.delete(
                TABLE_OPERATIONS,
                GAMME_ID + " LIKE ? AND " + RAPPORT_ID + " LIKE ?",
                new String[]{gamme_id, project_id}
        );

        bdd.close();
    }
    public void deleteOperation(String project_id){
        bdd = helper.getWritableDatabase();
        bdd.delete(
                TABLE_OPERATIONS,
                RAPPORT_ID + " LIKE ?",
                new String[]{project_id}
        );

        bdd.close();
    }
}
