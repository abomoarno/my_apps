package dev.casalov.projetetincel.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DevisHelper extends SQLiteOpenHelper {

    private static final String DEVIS_ID = "devis_id";
    private static final String titre = "titre";
    private static final String CLIENT_ID = "client_id";
    private static final String DATE_REDACTION = "date_redaction";
    private static final String DATE_REALISATION = "date_realisation";
    private static final String REDACTEUR = "redacteur";
    private static final String CHARGES_CLIENT = "charges_client";
    private static final String CHARGES_EINCEL = "charges_etincel";
    private static final String VILLE = "ville";

    private static String TABLE_DEVIS = "devis";

    private static String CREATE_BDD =  "CREATE TABLE " + TABLE_DEVIS + " ("
            + DEVIS_ID + " TEXT PRIMARY KEY, "
            + titre + " TEXT NOT NULL, "
            + CLIENT_ID + " TEXT NOT NULL, "
            + DATE_REDACTION + " TEXT NOT NULL, "
            + DATE_REALISATION + " TEXT NOT NULL, "
            + REDACTEUR + " TEXT NOT NULL, "
            + CHARGES_CLIENT + " TEXT NOT NULL, "
            + CHARGES_EINCEL + " TEXT NOT NULL, "
            + VILLE + " TEXT NOT NULL);";

    public DevisHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
