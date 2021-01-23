package afrimoov.jefala.db_managment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TerrainHelper extends SQLiteOpenHelper {

    private static final String TABLE_TERRAINS = "terrains";

    private static final String id ="id";
    private static final String type = "type";
    private static final String titre = "titre";
    private static final String description = "description";
    private static final String lien = "lien";
    private static final String image = "image";
    private static final String date = "date";
    private static final String pays = "pays";
    private static final String ville = "ville";
    private static final String superficie = "superficie";
    private static final String quartier = "quartier";
    private static final String prix = "prix";
    private static final String liked = "liked";
    private static final String affichages = "affichages";
    private static final String status = "status";

    private static final String create_table_pronostics = "CREATE TABLE " + TABLE_TERRAINS + " ("
            + id + " INTEGER PRIMARY KEY, "
            + type + " TEXT, "
            + titre + " TEXT NOT NULL, "
            + date + " TEXT NOT NULL, "
            + pays + " TEXT NOT NULL, "
            + ville + " TEXT NOT NULL, "
            + superficie + " INTEGER, "
            + description + " TEXT, "
            + lien + " TEXT NOT NULL, "
            + image + " TEXT NOT NULL, "
            + liked + " INTEGER NOT NULL, "
            + affichages + " INTEGER, "
            + prix + " INTEGER, "
            + status + " TEXT, "
            + quartier + " TEXT NOT NULL);";

    public TerrainHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_table_pronostics);
        }
        catch (Exception ignored){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
