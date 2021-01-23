package dev.casalov.projetetincel.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ElementsHelper extends SQLiteOpenHelper {

    private static final String ELEMENT_ID = "element_id";
    private static final String PROJECT_ID = "project_id";
    private static final String GAMME_ID = "gamme_id";
    private static final String ELEMENT_VALEUR = "element_valeur";
    private static final String ELEMENT_NOM = "element_nom";


    private static final String TABLE_NATIVE_ELEMENTS = "elements";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_NATIVE_ELEMENTS + " ("
            + ELEMENT_ID + " TEXT NOT NULL, "
            + ELEMENT_VALEUR + " INTEGER NOT NULL, "
            + PROJECT_ID + " TEXT NOT NULL, "
            + GAMME_ID + " TEXT NOT NULL, "
            + ELEMENT_NOM + " TEXT NOT NULL, "
            + "PRIMARY KEY (" + ELEMENT_ID + ", " + PROJECT_ID + "));";

    public ElementsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
