package dev.casalov.projetetincel.native_db_managment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NativeCompartimentsHelper extends SQLiteOpenHelper {

    private static final String COMPARTIMENT_ID = "compartiment_id";
    private static final String COMPARTIMENT_NOM = "compartiment_nom";
    private static final String GAMME_ID = "gamme_id";

    private static final String TABLE_NATIVE_COMPARTIMENT = "native_compartiment";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_NATIVE_COMPARTIMENT + " ("
            + COMPARTIMENT_ID + " TEXT PRIMARY KEY, "
            + COMPARTIMENT_NOM + " TEXT NOT NULL, "
            + GAMME_ID + " TEXT NOT NULL); ";

    public NativeCompartimentsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
