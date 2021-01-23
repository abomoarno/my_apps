package niamoro.comedy.db_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VideoHelper extends SQLiteOpenHelper {

    private static final String TABLE_VIDEO = "videos";

    private static final String id ="video_id";
    private static final String title = "title";
    private static final String description = "description";
    private static final String comedien = "comedien";
    private static final String link = "link";
    private static final String image = "image";
    private static final String views = "views";
    private static final String month_views = "month_views";
    private static final String day_views = "day_views";
    private static final String week_views = "week_views";
    private static final String duration = "duration";
    private static final String date = "date";

    private static final String create_table_replays = "CREATE TABLE " + TABLE_VIDEO + " ("
            + id + " TEXT PRIMARY KEY, "
            + title + " TEXT NOT NULL, "
            + description + " TEXT NOT NULL, "
            + comedien + " TEXT NOT NULL, "
            + link + " TEXT NOT NULL, "
            + image + " TEXT NOT NULL, "
            + views + " INTEGER NOT NULL, "
            + duration + " TEXT NOT NULL,"
            + date + " TEXT NOT NULL,"
            + day_views + " INTEGER, "
            + week_views + " INTEGER, "
            + month_views + " INTEGER);";

    VideoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i1 == 2){
            db.execSQL(
                    "ALTER TABLE " + TABLE_VIDEO + " ADD COLUMN "
                    + day_views + " INTEGER;"
            );
            db.execSQL(
                    "ALTER TABLE " + TABLE_VIDEO + " ADD COLUMN "
                            + week_views + " INTEGER;"
            );
            db.execSQL(
                    "ALTER TABLE " + TABLE_VIDEO + " ADD COLUMN "
                            + month_views + " INTEGER;"
            );
        }
    }
}