package dev.casalov.projetetincel.native_db_managment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NativeElementsHelper extends SQLiteOpenHelper {

    private static final String ELEMENT_ID = "element_id";
    private static final String ELEMENT_NOM = "element_nom";
    private static final String GAMME_ID = "gamme_id";

    private static final String TABLE_NATIVE_ELEMENTS = "native_elements";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_NATIVE_ELEMENTS + " ("
            + ELEMENT_ID + " TEXT PRIMARY KEY, "
            + ELEMENT_NOM + " TEXT NOT NULL, "
            + GAMME_ID + " TEXT NOT NULL); ";

    public NativeElementsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_BDD);
        }
        catch (Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
