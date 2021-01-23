package fallapro.landcrowdy.dbManagment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PaysHelper extends SQLiteOpenHelper {

    private static final String TABLE_PAYS = "pays";

    private static final String iso ="iso";
    private static final String nom = "nom";
    private static final String devise = "devise";
    private static final String logo = "logo";


    private static final String create_table_pays = "CREATE TABLE " + TABLE_PAYS + " ("
            + iso + " TEXT PRIMARY KEY, "
            + nom + " TEXT NOT NULL, "
            + devise + " TEXT NOT NULL, "
            + logo + " TEXT NOT NULL);";

    public PaysHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_table_pays);
        }
        catch (Exception ignored){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
