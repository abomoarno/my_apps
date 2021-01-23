package dev.casalov.projetetincel.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaintenanceHelper extends SQLiteOpenHelper {

    private static final String MAINTENANCE_ID = "maintenance_id";
    private static final String INITIAL_ID = "initial_id";
    private static final String titre = "titre";
    private static final String PROJECT_ID = "project_id";


    private static String TABLE_MAINTENANCES = "maintenances";

    private static String CREATE_BDD =  "CREATE TABLE " + TABLE_MAINTENANCES + " ("
            + MAINTENANCE_ID + " TEXT PRIMARY KEY, "
            + titre + " TEXT NOT NULL, "
            + PROJECT_ID + " TEXT NOT NULL, "
            + INITIAL_ID + " TEXT NOT NULL);";

    public MaintenanceHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_BDD);
        }
        catch (Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
