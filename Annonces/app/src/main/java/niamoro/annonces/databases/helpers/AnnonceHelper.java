package niamoro.annonces.databases.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class AnnonceHelper extends SQLiteOpenHelper {

    private static final String ID = "id";
    private static final String TITRE = "titre";
    private static final String PAYS = "pays";
    private static final String VILLE = "ville";
    private static final String TYPE_BIEN = "type_bien";
    private static final String TYPE_OPERATION = "type_operation";
    private static final String LIEN = "lien";
    private static final String IMAGE = "image";
    private static final String DATE = "date";
    private static final String PRIX = "prix";
    private static final String LIKED = "liked";

    private static final String TABLE_ANNONCES = "annonces";

    private static final String create_table = "CREATE TABLE " + TABLE_ANNONCES + " ("
            + ID + " TEXT PRIMARY KEY, "
            + TITRE + " TEXT NOT NULL, "
            + PAYS + " TEXT NOT NULL, "
            + VILLE + " TEXT NOT NULL, "
            + TYPE_BIEN + " TEXT NOT NULL, "
            + TYPE_OPERATION + " TEXT, "
            + LIEN + " TEXT NOT NULL, "
            + IMAGE + " TEXT NOT NULL, "
            + DATE + " TEXT NOT NULL,"
            + PRIX + " TEXT NOT NULL,"
            + LIKED + " INTEGER NOT NULL);";

    public AnnonceHelper(@Nullable Context context, @Nullable String name,@Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
