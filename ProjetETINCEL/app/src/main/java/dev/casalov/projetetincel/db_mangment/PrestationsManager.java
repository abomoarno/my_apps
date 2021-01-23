package dev.casalov.projetetincel.db_mangment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dev.casalov.projetetincel.utils.Prestation;
import dev.casalov.projetetincel.utils.Rapport;

public class PrestationsManager {

    private static String DB_NAME = "prestations";
    private static int VERSION = 1;

    private static final String PRESTATION_ID = "prestation_id";
    private static final String titre = "titre";
    private static final String DEVIS_ID = "devis_id";
    private static final String QUANTITE = "quantite";
    private static final String PRIX = "prix";

    private static String TABLE_PRESTATIONS = "prestations";

    private PrestationsHelper helper;
    private SQLiteDatabase db;

    public PrestationsManager(Context context){
        helper = new PrestationsHelper(context, DB_NAME,null,VERSION);
    }
    public void insertPrestation(Prestation prestation){
        Log.e("PRESTATION_ID",prestation.getPrestation_id());
        ContentValues values = new ContentValues();
        values.put(titre, prestation.getNom());
        values.put(DEVIS_ID, prestation.getDevis_id());
        values.put(QUANTITE, prestation.getQuantite());
        values.put(PRIX, prestation.getPrix());
        db = helper.getWritableDatabase();
        if (!verify(prestation.getPrestation_id())) {
            values.put(PRESTATION_ID, prestation.getPrestation_id());
            db.insert(TABLE_PRESTATIONS, null, values);
        }
        else {
            db.update(TABLE_PRESTATIONS,values,PRESTATION_ID + " LIKE ?", new String[]{prestation.getPrestation_id()});
        }
    }

    public List<Prestation> getFromDevis(String devis_id){

        List<Prestation> prestations = new ArrayList<>();
        Prestation prestation = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PRESTATIONS,new String[]{"*"},
                DEVIS_ID + " LIKE ?",
                new String[]{devis_id},
                null, null,null);

        if (cursor !=  null){
            while (cursor.moveToNext()){
                prestation = new Prestation(cursor.getString(0), cursor.getString(1));
                prestation.setDevis_id(cursor.getString(2));
                prestation.setQuantite(cursor.getInt(3));
                prestation.setPrix(cursor.getInt(4));
                prestations.add(prestation);
            }
            cursor.close();
        }

        return prestations;
    }

    private boolean verify(String id){

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PRESTATIONS,new String[]{"*"},PRESTATION_ID + " LIKE ?",
                new String[]{id}, null, null, null);
        if (cursor!=null && cursor.moveToNext())
        {
            cursor.close();
            return true;
        }

        return false;
    }


    public void delete(String devis){
        db = helper.getWritableDatabase();
        db.delete(TABLE_PRESTATIONS, DEVIS_ID + " LIKE ?", new String[]{devis});
    }
}
