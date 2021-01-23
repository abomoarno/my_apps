package dev.casalov.projetetincel.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PrestationsHelper extends SQLiteOpenHelper {

    private static final String PRESTATION_ID = "prestation_id";
    private static final String titre = "titre";
    private static final String DEVIS_ID = "devis_id";
    private static final String QUANTITE = "quantite";
    private static final String PRIX = "prix";
    private static String TABLE_PRESTATIONS = "prestations";

    private static String CREATE_BDD =  "CREATE TABLE " + TABLE_PRESTATIONS + " ("
            + PRESTATION_ID + " TEXT PRIMARY KEY, "
            + titre + " TEXT NOT NULL, "
            + DEVIS_ID + " TEXT NOT NULL, "
            + QUANTITE + " INTEGER NOT NULL, "
            + PRIX + " INTEGER NOT NULL);";

    public PrestationsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
