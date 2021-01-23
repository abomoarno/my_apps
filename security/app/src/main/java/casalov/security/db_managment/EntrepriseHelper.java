package casalov.security.db_managment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EntrepriseHelper extends SQLiteOpenHelper {

    private static final String TABLE_ENTREPRISES = "entreprises";
    private static String id = "id";
    private static String phone = "lieu";
    private static String nom = "prix";
    private static final String create_table_entreprises = "CREATE TABLE " + TABLE_ENTREPRISES + " ("
            + id + " TEXT PRIMARY KEY, "
            + phone + " TEXT NOT NULL,"
            + nom + " TEXT NOT NULL);";
    EntrepriseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_table_entreprises);
        }
        catch (Exception ignored){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}