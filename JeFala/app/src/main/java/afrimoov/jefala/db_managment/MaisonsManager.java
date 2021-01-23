package afrimoov.jefala.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import afrimoov.jefala.classes.Maison;

public class MaisonsManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "lcdy_maisons";

    private static final String TABLE_MAISONS = "maisons";

    private static final String id ="id";
    private static final String type = "type";
    private static final String titre = "titre";
    private static final String description = "description";
    private static final String lien = "lien";
    private static final String image = "image";
    private static final String date = "date";
    private static final String pays = "pays";
    private static final String ville = "ville";
    private static final String superficie = "superficie";
    private static final String quartier = "quartier";
    private static final String prix = "prix";
    private static final String liked = "liked";
    private static final String affichages = "affichages";
    private static final String style = "style";

    private SQLiteDatabase bdd;
    private MaisonHelper myHelper;
    private Context context;

    public MaisonsManager(Context context) {
        myHelper = new MaisonHelper(context,NAME_DB,null,VERSION_BDD);
        this.context = context;
    }

    public ArrayList<Maison> getAll(String p, String v, String t, int prix_min, int prix_max,
                                    int superficie_min, int superfice_max){

        String[] args = (!v.equals("tous"))? new String[9] : new String[8];

        String query = pays + " LIKE ? ";
        args[0] = p;
        int num_arg = 0;

        if (!v.equals("tous")) {
            query += " AND " + ville + " LIKE ? ";
            args[++ num_arg] = v;
        }
        query += "AND " + type + " LIKE ? ";
        args[++ num_arg] = t;

        query += " AND ((" + prix  + " <= ? AND " + prix  + " >= ?) OR " +
                prix + " LIKE ? )";
        query += " AND ((" + superficie  + " <= ? AND " + superficie  + " >= ?) OR " +
                superficie + " LIKE ? )";
        args[++ num_arg] = prix_max + "";
        args[++ num_arg] = prix_min + "";
        args[++ num_arg] = "0";
        args[++ num_arg] = superfice_max + "";
        args[++ num_arg] = superficie_min + "";
        args[++ num_arg] = "0";
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        ArrayList<Maison> maisons = new ArrayList<>();

        Cursor cursor = bdd.query(TABLE_MAISONS,new String[]{"*"},query,args,
                null,null,date + " DESC");

        while (cursor.moveToNext()){
            Maison maison = new Maison(cursor.getInt(0),cursor.getString(2));
            maison.setType(cursor.getString(1));
            maison.setDate(cursor.getString(3));
            maison.setPays(cursor.getString(4));
            maison.setPays(cursor.getString(5));
            maison.setVille(cursor.getString(6));
            maison.setSuperficie(cursor.getInt(7));
            maison.setDescription(cursor.getString(8));
            maison.setLien(cursor.getString(9));
            maison.setImage(cursor.getString(10));
            maison.setAffichages(cursor.getInt(11));
            maison.setLiked(cursor.getInt(10));
            maison.setPrix(cursor.getInt(12));
            maison.setStyle(cursor.getString(13));
            maison.setQuartier(cursor.getString(14));

            maisons.add(maison);
        }

        bdd.close();
        cursor.close();
        return maisons;
    }
    public ArrayList<Maison> getLiked(){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        ArrayList<Maison> maisons = new ArrayList<>();

        Cursor cursor = bdd.query(TABLE_MAISONS,new String[]{"*"},liked + " LIKE 1 ",null,
                null,null,date + " DESC");

        while (cursor.moveToNext()){
            Maison maison = new Maison(cursor.getInt(0),cursor.getString(2));
            maison.setType(cursor.getString(1));
            maison.setDate(cursor.getString(3));
            maison.setPays(cursor.getString(4));
            maison.setPays(cursor.getString(5));
            maison.setVille(cursor.getString(6));
            maison.setSuperficie(cursor.getInt(7));
            maison.setDescription(cursor.getString(8));
            maison.setLien(cursor.getString(9));
            maison.setImage(cursor.getString(10));
            maison.setAffichages(cursor.getInt(11));
            maison.setLiked(cursor.getInt(10));
            maison.setPrix(cursor.getInt(12));
            maison.setStyle(cursor.getString(13));
            maison.setQuartier(cursor.getString(14));

            maisons.add(maison);
        }

        bdd.close();
        cursor.close();
        return maisons;
    }

    public void insertMaison(Maison maison){

        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(id,maison.getId());
        values.put(type,maison.getType());
        values.put(titre,maison.getTitre());
        values.put(description,maison.getDescription());
        values.put(lien,maison.getLien());
        values.put(image,maison.getImage());
        values.put(date,maison.getDate());
        values.put(pays,maison.getPays());
        values.put(ville,maison.getVille());
        values.put(superficie,maison.getSuperficie());
        values.put(prix,maison.getPrix());
        values.put(quartier,maison.getQuartier());
        values.put(liked,maison.getLiked());
        values.put(affichages,maison.getAffichages());
        values.put(style,maison.getStyle());

        bdd.insert(TABLE_MAISONS,null,values);
        bdd.close();
    }

    public void insertAll(ArrayList<Maison> maisons){

        for (Maison maison:maisons)
        {
            bdd = myHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(id,maison.getId());
            values.put(type,maison.getType());
            values.put(titre,maison.getTitre());
            values.put(description,maison.getDescription());
            values.put(lien,maison.getLien());
            values.put(image,maison.getImage());
            values.put(date,maison.getDate());
            values.put(pays,maison.getPays());
            values.put(ville,maison.getVille());
            values.put(superficie,maison.getSuperficie());
            values.put(prix,maison.getPrix());
            values.put(quartier,maison.getQuartier());
            values.put(liked,maison.getLiked());
            values.put(affichages,maison.getAffichages());
            values.put(style,maison.getStyle());

            bdd.insert(TABLE_MAISONS,null,values);
            bdd.close();
        }
    }

    public void doLike(int maison_id){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(liked,1);
        bdd.update(TABLE_MAISONS,values,id + "LIKE " + maison_id, null);
        bdd.close();
    }
    public void doDisLike(int maison_id){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(liked,0);
        bdd.update(TABLE_MAISONS,values,id + "LIKE " + maison_id, null);
        bdd.close();
    }

    public int getLastId(){

        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_MAISONS,new String[]{"*"},null,
                null, null, null, id + "DESC LIMIT 1");
        int last_id = 0;
        if (cursor != null && cursor.moveToNext()){
            last_id = cursor.getInt(0);
            cursor.close();
        }
        bdd.close();
        return last_id;
    }

    public void setAffichages(int maison_id,int aff){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(affichages,aff);
        bdd.update(TABLE_MAISONS,values,id + "LIKE " + maison_id, null);
        bdd.close();
    }

    private int getAffichages(int maison_id){

        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_MAISONS,new String[]{affichages},id + " LIKE ?",
                new String[]{"" + maison_id}, null, null, null);
        int aff = 0;
        if (cursor != null && cursor.moveToNext()){
            aff = cursor.getInt(0);
            cursor.close();
        }

        bdd.close();
        return aff;
    }
    public int getTotal(){

        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_MAISONS,new String[]{"COUNT(" + id + ")"},null,null,
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
