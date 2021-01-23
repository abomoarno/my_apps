package fallapro.landcrowdy.dbManagment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlertHelper extends SQLiteOpenHelper {

    private static final String TABLE_ALERTS = "alerts";

    private static final String id ="id";
    private static final String type = "type";
    private static final String date = "date";
    private static final String pays = "pays";
    private static final String ville = "ville";
    private static final String superficieMin = "superficie_min";
    private static final String superficieMax = "superficie_max";
    private static final String pix_min = "prix_min";
    private static final String prix_max = "prix_max";
    private static final String operation = "operation";
    private static final String quartier = "quartier";
    private static final String status = "status";


    private static final String create_table_pronostics = "CREATE TABLE " + TABLE_ALERTS + " ("
            + id + " INTEGER PRIMARY KEY, "
            + type + " TEXT NOT NULL, "
            + date + " TEXT NOT NULL, "
            + pays + " TEXT NOT NULL, "
            + ville + " TEXT NOT NULL, "
            + superficieMin + " TEXT NOT NULL, "
            + superficieMax + " TEXT NOT NULL, "
            + pix_min + " TEXT NOT NULL, "
            + prix_max + " TEXT NOT NULL,"
            + status + " INTEGER NOT NULL,"
            + operation + " TEXT NOT NULL,"
            + quartier + " TEXT NOT NULL);";

    public AlertHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
