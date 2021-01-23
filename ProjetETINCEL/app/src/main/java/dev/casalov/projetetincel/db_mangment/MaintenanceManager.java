package dev.casalov.projetetincel.db_mangment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dev.casalov.projetetincel.utils.Devis;
import dev.casalov.projetetincel.utils.Maintenance;

public class MaintenanceManager {

    private static String DB_NAME = "maintenances";
    private static int VERSION = 1;

    private static final String MAINTENANCE_ID = "maintenance_id";
    private static final String titre = "titre";
    private static final String PROJECT_ID = "project_id";
    private static final String INITIAL_ID = "initial_id";


    private static String TABLE_MAINTENANCES = "maintenances";

    private MaintenanceHelper helper;
    private Context context;
    private SQLiteDatabase db;

    public MaintenanceManager(Context context){
        helper = new MaintenanceHelper(context, DB_NAME,null,VERSION);
        this.context = context;
    }

    public void insertMaintenance(Maintenance maintenance){

        try {

            ContentValues values = new ContentValues();
            values.put(MAINTENANCE_ID, maintenance.getMaintenance_id());
            values.put(titre, maintenance.getTitle());
            values.put(PROJECT_ID, maintenance.getProjet_id());
            values.put(INITIAL_ID, maintenance.getInitial_id());
            db = helper.getWritableDatabase();
            db.insert(TABLE_MAINTENANCES, null, values);
            db.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Log.e("DEVIS","REGISTERED");
    }

    public void updateMaintenance(Maintenance maintenance){

        try {

            ContentValues values = new ContentValues();
            values.put(titre, maintenance.getTitle());

            db = helper.getWritableDatabase();
            db.update(TABLE_MAINTENANCES,values, MAINTENANCE_ID + " LIKE ?", new String[]{maintenance.getMaintenance_id()});
            db.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Log.e("DEVIS","REGISTERED");
    }

    public List<Maintenance> getAllMaintenances(String project_id){

        List<Maintenance> maintenances = new ArrayList<>();
        Maintenance maintenance = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_MAINTENANCES,new String[]{"*"}, PROJECT_ID + " LIKE ?",
                new String[]{project_id}, null, null,null);

        if (cursor !=  null){
            while (cursor.moveToNext()){
                maintenance = new Maintenance(cursor.getString(1), cursor.getString(3));
                maintenance.setProjet_id(cursor.getString(2));
                maintenance.setMaintenance_id(cursor.getString(0));
                maintenances.add(maintenance);
            }
            cursor.close();
        }
        return maintenances;
    }

    public void deleteMaintenance(Maintenance maintenance){
        db = helper.getWritableDatabase();
        new PhotoManager(context).deletePhoto(maintenance.getMaintenance_id());
        db.delete(TABLE_MAINTENANCES,MAINTENANCE_ID + " LIKE ?",new String[]{maintenance.getMaintenance_id()});
        db.close();
    }
    public void deleteMaintenance(String project_id){
        db = helper.getWritableDatabase();
        db.delete(TABLE_MAINTENANCES,PROJECT_ID + " LIKE ?",new String[]{project_id});
        db.close();
    }
}
