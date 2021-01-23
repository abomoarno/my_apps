package fallapro.landcrowdy.dbManagment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VilleHelper extends SQLiteOpenHelper {

    private static final String TABLE_VILLES = "villes";

    private static final String pays ="pays";
    private static final String nom = "nom";
    private static final String id = "id";



    private static final String create_tablevilles = "CREATE TABLE " + TABLE_VILLES + " ("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + pays + " TEXT NOT NULL, "
            + nom + " TEXT NOT NULL);";

    public VilleHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_tablevilles);
        }
        catch (Exception ignored){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}