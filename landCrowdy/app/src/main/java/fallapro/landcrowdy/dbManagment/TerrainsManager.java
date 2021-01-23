package fallapro.landcrowdy.dbManagment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fallapro.landcrowdy.classes.Terrain;

public class TerrainsManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "lcdy_terrains";

    private static final String TABLE_TERRAINS = "terrains";

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
    private static final String status = "status";

    private SQLiteDatabase bdd;
    private TerrainHelper myHelper;
    private Context context;

    public TerrainsManager(Context context) {
        myHelper = new TerrainHelper(context,NAME_DB,null,VERSION_BDD);
        this.context = context;
    }

    public ArrayList<Terrain> getAll(String p, String v, String t, int prix_min, int prix_max,
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
        ArrayList<Terrain> terrains = new ArrayList<>();

        Cursor cursor = bdd.query(TABLE_TERRAINS,new String[]{"*"},query,args,
                null,null,date + " DESC");

        while (cursor.moveToNext()){
            Terrain terrain = new Terrain(cursor.getInt(0),cursor.getString(2));
            terrain.setType(cursor.getString(1));
            terrain.setDate(cursor.getString(3));
            terrain.setPays(cursor.getString(4));
            terrain.setPays(cursor.getString(5));
            terrain.setVille(cursor.getString(6));
            terrain.setSuperficie(cursor.getInt(7));
            terrain.setDescription(cursor.getString(8));
            terrain.setLien(cursor.getString(9));
            terrain.setImage(cursor.getString(10));
            terrain.setAffichages(cursor.getInt(11));
            terrain.setLiked(cursor.getInt(10));
            terrain.setPrix(cursor.getInt(12));
            terrain.setStatus(cursor.getString(13));
            terrain.setQuartier(cursor.getString(14));

            terrains.add(terrain);
        }

        bdd.close();
        cursor.close();
        return terrains;
    }
    public ArrayList<Terrain> getLiked(){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        ArrayList<Terrain> terrains = new ArrayList<>();

        Cursor cursor = bdd.query(TABLE_TERRAINS,new String[]{"*"},liked + " LIKE 1 ",null,
                null,null,date + " DESC");

        while (cursor.moveToNext()){
            Terrain terrain = new Terrain(cursor.getInt(0),cursor.getString(2));
            terrain.setType(cursor.getString(1));
            terrain.setDate(cursor.getString(3));
            terrain.setPays(cursor.getString(4));
            terrain.setPays(cursor.getString(5));
            terrain.setVille(cursor.getString(6));
            terrain.setSuperficie(cursor.getInt(7));
            terrain.setDescription(cursor.getString(8));
            terrain.setLien(cursor.getString(9));
            terrain.setImage(cursor.getString(10));
            terrain.setAffichages(cursor.getInt(11));
            terrain.setLiked(cursor.getInt(10));
            terrain.setPrix(cursor.getInt(12));
            terrain.setStatus(cursor.getString(13));
            terrain.setQuartier(cursor.getString(14));

            terrains.add(terrain);
        }

        bdd.close();
        cursor.close();
        return terrains;
    }

    public void insertTerrain(Terrain terrain){

        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(id,terrain.getId());
        values.put(type,terrain.getType());
        values.put(titre,terrain.getTitre());
        values.put(description,terrain.getDescription());
        values.put(lien,terrain.getLien());
        values.put(image,terrain.getImage());
        values.put(date,terrain.getDate());
        values.put(pays,terrain.getPays());
        values.put(ville,terrain.getVille());
        values.put(superficie,terrain.getSuperficie());
        values.put(prix,terrain.getPrix());
        values.put(quartier,terrain.getQuartier());
        values.put(liked,terrain.getLiked());
        values.put(affichages,terrain.getAffichages());
        values.put(status,terrain.getStatus());

        bdd.insert(TABLE_TERRAINS,null,values);
        bdd.close();
    }

    public void insertAll(ArrayList<Terrain> terrains){

        for (Terrain terrain:terrains)
        {
            bdd = myHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(id,terrain.getId());
            values.put(type,terrain.getType());
            values.put(titre,terrain.getTitre());
            values.put(description,terrain.getDescription());
            values.put(lien,terrain.getLien());
            values.put(image,terrain.getImage());
            values.put(date,terrain.getDate());
            values.put(pays,terrain.getPays());
            values.put(ville,terrain.getVille());
            values.put(superficie,terrain.getSuperficie());
            values.put(prix,terrain.getPrix());
            values.put(quartier,terrain.getQuartier());
            values.put(liked,terrain.getLiked());
            values.put(affichages,terrain.getAffichages());
            values.put(status,terrain.getStatus());

            bdd.insert(TABLE_TERRAINS,null,values);
            bdd.close();
        }
    }

    public void doLike(int terrain_id){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(liked,1);
        bdd.update(TABLE_TERRAINS,values,id + "LIKE " + terrain_id, null);
        bdd.close();
    }
    public void doDisLike(int terrain_id){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(liked,0);
        bdd.update(TABLE_TERRAINS,values,id + "LIKE " + terrain_id, null);
        bdd.close();
    }

    public int getLastId(){

        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_TERRAINS,new String[]{"*"},null,
                null, null, null, id + "DESC LIMIT 1");
        int last_id = 0;
        if (cursor != null && cursor.moveToNext()){
            last_id = cursor.getInt(0);
            cursor.close();
        }
        bdd.close();
        return last_id;
    }

    public void setAffichages(int terrain_id,int aff){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(affichages,aff);
        bdd.update(TABLE_TERRAINS,values,id + "LIKE " + terrain_id, null);
        bdd.close();
    }

    private int getAffichages(int terrain_id){

        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_TERRAINS,new String[]{affichages},id + " LIKE ?",
                new String[]{"" + terrain_id}, null, null, null);
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

        Cursor cursor = bdd.query(TABLE_TERRAINS,new String[]{"COUNT(" + id + ")"},null,null,
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
