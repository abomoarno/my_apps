package dev.casalov.projetetincel.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SurveillerConformiteHelper extends SQLiteOpenHelper {

    private static final String ELEMENT_ID = "element_id";
    private static final String PROJECT_ID = "project_id";
    private static final String ELEMENT_VALEUR = "element_valeur";
    private static final String ELEMENT_NOM = "element_nom";
    private static final String ELEMENT_TYPE = "element_type";


    private static final String TABLE_SURVEILLER = "surveiller_conformite";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_SURVEILLER + " ("
            + ELEMENT_ID + " TEXT NOT NULL, "
            + ELEMENT_VALEUR + " INTEGER NOT NULL, "
            + PROJECT_ID + " TEXT NOT NULL, "
            + ELEMENT_NOM + " TEXT NOT NULL, "
            + ELEMENT_TYPE + " TEXT NOT NULL, "
            + "PRIMARY KEY (" + ELEMENT_ID + ", " + PROJECT_ID + "));";

    public SurveillerConformiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
