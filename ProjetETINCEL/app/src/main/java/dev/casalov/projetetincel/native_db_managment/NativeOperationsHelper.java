package dev.casalov.projetetincel.native_db_managment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NativeOperationsHelper extends SQLiteOpenHelper {

    private static final String OPERATION_ID = "operation_id";
    private static final String COMPARTIMENT_ID = "compartiment_id";
    private static final String OPERATION_NOM = "operation_nom";


    private static final String TABLE_OPERATIONS = "native_operations";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_OPERATIONS + " ("
            + OPERATION_ID + " TEXT PRIMARY KEY, "
            + COMPARTIMENT_ID + " TEXT NOT NULL, "
            + OPERATION_NOM + " TEXT NOT NULL);";

    public NativeOperationsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_BDD);
        }
        catch (Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
