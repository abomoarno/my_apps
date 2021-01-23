package casalov.security.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import casalov.security.classes.Entreprise;
import casalov.security.classes.Intervention;
import casalov.security.utils.Utils;

public class EntreprisesManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "montpellier_security_entrepsie";
    private static final String TABLE_ENTREPRISES = "entreprises";

    private static String id = "id";
    private static String nom = "nom";
    private static String phone = "phone";


    private SQLiteDatabase bdd;
    private EntrepriseHelper myHelper;

    public EntreprisesManager(Context context) {
        myHelper = new EntrepriseHelper(context,NAME_DB,null,VERSION_BDD);
    }

    public List<Entreprise> getAllEntreprises(){
        List<Entreprise> entreprises = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_ENTREPRISES,new String[]{"*"}, null ,null,
                null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                Entreprise entreprise = new Entreprise(cursor.getString(2),cursor.getString(0));
                entreprise.setPhone(cursor.getString(1));
                entreprises.add(entreprise);
            }
            cursor.close();
        }
        bdd.close();
        return entreprises;
    }

    public void insertEntreprise(Entreprise entreprise){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(id,entreprise.getId());
        values.put(nom,entreprise.getNom());
        values.put(phone,entreprise.getPhone());
        bdd.insert(TABLE_ENTREPRISES,null,values);
        bdd.close();
    }

    public int getLastId(){
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_ENTREPRISES,new String[]{id},null,null,null,null,
                id + " DESC");
        if (cursor.moveToNext()){
            return cursor.getInt(0);
        }
        cursor.close();
        bdd.close();
        return  0;
    }
}
