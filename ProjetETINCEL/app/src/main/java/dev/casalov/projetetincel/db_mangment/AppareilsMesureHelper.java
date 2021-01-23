package dev.casalov.projetetincel.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppareilsMesureHelper extends SQLiteOpenHelper {

    private static final String ELEMENT_ID = "element_id";
    private static final String PROJECT_ID = "project_id";
    private static final String ELEMENT_SERIE = "element_serie";
    private static final String ELEMENT_NOM = "element_nom";
    private static final String ELEMENT_VALIDITE = "element_validite";


    private static final String TABLE_APPAREILS = "appareils";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_APPAREILS + " ("
            + ELEMENT_ID + " TEXT NOT NULL, "
            + ELEMENT_SERIE + " INTEGER NOT NULL, "
            + PROJECT_ID + " TEXT NOT NULL, "
            + ELEMENT_NOM + " TEXT NOT NULL, "
            + ELEMENT_VALIDITE + " TEXT NOT NULL, "
            + "PRIMARY KEY (" + ELEMENT_ID + ", " + PROJECT_ID + "));";

    public AppareilsMesureHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
