package niamoro.annonces.databases.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class RechercheHelper extends SQLiteOpenHelper {

    private static final String ID = "id";
    private static final String PAYS = "pays";
    private static final String VILLE = "ville";
    private static final String TYPE_BIEN = "type_bien";
    private static final String TYPE_OPERATION = "type_operation";
    private static final String DATE = "date";
    private static final String STATUS = "status";


    private static final String TABLE_RECHERCHES = "recherches";

    private static final String create_table = "CREATE TABLE " + TABLE_RECHERCHES + " ("
            + ID + " TEXT, "
            + PAYS + " TEXT NOT NULL, "
            + VILLE + " TEXT NOT NULL, "
            + TYPE_BIEN + " TEXT NOT NULL, "
            + TYPE_OPERATION + " TEXT, "
            + STATUS + " INTEGER, "
            + DATE + " INTEGER NOT NULL, "
            +" PRIMARY KEY (" + VILLE + ", " + TYPE_BIEN + ", " + TYPE_OPERATION + "));";

    public RechercheHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
