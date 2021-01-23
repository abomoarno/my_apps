package dev.casalov.projetetincel.db_mangment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dev.casalov.projetetincel.utils.Devis;
import dev.casalov.projetetincel.utils.Rapport;

public class DevisManager {

    private static String DB_NAME = "devis";
    private static int VERSION = 1;

    private static final String DEVIS_ID = "devis_id";
    private static final String titre = "titre";
    private static final String CLIENT_ID = "client_id";
    private static final String DATE_REDACTION = "date_redaction";
    private static final String DATE_REALISATION = "date_realisation";
    private static final String REDACTEUR = "redacteur";
    private static final String CHARGES_CLIENT = "charges_client";
    private static final String CHARGES_EINCEL = "charges_etincel";
    private static final String VILLE = "ville";

    private static String TABLE_DEVIS = "devis";

    private DevisHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DevisManager(Context context){
        helper = new DevisHelper(context, DB_NAME,null,VERSION);
        this.context = context;
    }

    public void insertDevis(Devis devis){

        try {

            ContentValues values = new ContentValues();
            values.put(DEVIS_ID, devis.getDevis_id());
            values.put(titre, devis.getNom());
            values.put(CLIENT_ID, devis.getClient());
            values.put(DATE_REDACTION, devis.getDate_document());
            values.put(DATE_REALISATION, devis.getDate_realisation());
            values.put(REDACTEUR, devis.getRedacteur()+";"+devis.getRedacteur_poste());
            values.put(CHARGES_CLIENT, devis.getCharges_client());
            values.put(CHARGES_EINCEL, devis.getCharges_etincel());
            values.put(VILLE, devis.getVille());
            db = helper.getWritableDatabase();
            db.insert(TABLE_DEVIS, null, values);
            db.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Log.e("DEVIS","REGISTERED");
    }

    public void updateDevis(Devis devis){

        try {

            ContentValues values = new ContentValues();
            values.put(titre, devis.getNom());
            values.put(CLIENT_ID, devis.getClient());
            values.put(DATE_REDACTION, devis.getDate_document());
            values.put(DATE_REALISATION, devis.getDate_realisation());
            values.put(REDACTEUR, devis.getRedacteur()+";"+devis.getRedacteur_poste());
            values.put(CHARGES_CLIENT, devis.getCharges_client());
            values.put(CHARGES_EINCEL, devis.getCharges_etincel());
            values.put(VILLE, devis.getVille());
            db = helper.getWritableDatabase();
            db.update(TABLE_DEVIS,values,DEVIS_ID + " LIKE ?", new String[]{devis.getDevis_id()});
            db.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Log.e("DEVIS","REGISTERED");
    }

    public Devis getDevis(String devis_id){
        Devis devis = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_DEVIS,new String[]{"*"}, DEVIS_ID + " LIKE ?",
                new String[]{devis_id}, null, null,null);

        if (cursor != null && cursor.moveToNext()){
            devis = new Devis(cursor.getString(0), cursor.getString(1));
            devis.setClient_id(cursor.getString(2));
            devis.setDate_document(cursor.getString(3));
            devis.setDate_realisation(cursor.getString(4));
            devis.setRedacteur(cursor.getString(5));
            devis.setCharges_client(cursor.getString(6));
            devis.setCharges_etincel(cursor.getString(7));
            devis.setVille(cursor.getString(8));
            cursor.close();
        }
        else
            Log.e("PAS DE DEVIS", "OK");

        return devis;
    }

    public List<Devis> getAllDevis(){

        List<Devis> devisList = new ArrayList<>();
        Devis devis = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_DEVIS,new String[]{"*"}, null,
                null, null, null,null);

        if (cursor !=  null){
            while (cursor.moveToNext()){
                devis = new Devis(cursor.getString(0), cursor.getString(1));
                devis.setClient_id(cursor.getString(2));
                devis.setDate_document(cursor.getString(3));
                devis.setDate_realisation(cursor.getString(4));
                devis.setRedacteur(cursor.getString(5));
                devis.setCharges_client(cursor.getString(6));
                devis.setCharges_etincel(cursor.getString(7));
                devis.setVille(cursor.getString(8));
                devisList.add(devis);
            }
            cursor.close();
        }
        return devisList;
    }

    public void delete(String devis_id){
        db = helper.getWritableDatabase();
        new PrestationsManager(context).delete(devis_id);
        db.delete(TABLE_DEVIS,DEVIS_ID + " LIKE ?", new String[]{devis_id});
    }
}