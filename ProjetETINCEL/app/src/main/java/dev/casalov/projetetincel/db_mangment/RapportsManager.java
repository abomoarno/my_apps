package dev.casalov.projetetincel.db_mangment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.casalov.projetetincel.utils.Rapport;

public class RapportsManager {

    private static String DB_NAME = "rapports";
    private static int VERSION = 1;
    private static final String RAPPORT_ID = "rapport_id";
    private static final String titre = "titre";
    private static final String CLIENT_ID = "client_id";
    private static final String TYPE = "type";
    private static final String DATE_D = "date_d";
    private static final String DATE_F = "date_f";
    private static final String REDACTEUR = "redacteur";
    private static final String VERIFICATEUR = "verificateur";
    private static final String APPROBATEUR = "approbateur";
    private static final String CONTACT_SITE = "contact_site";
    private static final String CHARGE_AFFAIRES = "charge_affaires";
    private static final String CHARGE_TRAVAUX = "charge_travaux";
    private static final String TECHNICIENS = "techniciens";

    private static String TABLE_RAPPORTS = "rapports";

    private RapportsHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public RapportsManager(Context context){
        helper = new RapportsHelper(context, DB_NAME,null,VERSION);
        this.context = context;
    }

    public void insertRapport(Rapport rapport){

        ContentValues values = new ContentValues();

        values.put(RAPPORT_ID, rapport.getRapport_id());
        values.put(titre, rapport.getNom());
        values.put(CLIENT_ID, rapport.getClient_id());
        values.put(TYPE, rapport.getType());
        values.put(DATE_D, rapport.getDate_debut());
        values.put(DATE_F, rapport.getDate_fin());
        values.put(REDACTEUR, rapport.getRedacteur());
        values.put(VERIFICATEUR, rapport.getVerificateur());
        values.put(APPROBATEUR, rapport.getApprobateur());
        values.put(CONTACT_SITE, rapport.getContact_sur_site());
        values.put(CHARGE_AFFAIRES, rapport.getCharge_affaires());
        values.put(CHARGE_TRAVAUX, rapport.getCharge_travaux());
        values.put(TECHNICIENS, rapport.getTechniciens());

        db = helper.getWritableDatabase();
        db.insert(TABLE_RAPPORTS,null,values);
        db.close();
    }

    public Rapport getRapport(String rapport_id){
        Rapport rapport = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_RAPPORTS,new String[]{"*"}, RAPPORT_ID + " LIKE ?",
                new String[]{rapport_id}, null, null,null);

        if (cursor != null && cursor.moveToNext()){
            rapport = new Rapport(cursor.getString(0), cursor.getString(1));
            rapport.setClient_id(cursor.getString(2));
            rapport.setType(cursor.getString(3));
            rapport.setDate_debut(cursor.getString(4));
            rapport.setDate_fin(cursor.getString(5));
            rapport.setRedacteur(cursor.getString(6));
            rapport.setVerificateur(cursor.getString(7));
            rapport.setApprobateur(cursor.getString(8));
            rapport.setContact_sur_site(cursor.getString(9));
            rapport.setCharge_affaires(cursor.getString(10));
            rapport.setCharge_travaux(cursor.getString(11));
            rapport.setTechniciens(cursor.getString(12));
            cursor.close();
        }

        return rapport;
    }

    public List<Rapport> getAllRapports(){

        List<Rapport> rapports = new ArrayList<>();
        Rapport rapport = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_RAPPORTS,new String[]{"*"}, null,
                null, null, null,null);

        if (cursor !=  null){
            while (cursor.moveToNext()){
                rapport = new Rapport(cursor.getString(0), cursor.getString(1));
                rapport.setClient_id(cursor.getString(2));
                rapport.setType(cursor.getString(3));
                rapport.setDate_debut(cursor.getString(4));
                rapport.setDate_fin(cursor.getString(5));
                rapport.setRedacteur(cursor.getString(6));
                rapport.setVerificateur(cursor.getString(7));
                rapport.setApprobateur(cursor.getString(8));
                rapport.setContact_sur_site(cursor.getString(9));
                rapport.setCharge_affaires(cursor.getString(10));
                rapport.setCharge_travaux(cursor.getString(11));

                rapports.add(rapport);
            }
            cursor.close();
        }
        return rapports;
    }

    public void deleteRapport(String rapport_id){
        db = helper.getWritableDatabase();
        new OperationsManager(context).deleteOperation(rapport_id);
        new MaintenanceManager(context).deleteMaintenance(rapport_id);
        new ElementsManager(context).deleteElement(rapport_id);
        new SurveillerConformiteManager(context).deleteElement(rapport_id);
        db.delete(TABLE_RAPPORTS,RAPPORT_ID + " LIKE ?",new String[]{rapport_id});
    }
}
