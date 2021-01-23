package niamoro.annonces.databases.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class PaysHelper extends SQLiteOpenHelper {

    private static final String ID = "code";
    private static final String TITRE = "nom";


    private static final String TABLE_PAYS = "pays";

    private static final String create_table = "CREATE TABLE " + TABLE_PAYS + " ("
            + ID + " TEXT PRIMARY KEY, "
            + TITRE + " TEXT NOT NULL);";

    public PaysHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_table);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
