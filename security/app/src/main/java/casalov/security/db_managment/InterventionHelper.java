package casalov.security.db_managment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InterventionHelper extends SQLiteOpenHelper {

    private static final String TABLE_INTERVENTIONS = "interventions";
    private static String id = "id";
    private static String motif = "motif";
    private static String statut = "statut";
    private static String date = "date";
    private static String lieu = "lieu";
    private static String prix = "prix";
    private static final String create_table_interventions = "CREATE TABLE " + TABLE_INTERVENTIONS + " ("
            + id + " INTEGER PRIMARY KEY, "
            + motif + " TEXT NOT NULL, "
            + statut + " TEXT NOT NULL, "
            + date + " TEXT NOT NULL, "
            + lieu + " TEXT NOT NULL,"
            + prix + " TEXT NOT NULL);";
    InterventionHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_table_interventions);
        }
        catch (Exception ignored){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}