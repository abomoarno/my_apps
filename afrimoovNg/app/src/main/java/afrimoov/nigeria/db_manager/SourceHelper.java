package afrimoov.nigeria.db_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SourceHelper extends SQLiteOpenHelper {

    private static final String TABLE_SOURCE = "sources";

    private static final String id ="source_id";
    private static final String title = "title";
    private static final String image = "image";
    private static final String pays = "pays";

    private static final String create_table_sources = "CREATE TABLE " + TABLE_SOURCE + " ("
            + id + " TEXT PRIMARY KEY, "
            + title + " TEXT NOT NULL, "
            + image + " TEXT NOT NULL, "
            + pays + " TEXT NOT NULL);";

    SourceHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_table_sources);
        }
        catch (Exception ignored){
            ignored.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}