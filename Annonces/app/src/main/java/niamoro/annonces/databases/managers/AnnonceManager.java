package niamoro.annonces.databases.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import niamoro.annonces.databases.helpers.AnnonceHelper;
import niamoro.annonces.utils.Annonce;

public class AnnonceManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "afrimoov_annonces";

    private static final String ID = "id";
    private static final String TITRE = "titre";
    private static final String PAYS = "pays";
    private static final String VILLE = "ville";
    private static final String TYPE_BIEN = "type_bien";
    private static final String TYPE_OPERATION = "type_operation";
    private static final String LIEN = "lien";
    private static final String IMAGE = "image";
    private static final String DATE = "date";
    private static final String PRIX = "prix";
    private static final String LIKED = "liked";


    private static final String TABLE_ANNONCES = "annonces";

    private AnnonceHelper helper;
    private SQLiteDatabase db;

    public AnnonceManager(Context context){
        helper = new AnnonceHelper(context,NAME_DB,null,VERSION_BDD);
    }

    public void insertAnnonce(Annonce annonce){
        if (annonce == null)
            return;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID,annonce.getId());
        values.put(TITRE,annonce.getTitre());
        values.put(PAYS,annonce.getPays());
        values.put(VILLE,annonce.getVille());
        values.put(TYPE_BIEN,annonce.getTypeBien());
        values.put(TYPE_OPERATION,annonce.getTypeOperation());
        values.put(LIEN,annonce.getLien());
        values.put(IMAGE,annonce.getImage());
        values.put(DATE,annonce.getDate());
        values.put(PRIX,annonce.getPrix());
        values.put(LIKED,annonce.getLiked());
        db.insert(TABLE_ANNONCES,null,values);
        db.close();
    }

    public void insertAnnonces(List<Annonce> annonces){
        if (annonces == null || annonces.isEmpty())
            return;
        db = helper.getWritableDatabase();
        db.beginTransaction();

        for (Annonce annonce:annonces) {
            ContentValues values = new ContentValues();

            values.put(ID, annonce.getId());
            values.put(TITRE, annonce.getTitre());
            values.put(PAYS, annonce.getPays());
            values.put(VILLE, annonce.getVille());
            values.put(TYPE_BIEN, annonce.getTypeBien());
            values.put(TYPE_OPERATION, annonce.getTypeOperation());
            values.put(LIEN, annonce.getLien());
            values.put(IMAGE, annonce.getImage());
            values.put(DATE, annonce.getDate());
            values.put(PRIX, annonce.getPrix().trim());
            values.put(LIKED,annonce.getLiked());
            db.insert(TABLE_ANNONCES, null, values);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void updateAnnonces(List<Annonce> annonces){
        if (annonces == null || annonces.isEmpty())
            return;
        db = helper.getWritableDatabase();
        db.beginTransaction();

        for (Annonce annonce:annonces) {
            ContentValues values = new ContentValues();

            values.put(TITRE, annonce.getTitre());
            values.put(PAYS, annonce.getPays());
            values.put(VILLE, annonce.getVille());
            values.put(TYPE_BIEN, annonce.getTypeBien());
            values.put(TYPE_OPERATION, annonce.getTypeOperation());
            values.put(LIEN, annonce.getLien());
            values.put(IMAGE, annonce.getImage());
            values.put(DATE, annonce.getDate());
            values.put(PRIX, annonce.getPrix());
            db.update(TABLE_ANNONCES,values, ID + " LIKE ?", new String[]{annonce.getId()});
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void updateAnnonce(Annonce annonce){
        if (annonce == null)
            return;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITRE,annonce.getTitre());
        values.put(PAYS,annonce.getPays());
        values.put(VILLE,annonce.getVille());
        values.put(TYPE_BIEN,annonce.getTypeBien());
        values.put(TYPE_OPERATION,annonce.getTypeOperation());
        values.put(LIEN,annonce.getLien());
        values.put(IMAGE,annonce.getImage());
        values.put(DATE,annonce.getDate());
        values.put(PRIX,annonce.getPrix());
        values.put(LIKED,annonce.getLiked());
        db.update(TABLE_ANNONCES,values, ID + " LIKE ?", new String[]{annonce.getId()});
        db.close();
    }

    public void updateLiked(Annonce annonce){
        if (annonce == null)
            return;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LIKED,annonce.getLiked());
        db.update(TABLE_ANNONCES,values, ID + " LIKE ?", new String[]{annonce.getId()});
        db.close();
    }

    public void deleteAnnonce(String annonce){
        if (annonce == null || annonce.isEmpty())
            return;
        db = helper.getWritableDatabase();
        db.delete(TABLE_ANNONCES,ID + " LIKE ?", new String[]{annonce});
        db.close();
    }

    public void deleteAnnonces(List<String> annonces, String pays){
        if (annonces == null || annonces.isEmpty())
            return;
        db = helper.getWritableDatabase();
        db.beginTransaction();

        for (String annonce:annonces){
            db.delete(TABLE_ANNONCES,ID + " LIKE ? AND " + PAYS + " LIKE ?", new String[]{annonce,pays});
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public Annonce getAnnonce(String annonce_id){
        Annonce annonce = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_ANNONCES,
                new String[]{"*"},
                ID + " LIKE ?",
                new String[]{annonce_id},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToNext()){
            annonce = new Annonce();
            annonce.setId(cursor.getString(0));
            annonce.setTitre(cursor.getString(1));
            annonce.setPays(cursor.getString(2));
            annonce.setVille(cursor.getString(3));
            annonce.setTypeBien(cursor.getString(4));
            annonce.setTypeOperation(cursor.getString(5));
            annonce.setLien(cursor.getString(6));
            annonce.setImage(cursor.getString(7));
            annonce.setDate(cursor.getString(8));
            annonce.setPrix(cursor.getString(9));
            annonce.setLiked(cursor.getInt(10));

            cursor.close();
        }
        db.close();
        return annonce;
    }

    public List<Annonce> getAnnonces(String type_annonce,String type_operation,String ville, String pays){

        List<Annonce> annonces = new ArrayList<>();
        db = helper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_ANNONCES,new String[]{"*"},
        TYPE_BIEN  +  " LIKE ? AND " + TYPE_OPERATION + " LIKE ? AND " + VILLE + " LIKE ? AND " + PAYS + " LIKE ?",
                new String[]{type_annonce,type_operation,ville,pays},
                null,
                null,
                DATE + " DESC");

        if (cursor != null){
            Annonce annonce;
            while (cursor.moveToNext()){
                annonce = new Annonce();
                annonce.setId(cursor.getString(0));
                annonce.setTitre(cursor.getString(1));
                annonce.setPays(cursor.getString(2));
                annonce.setVille(cursor.getString(3));
                annonce.setTypeBien(cursor.getString(4));
                annonce.setTypeOperation(cursor.getString(5));
                annonce.setLien(cursor.getString(6));
                annonce.setImage(cursor.getString(7));
                annonce.setDate(cursor.getString(8));
                annonce.setPrix(cursor.getString(9));
                annonce.setLiked(cursor.getInt(10));

                annonces.add(annonce);
            }
            cursor.close();
        }

        db.close();
        return annonces;
    }

    public List<String> getAllIds(String pays){
        db = helper.getWritableDatabase();
        List<String> ids = new ArrayList<>();
        Cursor cursor = db.query(TABLE_ANNONCES,new String[]{ID},PAYS + " LIKE ?",
                new String[]{pays}, null,null, null );
        if (cursor != null){
            while (cursor.moveToNext())
                ids.add(cursor.getString(0));
            cursor.close();
        }

        db.close();

        return ids;
    }

    public List<String> getVilles(String pays){
        db = helper.getWritableDatabase();
        List<String> ids = new ArrayList<>();
        Cursor cursor = db.query(TABLE_ANNONCES,new String[]{VILLE},PAYS + " LIKE ?",
                new String[]{pays}, VILLE,null,"COUNT(" + VILLE + ") DESC, " + VILLE + " ASC");
        if (cursor != null){
            while (cursor.moveToNext()) {
                ids.add(cursor.getString(0));
            }
            cursor.close();
        }

        db.close();

        return ids;
    }

    public List<Annonce> getFavories() {
        List<Annonce> annonces = new ArrayList<>();
        db = helper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_ANNONCES,new String[]{"*"},
                LIKED  +  " = 1",
                null,
                null,
                null,
                DATE + " DESC");

        if (cursor != null){
            Annonce annonce;
            while (cursor.moveToNext()){
                annonce = new Annonce();
                annonce.setId(cursor.getString(0));
                annonce.setTitre(cursor.getString(1));
                annonce.setPays(cursor.getString(2));
                annonce.setVille(cursor.getString(3));
                annonce.setTypeBien(cursor.getString(4));
                annonce.setTypeOperation(cursor.getString(5));
                annonce.setLien(cursor.getString(6));
                annonce.setImage(cursor.getString(7));
                annonce.setDate(cursor.getString(8));
                annonce.setPrix(cursor.getString(9));
                annonce.setLiked(cursor.getInt(10));

                Log.e("Annonce",annonce.getLiked() + "");
                annonces.add(annonce);
            }
            cursor.close();
        }

        db.close();
        return annonces;
    }


}
