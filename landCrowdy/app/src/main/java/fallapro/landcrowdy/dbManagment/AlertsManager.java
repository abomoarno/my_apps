package fallapro.landcrowdy.dbManagment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fallapro.landcrowdy.classes.Alert;

public class AlertsManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "lcdy_alerts";

    private static final String TABLE_ALERTS = "alerts";
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

    public void insertAlert(Alert alert){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(type,alert.getType());
        values.put(date,alert.getDate());
        values.put(pays,alert.getPays());
        values.put(ville,alert.getVille());
        values.put(superficieMin,alert.getSuperficieMin());
        values.put(superficieMax,alert.getSuperficieMax());
        values.put(prix_max,alert.getPrix_max());
        values.put(prix_min,alert.getPrix_min());
        values.put(quartier,alert.getQuartier());
        values.put(operation,alert.getOperation());
        values.put(status,alert.getStatus());

        bdd.insert(TABLE_ALERTS,null,values);
        bdd.close();
    }

    public Alert getAlert(String alert_id){
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_ALERTS, new String[]{"*"},id + " LIKE " + alert_id,null,null,
                null, null);
        if (cursor.moveToNext()){
            Alert alert = new Alert();
            alert.setId(cursor.getString(0));
            alert.setType(cursor.getString(1));
            alert.setDate(cursor.getString(2));
            alert.setPays(cursor.getString(3));
            alert.setVille(cursor.getString(4));
            alert.setSuperficieMin(cursor.getString(5));
            alert.setSuperficieMax(cursor.getString(6));
            alert.setPrix_min(cursor.getString(7));
            alert.setPrix_max(cursor.getString(8));
            alert.setStatus(cursor.getInt(9));
            alert.setOperation(cursor.getString(10));
            alert.setQuartier(cursor.getString(11));
            return alert;
        }
        cursor.close();
        bdd.close();

        return null;
    }
    public ArrayList<Alert> getAll(){
        ArrayList<Alert> alerts = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Alert alert;
        Cursor cursor = bdd.query(TABLE_ALERTS, new String[]{"*"},null,null,null,
                null, null);
        while (cursor.moveToNext()){
            alert = new Alert();
            alert.setId(cursor.getString(0));
            alert.setType(cursor.getString(1));
            alert.setDate(cursor.getString(2));
            alert.setPays(cursor.getString(3));
            alert.setVille(cursor.getString(4));
            alert.setSuperficieMin(cursor.getString(5));
            alert.setSuperficieMax(cursor.getString(6));
            alert.setPrix_min(cursor.getString(7));
            alert.setPrix_max(cursor.getString(8));
            alerts.add(alert);
        }
        cursor.close();
        bdd.close();

        return alerts;
    }

    public void deleteAlert(String alert_id){
        bdd = myHelper.getWritableDatabase();
        bdd.delete(TABLE_ALERTS,id + " = " + alert_id,null);
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
