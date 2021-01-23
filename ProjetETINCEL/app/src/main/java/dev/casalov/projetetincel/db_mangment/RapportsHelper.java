package dev.casalov.projetetincel.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RapportsHelper extends SQLiteOpenHelper {

    private static final String RAPPORT_ID = "rapport_id";
    private static final String titre = "titre";
    private static final String CLIENT_ID = "client_id";
    private static final String TYPE = "type";
    private static final String DATE_D = "date_d";
    private static final String DATE_F = "date_f";
    private static final String REDACTEUR = "redacteur";
    private static final String VERIFICATEUR = "verificateur";
    private static final String APPROBATEUR = "approbateur";
    private static final String CONTACT_SITE = "contact_site";
    private static final String CHARGE_AFFAIRES = "charge_affaires";
    private static final String CHARGE_TRAVAUX = "charge_travaux";
    private static final String TECHNICIENS = "techniciens";

    private static String TABLE_RAPPORTS = "rapports";

    private static String CREATE_BDD =  "CREATE TABLE " + TABLE_RAPPORTS + " ("
            + RAPPORT_ID + " TEXT PRIMARY KEY, "
            + titre + " TEXT NOT NULL, "
            + CLIENT_ID + " TEXT NOT NULL, "
            + TYPE + " TEXT NOT NULL, "
            + DATE_D + " TEXT NOT NULL, "
            + DATE_F + " TEXT NOT NULL, "
            + REDACTEUR + " TEXT NOT NULL, "
            + VERIFICATEUR + " TEXT NOT NULL, "
            + APPROBATEUR + " TEXT NOT NULL, "
            + CONTACT_SITE + " TEXT NOT NULL, "
            + CHARGE_AFFAIRES + " TEXT NOT NULL, "
            + CHARGE_TRAVAUX + " TEXT NOT NULL,"
            + TECHNICIENS + " TEXT NOT NULL);";

    public RapportsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
