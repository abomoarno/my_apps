package casalov.security.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import casalov.security.classes.Intervention;
import casalov.security.utils.Utils;

public class InterventionsManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "montpellier_security";
    private static final String TABLE_INTERVENTIONS = "interventions";

    private static String id = "id";
    private static String motif = "motif";
    private static String statut = "statut";
    private static String date = "date";
    private static String lieu = "lieu";
    private static String prix = "prix";

    private SQLiteDatabase bdd;
    private InterventionHelper myHelper;

    public InterventionsManager(Context context) {
        myHelper = new InterventionHelper(context,NAME_DB,null,VERSION_BDD);
    }

    public List<Intervention> getAllInterventions(){
        List<Intervention> interventions = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_INTERVENTIONS,new String[]{"*"}, null ,null,
                null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                Intervention intervention = new Intervention(cursor.getString(0),cursor.getString(1));
                intervention.setStatut(cursor.getString(2));
                intervention.setDate(cursor.getString(3));
                intervention.setLieu(cursor.getString(4));
                intervention.setPrix(cursor.getInt(5));
                interventions.add(intervention);
            }

            cursor.close();
        }
        bdd.close();
        return interventions;
    }

    public List<Intervention> getUnpayInterventions(){
        List<Intervention> interventions = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_INTERVENTIONS,new String[]{"*"}, statut + " LIKE ?" ,new String[]{Utils.STATUT_NOT_OK},
                null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                Intervention intervention = new Intervention(cursor.getString(0),cursor.getString(1));
                intervention.setStatut(cursor.getString(2));
                intervention.setDate(cursor.getString(3));
                intervention.setLieu(cursor.getString(4));
                intervention.setPrix(cursor.getInt(5));
                interventions.add(intervention);
            }

            cursor.close();
        }
        bdd.close();
        return interventions;
    }

    public void insertIntervention(Intervention intervention){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(id,intervention.getId());
        values.put(motif,intervention.getMotif());
        values.put(statut,intervention.getStatut());
        values.put(lieu,intervention.getLieu());
        values.put(date,intervention.getDate());
        values.put(prix,intervention.getPrix());

        bdd.insert(TABLE_INTERVENTIONS,null,values);
        bdd.close();
    }

    public void updateStatut(String intervention_id,String new_statut){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(statut,new_statut);
        bdd.update(TABLE_INTERVENTIONS,values,id + " LIKE ?", new String[]{intervention_id});
    }

    public int getLastId(){
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_INTERVENTIONS,new String[]{id},null,null,null,null,
                id + " DESC");
        if (cursor.moveToNext()){
            return cursor.getInt(0);
        }
        cursor.close();
        bdd.close();
        return  0;
    }
}
