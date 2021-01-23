package afrimoov.jefala.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class VillesManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "lcdy_villes";
    private static final String TABLE_VILLES = "villes";

    private static final String pays ="pays";
    private static final String nom = "nom";
    private static final String id = "id";

    private SQLiteDatabase bdd;
    private PaysHelper myHelper;
    private Context context;

    public VillesManager(Context context) {
        myHelper = new PaysHelper(context,NAME_DB,null,VERSION_BDD);
        this.context = context;
    }

    public void insertVille(String nom_v, String pays_v){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nom,nom_v);
        values.put(pays,pays_v);

        bdd.insert(TABLE_VILLES,null,values);
        bdd.close();
    }
    public ArrayList<String> getPays(String pays_iso){
        bdd = myHelper.getWritableDatabase();
        ArrayList<String> pays = new ArrayList<>();
        Cursor cursor = bdd.query(TABLE_VILLES,new String[]{nom},pays + " LIKE " + pays_iso, null, null,
                null, null, null);
        while (cursor.moveToNext()){
            pays.add(cursor.getString(0));
        }
        bdd.close();
        cursor.close();
        return pays;
    }
}