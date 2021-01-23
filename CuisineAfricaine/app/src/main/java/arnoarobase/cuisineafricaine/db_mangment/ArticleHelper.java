package arnoarobase.cuisineafricaine.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArticleHelper extends SQLiteOpenHelper {

    static final String ID = "id";
    static final String TITRE = "titre";
    static final String DESCRIPTION = "description";
    static final String IMAGE = "image";
    static final String DATE = "date";
    static final String URL = "url";
    static final String VIEWS = "views";

    static final String TABLE_ARTICLES = "table_articles";
    static final String DB = "articles";
    static final int VERSION = 1;

    private String exec = "CREATE TABLE " + TABLE_ARTICLES + " ("
            + ID + " TEXT PRIMARY KEY, "
            + TITRE + " TEXT NOT NULL, "
            + DESCRIPTION + " TEXT, "
            + IMAGE + " TEXT NOT NULL, "
            + DATE + " TEXT NOT NULL, "
            + URL + " TEXT NOT NULL, "
            + VIEWS + " INTEGER NOT NULL); ";

    ArticleHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(exec);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
