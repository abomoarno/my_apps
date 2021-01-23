package big.win.db_managment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db_Helper extends SQLiteOpenHelper {

    private static final String TABLE_PRONOSTICS = "pronostics";

    private static final String id ="id";
    private static final String pays = "pays";
    private static final String hour = "hour";
    private static final String cote = "cote";
    private static final String pronostic = "pronostic";
    private static final String result = "result";
    private static final String category = "category";
    private static final String name_teamA = "name_A";
    private static final String scoreA = "scoreA";
    private static final String name_teamB = "name_B";
    private static final String scoreB = "scoreB";
    private static final String notify = "notified";


    private static final String create_table_pronostics = "CREATE TABLE " + TABLE_PRONOSTICS + " ("
            + id + " INTEGER PRIMARY KEY, "
            + pays + " TEXT NOT NULL, "
            + hour + " TEXT NOT NULL, "
            + cote + " TEXT NOT NULL, "
            + pronostic + " TEXT NOT NULL, "
            + category + " TEXT NOT NULL, "
            + result + " TEXT NOT NULL, "
            + name_teamA + " TEXT NOT NULL, "
            + scoreA + " TEXT NOT NULL, "
            + name_teamB + " TEXT NOT NULL, "
            + scoreB + " TEXT NOT NULL,"
            + notify + " INTEGER NOT NULL);";

    public Db_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
