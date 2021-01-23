package niamoro.annonces.databases.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import niamoro.annonces.databases.helpers.RechercheHelper;
import niamoro.annonces.utils.Recherche;

public class RechercheManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "afrimoov_annonces_recherches";

    private static final String ID = "id";
    private static final String PAYS = "pays";
    private static final String VILLE = "ville";
    private static final String TYPE_BIEN = "type_bien";
    private static final String TYPE_OPERATION = "type_operation";
    private static final String DATE = "date";
    private static final String STATUS = "status";

    private static final String TABLE_RECHERCHES = "recherches";

    private RechercheHelper helper;
    private SQLiteDatabase db;

    public RechercheManager(Context context){
        helper = new RechercheHelper(context,NAME_DB,null,VERSION_BDD);
    }

    public void insertRecherche(Recherche recherche){
        if (recherche == null)
            return;
        if (verifyStatus(recherche)) {
            updateStatus(recherche);
            return;
        }
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID,recherche.getId());
        values.put(PAYS,recherche.getPays());
        values.put(VILLE,recherche.getVille());
        values.put(TYPE_BIEN,recherche.getTypeBien());
        values.put(TYPE_OPERATION,recherche.getTypeOperation());
        values.put(DATE,recherche.getDate());
        values.put(STATUS,recherche.getStatus());

        db.insert(TABLE_RECHERCHES,null,values);
        FirebaseMessaging.getInstance().subscribeToTopic(recherche.getTopic());

        db.close();

    }


    public void updateStatus(Recherche recherche){
        if (recherche == null)
            return;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS,recherche.getStatus());

        try {
            db.update(TABLE_RECHERCHES, values, ID + " LIKE ?", new String[]{recherche.getId()});
            if (recherche.getStatus() == 1){
                FirebaseMessaging.getInstance().subscribeToTopic(recherche.getTopic());
            }
            else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(recherche.getTopic());
            }
        }
        finally {
            db.close();
        }
    }

    public void updateStatus(List<Recherche> recherches){

        if (recherches == null || recherches.isEmpty())
            return;

        db = helper.getWritableDatabase();
        db.beginTransaction();
        for (Recherche recherche:recherches) {
            ContentValues values = new ContentValues();
            values.put(STATUS, recherche.getStatus());
            db.update(TABLE_RECHERCHES, values, ID + " LIKE ?", new String[]{recherche.getId()});
        }
        try {
            db.setTransactionSuccessful();
            db.endTransaction();
            for (Recherche recherche:recherches){
                if (recherche.getStatus() == 1){
                    FirebaseMessaging.getInstance().subscribeToTopic(recherche.getTopic());
                    Log.e("Topic",recherche.getTopic());
                }
                else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(recherche.getTopic());
                }
            }
        }
        finally {
            db.close();
        }


    }

    public void deleteRecherche(String pays){
        if (pays == null || pays.isEmpty())
            return;
        db = helper.getWritableDatabase();
        db.delete(TABLE_RECHERCHES,PAYS + " LIKE ?", new String[]{pays});

        db.close();
    }

    public List<Recherche> getRecherches(){
        List<Recherche> recherches = new ArrayList<>();
        db = helper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_RECHERCHES,new String[]{"*"},
        null,
                null,
                null,
                null,
                DATE + " DESC");

        if (cursor != null){
            Recherche recherche;
            while (cursor.moveToNext()){
                recherche = new Recherche(cursor.getString(0));
                recherche.setPays(cursor.getString(1));
                recherche.setVille(cursor.getString(2));
                recherche.setTypeBien(cursor.getString(3));
                recherche.setTypeOperation(cursor.getString(4));
                recherche.setDate(cursor.getLong(6));
                recherche.setStatus(cursor.getInt(5));
                recherches.add(recherche);
            }
            cursor.close();
        }

        db.close();
        return recherches;
    }

    public List<Recherche> getActives(){
        List<Recherche> recherches = new ArrayList<>();
        db = helper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_RECHERCHES,new String[]{"*"},
                STATUS + " = 1",
                null,
                null,
                null,
                DATE + " DESC");

        if (cursor != null){
            Recherche recherche;
            while (cursor.moveToNext()){
                recherche = new Recherche(cursor.getString(0));
                recherche.setPays(cursor.getString(1));
                recherche.setVille(cursor.getString(2));
                recherche.setTypeBien(cursor.getString(3));
                recherche.setTypeOperation(cursor.getString(4));
                recherche.setDate(cursor.getLong(6));
                recherche.setStatus(cursor.getInt(5));
                recherches.add(recherche);
            }
            cursor.close();
        }

        db.close();
        return recherches;
    }

    public void deleteRecherche(Recherche recherche) {
        if (recherche == null)
            return;
        db = helper.getWritableDatabase();
        db.delete(TABLE_RECHERCHES,ID + " LIKE ?", new String[]{recherche.getId()});
        if (recherche.getStatus() == 1){
            FirebaseMessaging.getInstance().unsubscribeFromTopic(recherche.getTopic());
        }
        db.close();
    }
    private boolean verifyStatus(Recherche recherche){

        db = helper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_RECHERCHES,new String[]{ID}, VILLE + " LIKE ? AND " + TYPE_BIEN + " LIKE ? AND " +
                TYPE_OPERATION + " LIKE ?", new String[]{recherche.getVille(),recherche.getTypeBien(),recherche.getTypeOperation()},
                null,null,null);

        if (cursor !=null && cursor.moveToNext())
        {
            cursor.close();
            return true;
        }

        return false;
    }
}
