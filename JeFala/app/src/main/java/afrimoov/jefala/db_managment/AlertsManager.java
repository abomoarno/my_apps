package afrimoov.jefala.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import afrimoov.jefala.classes.Alerte;

public class AlertsManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "lcdy_alertes";

    private static final String TABLE_ALERTS = "alertes";
    private static final String id ="id";
    private static final String type = "type";
    private static final String date = "date";
    private static final String pays = "pays";
    private static final String ville = "ville";
    private static final String superficieMin = "superficie_min";
    private static final String superficieMax = "superficie_max";
    private static final String prix_min = "prix_min";
    private static final String prix_max = "prix_max";
    private static final String operation = "operation";
    private static final String quartier = "quartier";
    private static final String status = "status";

    private SQLiteDatabase bdd;
    private AlertHelper myHelper;
    private Context context;

    public AlertsManager(Context context) {
        myHelper = new AlertHelper(context,NAME_DB,null,VERSION_BDD);
        this.context = context;
    }

    public void insertAlerte(Alerte alerte){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(type,alerte.getType());
        values.put(date,alerte.getDate());
        values.put(pays,alerte.getPays());
        values.put(ville,alerte.getVille());
        values.put(superficieMin,alerte.getSuperficieMin());
        values.put(superficieMax,alerte.getSuperficieMax());
        values.put(prix_max,alerte.getPrix_max());
        values.put(prix_min,alerte.getPrix_min());
        values.put(quartier,alerte.getQuartier());
        values.put(operation,alerte.getOperation());
        values.put(status,alerte.getStatus());

        bdd.insert(TABLE_ALERTS,null,values);
        bdd.close();
    }

    public Alerte getAlerte(String alerte_id){
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_ALERTS, new String[]{"*"},id + " LIKE " + alerte_id,null,null,
                null, null);
        if (cursor.moveToNext()){
            Alerte alerte = new Alerte();
            alerte.setId(cursor.getString(0));
            alerte.setType(cursor.getString(1));
            alerte.setDate(cursor.getString(2));
            alerte.setPays(cursor.getString(3));
            alerte.setVille(cursor.getString(4));
            alerte.setSuperficieMin(cursor.getString(5));
            alerte.setSuperficieMax(cursor.getString(6));
            alerte.setPrix_min(cursor.getString(7));
            alerte.setPrix_max(cursor.getString(8));
            alerte.setStatus(cursor.getInt(9));
            alerte.setOperation(cursor.getString(10));
            alerte.setQuartier(cursor.getString(11));
            return alerte;
        }
        cursor.close();
        bdd.close();

        return null;
    }
    public ArrayList<Alerte> getAll(){
        ArrayList<Alerte> alertes = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Alerte alerte;
        Cursor cursor = bdd.query(TABLE_ALERTS, new String[]{"*"},null,null,null,
                null, null);
        while (cursor.moveToNext()){
            alerte = new Alerte();
            alerte.setId(cursor.getString(0));
            alerte.setType(cursor.getString(1));
            alerte.setDate(cursor.getString(2));
            alerte.setPays(cursor.getString(3));
            alerte.setVille(cursor.getString(4));
            alerte.setSuperficieMin(cursor.getString(5));
            alerte.setSuperficieMax(cursor.getString(6));
            alerte.setPrix_min(cursor.getString(7));
            alerte.setPrix_max(cursor.getString(8));
            alertes.add(alerte);
        }
        cursor.close();
        bdd.close();

        return alertes;
    }

    public void deleteAlerte(String alerte_id){
        bdd = myHelper.getWritableDatabase();
        bdd.delete(TABLE_ALERTS,id + " = " + alerte_id,null);
        bdd.close();
    }

    public int getTotal(){

        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_ALERTS,new String[]{"COUNT(" + id + ")"},null,null,
                null,null,null);

        int count = 0;

        if (cursor != null && cursor.moveToNext())
        {
            count = cursor.getInt(0);
            cursor.close();
        }

        bdd.close();

        return count;
    }
    
}
