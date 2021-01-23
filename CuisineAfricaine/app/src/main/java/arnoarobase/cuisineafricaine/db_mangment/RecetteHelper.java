package arnoarobase.cuisineafricaine.db_mangment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecetteHelper extends SQLiteOpenHelper {

    static final String ID = "id";
    static final String TITRE = "titre";
    static final String DESCRIPTION = "description";
    static final String IMAGE = "image";
    static final String VIDEO = "video";
    static final String NOTE = "note";
    static final String TEMPS = "temps";
    static final String PRIX = "prix";
    static final String DIFFICULTE = "difficulte";
    static final String CATEGORY = "category";
    static final String DATE = "date";
    static final String NUM_PERSONNES = "num_personnes";
    public static final String INGREDIENTS = "ingredients";
    public static final String PREPARATION = "preparation";
    public static final String FAVORIS = "favoris";
    public static final String MA_LISTE = "ma_liste";
    public static final String PAYS = "pays";
    public static final String VIEWS = "views";
    public static final String FAVORIES = "favories";
    public static final String ADD_LIST = "add_list";

    public static final String TABLE_RECETTES = "table_recettes";
    static final String DB = "recettes";
    static final int VERSION = 1;

    private String exec = "CREATE TABLE " + TABLE_RECETTES + " ("
            + ID + " TEXT PRIMARY KEY, "
            + TITRE + " TEXT NOT NULL, "
            + DESCRIPTION + " TEXT, "
            + IMAGE + " TEXT NOT NULL, "
            + VIDEO + " TEXT NOT NULL, "
            + NOTE + " INTEGER NOT NULL, "
            + TEMPS + " TEXT NOT NULL, "
            + PRIX + " TEXT NOT NULL, "
            + DIFFICULTE + " TEXT NOT NULL, "
            + CATEGORY + " TEXT NOT NULL, "
            + DATE + " TEXT NOT NULL, "
            + NUM_PERSONNES + " INTEGER NOT NULL, "
            + INGREDIENTS + " TEXT NOT NULL, "
            + PREPARATION + " TEXT NOT NULL, "
            + FAVORIES + " INTEGER NOT NULL, "
            + MA_LISTE + " INTEGER NOT NULL, "
            + PAYS + " TEXT NOT NULL, "
            + VIEWS + " INTEGER NOT NULL, "
            + FAVORIS + " INTEGER NOT NULL, "
            + ADD_LIST + " INTEGER NOT NULL); ";

    public RecetteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
