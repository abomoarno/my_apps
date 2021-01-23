package dev.casalov.projetetincel.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhotoHelper extends SQLiteOpenHelper {

    private static final String LIEN = "lien";
    private static final String titre = "titre";
    private static final String GAMME = "gamme";
    private static final String STATUS = "status";

    private static String TABLE_PHOTOS = "photos";

    private static String CREATE_BDD =  "CREATE TABLE " + TABLE_PHOTOS + " ("
            + LIEN + " TEXT PRIMARY KEY, "
            + titre + " TEXT NOT NULL, "
            + GAMME + " TEXT NOT NULL, "
            + STATUS + " TEXT NOT NULL);";

    public PhotoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
