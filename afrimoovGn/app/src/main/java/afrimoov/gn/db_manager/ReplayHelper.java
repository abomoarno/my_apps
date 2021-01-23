package afrimoov.gn.db_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReplayHelper extends SQLiteOpenHelper {

    private static final String TABLE_REPLAY = "replays";

    private static final String id ="replay_id";
    private static final String title = "title";
    private static final String description = "description";
    private static final String chaine = "chaine";
    private static final String link = "link";
    private static final String plateforme = "plateforme";
    private static final String image = "image";
    private static final String views = "views";
    private static final String duration = "duration";
    private static final String date = "date";

    private static final String create_table_replays = "CREATE TABLE " + TABLE_REPLAY + " ("
            + id + " TEXT PRIMARY KEY, "
            + title + " TEXT NOT NULL, "
            + description + " TEXT NOT NULL, "
            + chaine + " TEXT NOT NULL, "
            + link + " TEXT NOT NULL, "
            + plateforme + " TEXT, "
            + image + " TEXT NOT NULL, "
            + views + " INTEGER NOT NULL,"
            + duration + " TEXT NOT NULL,"
            + date + " TEXT NOT NULL);";

    public ReplayHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_table_replays);
        }
        catch (Exception ignored){
            ignored.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}