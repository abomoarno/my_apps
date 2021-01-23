package afrimoov.nigeria.db_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TvHelper extends SQLiteOpenHelper {

    private static final String TABLE_TVS = "tvs";

    private static final String id ="tv_id";
    private static final String nom = "nom";
    private static final String description = "description";
    private static final String pays = "pays";
    private static final String lien = "lien";
    private static final String plateforme = "plateforme";
    private static final String image = "image";
    private static final String langue = "langue";
    private static final String categorie = "categorie";
    private static final String vues = "vues";



    private static final String create_table_tvs = "CREATE TABLE " + TABLE_TVS + " ("
            + id + " TEXT PRIMARY KEY, "
            + nom + " TEXT NOT NULL, "
            + description + " TEXT NOT NULL, "
            + pays + " TEXT NOT NULL, "
            + lien + " TEXT NOT NULL, "
            + plateforme + " TEXT NOT NULL, "
            + image + " TEXT NOT NULL, "
            + langue + " TEXT NOT NULL, "
            + categorie + " TEXT NOT NULL,"
            + vues + " INTEGER NOT NULL);";

    TvHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_table_tvs);
        }
        catch (Exception ignored){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
