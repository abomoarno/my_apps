package dev.casalov.projetetincel.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OperationsHelper extends SQLiteOpenHelper {

    private static final String OPERATION_ID = "operation_id";
    private static final String OPERATION_STATUT = "operation_statut";
    private static final String RAPPORT_ID = "rapport_id";
    private static final String GAMME_ID = "GAMME_ID";
    private static final String NOM = "nom";
    private static final String OBSERVATION = "OBSERVATION";
    private static final String COMPARTIMENT_ID = "COMPARTIMENT_ID";

    private static final String TABLE_OPERATIONS = "operations";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_OPERATIONS + " ("
            + OPERATION_ID + " TEXT, "
            + NOM + " TEXT, "
            + RAPPORT_ID + " TEXT, "
            + GAMME_ID + " TEXT NOT NULL, "
            + COMPARTIMENT_ID + " TEXT NOT NULL, "
            + OPERATION_STATUT + " INTEGER, "
            + OBSERVATION + " TEXT NOT NULL,"
            + "PRIMARY KEY (" + OPERATION_ID + ", " + RAPPORT_ID + ", " + COMPARTIMENT_ID + ", " + GAMME_ID +"));";

    public OperationsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
