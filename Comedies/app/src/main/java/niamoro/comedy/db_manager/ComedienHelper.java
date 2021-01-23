package niamoro.comedy.db_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ComedienHelper extends SQLiteOpenHelper {

    private static final String TABLE_COMEDIEN = "comediens";

    private static final String id ="comedien_id";
    private static final String nom = "nom";
    private static final String image = "image";
    private static final String pays = "pays";

    private static final String followed = "followed"; //AJOUTER A LA VERSION 2 DE L'APPLICATION

    private static final String create_table_comediens = "CREATE TABLE " + TABLE_COMEDIEN + " ("
        + id + " TEXT PRIMARY KEY, "
        + nom + " TEXT NOT NULL, "
        + image + " TEXT NOT NULL, "
        + pays + " TEXT NOT NULL, "
        + followed + " INTEGER);";

    ComedienHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_table_comediens);
        }
        catch (Exception ignored){
            ignored.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i == 1){
            sqLiteDatabase.execSQL(
                "ALTER TABLE " + TABLE_COMEDIEN +
                " ADD COLUMN " + followed +
                " INTEGER"
            );
        }
    }
}