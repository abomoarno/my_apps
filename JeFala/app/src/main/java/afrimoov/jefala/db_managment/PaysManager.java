package afrimoov.jefala.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import afrimoov.jefala.classes.Pays;

public class PaysManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "lcdy_pays";

    private static final String TABLE_PAYS = "pays";

    private static final String iso ="iso";
    private static final String nom = "nom";
    private static final String devise = "devise";
    private static final String logo = "logo";

    private SQLiteDatabase bdd;
    private PaysHelper myHelper;
    private Context context;

    public PaysManager(Context context) {
        myHelper = new PaysHelper(context,NAME_DB,null,VERSION_BDD);
        this.context = context;
    }

    public void insertPays(Pays pays){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(iso,pays.getIso());
        values.put(nom,pays.getNom());
        values.put(devise,pays.getDevise());
        values.put(logo,pays.getLogo());
        bdd.insert(TABLE_PAYS,null,values);
        bdd.close();
    }
    public Pays getPays(String pays_iso){
        bdd = myHelper.getWritableDatabase();
        Pays pays = null;
        Cursor cursor = bdd.query(TABLE_PAYS,new String[]{"*"},iso + " LIKE ?", new String[]{pays_iso}, null,
                null, null, null);
        if (cursor.moveToNext()){
            pays = new Pays(cursor.getString(0), cursor.getString(1));
            pays.setDevise(cursor.getString(2));
            pays.setLogo(cursor.getString(3));
        }
        bdd.close();
        cursor.close();
        return pays;
    }
}
