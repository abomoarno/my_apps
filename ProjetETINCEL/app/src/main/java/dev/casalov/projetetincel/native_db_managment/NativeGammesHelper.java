package dev.casalov.projetetincel.native_db_managment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NativeGammesHelper extends SQLiteOpenHelper {

    private static final String GAMME_ID = "gamme_id";
    private static final String GAMME_NOM = "gamme_nom";

    private static final String TABLE_GAMMES = "native_gammes";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_GAMMES + " ("
            + GAMME_ID + " TEXT PRIMARY KEY, "
            + GAMME_NOM + " TEXT NOT NULL);";

    public NativeGammesHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
