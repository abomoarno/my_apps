package arnoarobase.coiffuresafricaines.db_mangers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhotosHelper extends SQLiteOpenHelper {

    private static String id = "id";
    private static String titre = "titre";
    private static String url = "url";
    private static String genre = "genre";
    private static String liked = "liked";
    private static String liks = "liks";
    private static String date = "date";
    private static String description = "description";

    private static String TABLE_PHOTOS = "table_photos";

    private static String CREATE_TABLE = " CREATE TABLE " + TABLE_PHOTOS +" ("
            + id + " TEXT PRIMARY KEY, "
            + titre + " TEXT, "
            + url + " TEXT, "
            + genre + " TEXT, "
            + date + " TEXT, "
            + liked + " INTEGER, "
            + description + " TEXT, "
            + liks + " INTEGER);";


    PhotosHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
