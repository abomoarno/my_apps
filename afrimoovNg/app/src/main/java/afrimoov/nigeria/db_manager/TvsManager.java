package afrimoov.nigeria.db_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import afrimoov.nigeria.utilis.Live_Tv;

public class TvsManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "afrimoov_crtv_tvs";

    private static final String TABLE_TVS = "tvs";
    private static final String id ="tv_id";
    private static final String nom = "nom";
    private static final String description = "description";
    private static final String pays = "pays";
    private static final String lien = "lien";
    private static final String plateforme = "plateforme";
    private static final String image = "image";
    private static final String langue = "langue";
    private static final String categorie = "categorie";
    private static final String vues = "vues";


    private SQLiteDatabase bdd;
    private TvHelper myHelper;

    public TvsManager(Context context) {
        myHelper = new TvHelper(context,NAME_DB,null,VERSION_BDD);
    }

    public void insertTv(Live_Tv tv){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(id,tv.getTv_id());
        values.put(nom,tv.getNom());
        values.put(description,tv.getDescription());
        values.put(pays,tv.getPays());
        values.put(lien,tv.getLien());
        values.put(plateforme,tv.getPlateforme());
        values.put(image,tv.getImage());
        values.put(categorie,tv.getCategorie());
        values.put(langue,tv.getLangue());
        values.put(vues,tv.getVues());

        bdd.insert(TABLE_TVS,null,values);
        bdd.close();
    }
    public void updateTv(Live_Tv tv){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(id,tv.getTv_id());
        values.put(nom,tv.getNom());
        values.put(description,tv.getDescription());
        values.put(pays,tv.getPays());
        values.put(lien,tv.getLien());
        values.put(plateforme,tv.getPlateforme());
        values.put(image,tv.getImage());
        values.put(categorie,tv.getCategorie());
        values.put(langue,tv.getLangue());
        values.put(vues,tv.getVues());
        bdd.update(TABLE_TVS,values,id + " LIKE ?", new String[]{tv.getTv_id()});
        bdd.close();
    }

    public Live_Tv getTv(String tv_id){
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_TVS, new String[]{"*"},id + " LIKE ?",new String[]{tv_id}, null,
                null, null);
        if (cursor.moveToNext()){
            Live_Tv tv = new Live_Tv(cursor.getString(0),cursor.getString(1));
            tv.setDescription(cursor.getString(2));
            tv.setPays(cursor.getString(3));
            tv.setLien(cursor.getString(4));
            tv.setPlateforme(cursor.getString(5));
            tv.setImage(cursor.getString(6));
            tv.setLangue(cursor.getString(7));
            tv.setCategorie(cursor.getString(8));
            tv.setVues(cursor.getInt(9));
            return tv;
        }
        cursor.close();
        bdd.close();

        return null;
    }
    public List<Live_Tv> getAll(){
        List<Live_Tv> tvs = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Live_Tv tv;
        Cursor cursor = bdd.query(TABLE_TVS, new String[]{"*"},null,null,null,
                null, vues + " DESC");
        while (cursor.moveToNext()){
            tv = new Live_Tv(cursor.getString(0),cursor.getString(1));
            tv.setDescription(cursor.getString(2));
            tv.setPays(cursor.getString(3));
            tv.setLien(cursor.getString(4));
            tv.setPlateforme(cursor.getString(5));
            tv.setImage(cursor.getString(6));
            tv.setLangue(cursor.getString(7));
            tv.setCategorie(cursor.getString(8));
            tv.setVues(cursor.getInt(9));
            tvs.add(tv);
        }
        cursor.close();
        bdd.close();

        return tvs;
    }
    public List<Live_Tv> getLimit(int nbr){
        List<Live_Tv> tvs = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Live_Tv tv;
        Cursor cursor = bdd.query(TABLE_TVS, new String[]{"*"},null,null,null,
                null, vues + " DESC");
        while (cursor.moveToNext()){
            tv = new Live_Tv(cursor.getString(0),cursor.getString(1));
            tv.setDescription(cursor.getString(2));
            tv.setPays(cursor.getString(3));
            tv.setLien(cursor.getString(4));
            tv.setPlateforme(cursor.getString(5));
            tv.setImage(cursor.getString(6));
            tv.setLangue(cursor.getString(7));
            tv.setCategorie(cursor.getString(8));
            tv.setVues(cursor.getInt(9));
            tvs.add(tv);
        }
        cursor.close();
        bdd.close();

        return (tvs.size()>=nbr) ? tvs.subList(0,nbr) : tvs;
    }

    public void deleteTv(String tv_id){
        bdd = myHelper.getWritableDatabase();
        bdd.delete(TABLE_TVS,id + " LIKE ?",new String[]{tv_id});
        bdd.close();
    }

    public List<String> getIds(){
        List<String> ids = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_TVS,new String[]{id},null,null,
                null,null,null);

        if (cursor != null)
        {
            while (cursor.moveToNext())
                ids.add(cursor.getString(0));
            cursor.close();
        }
        bdd.close();

        return ids;
    }

    public boolean verifyTvId(String tv_id){
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_TVS,new String[]{id}, id + " LIKE ?", new String[]{tv_id},
                null,null,null);

        if (cursor != null && cursor.moveToNext() && cursor.getString(0).equals(tv_id)){

            cursor.close();

            return true;

        }

        bdd.close();
        return false;
    }

}
