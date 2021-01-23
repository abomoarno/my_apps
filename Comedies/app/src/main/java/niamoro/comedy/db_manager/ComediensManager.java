package niamoro.comedy.db_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import niamoro.comedy.utilis.Comedien;

public class ComediensManager {

    private static final int VERSION_BDD = 2;
    private static final String NAME_DB = "afrimoov_crtv_comediens";

    private static final String TABLE_COMEDIEN = "comediens";

    private static final String id ="comedien_id";
    private static final String nom = "nom";
    private static final String image = "image";
    private static final String pays = "pays";
    private static final String followed = "followed";

    private SQLiteDatabase bdd;
    private ComedienHelper myHelper;
    private Context context;

    public ComediensManager(Context context) {
        myHelper = new ComedienHelper(context,NAME_DB,null,VERSION_BDD);
        this.context = context;
    }

    public void insertComedien(Comedien comedien){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nom, comedien.getNom());
        values.put(image, comedien.getImage());
        values.put(id, comedien.getComedien_id());
        values.put(pays, comedien.getPays());
        values.put(followed,(comedien.isFollowed()) ? 1 : 0);

        bdd.insert(TABLE_COMEDIEN,null,values);
        bdd.close();
    }

    public void updateComedien(Comedien comedien){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nom, comedien.getNom());
        values.put(image, comedien.getImage());
        values.put(pays, comedien.getPays());

        bdd.update(TABLE_COMEDIEN,values,id + " LIKE ?", new String[]{comedien.getComedien_id()});
        bdd.close();
    }

    public void updateComedien(String comedien,boolean follow){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(followed, (follow) ? 1 :0);
        bdd.update(TABLE_COMEDIEN,values,id + " LIKE ?", new String[]{comedien});
        bdd.close();
    }

    public List<Comedien> getAll(int nbr){
        List<Comedien> comediens = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Comedien comedien;
        Cursor cursor = bdd.query(TABLE_COMEDIEN, new String[]{"*"},null,null,null,
                null, null);
        while (cursor.moveToNext()){
            comedien = new Comedien();
            comedien.setComedien_id(cursor.getString(0));
            comedien.setImage(cursor.getString(2));
            comedien.setNom(cursor.getString(1));
            comedien.setPays(cursor.getString(3));
            comedien.setFollowed(cursor.getInt(4) == 1);
            Map<String, Integer> infos = new VideosManager(context).getComedienInfos(cursor.getString(0));
            comedien.setNbr_view(infos.get("views"));
            comedien.setNbr_videos(infos.get("total"));
            comediens.add(comedien);
        }
        cursor.close();
        bdd.close();
        Collections.sort(comediens);

        return (comediens.size() > nbr) ? comediens.subList(0,nbr) : comediens;
    }

    public List<Comedien> getFollowed(int nbr){
        List<Comedien> comediens = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Comedien comedien;
        Cursor cursor = bdd.query(TABLE_COMEDIEN, new String[]{"*"},null,null,null,
                null, followed + " DESC");
        while (cursor.moveToNext()){
            comedien = new Comedien();
            comedien.setComedien_id(cursor.getString(0));
            comedien.setImage(cursor.getString(2));
            comedien.setNom(cursor.getString(1));
            comedien.setPays(cursor.getString(3));
            comedien.setFollowed(cursor.getInt(4) == 1);
            Map<String, Integer> infos = new VideosManager(context).getComedienInfos(cursor.getString(0));
            comedien.setNbr_view(infos.get("views"));
            comedien.setNbr_videos(infos.get("total"));
            comediens.add(comedien);
        }
        cursor.close();
        bdd.close();
        Collections.sort(comediens);

        return (comediens.size() > nbr) ? comediens.subList(0,nbr) : comediens;
    }

    public Comedien getComedien(String comedien_id){
        Comedien comedien = null;
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_COMEDIEN, new String[]{"*"},
                id + " LIKE ?" ,
                new String[]{comedien_id},
                null, null, null);
        if (cursor != null && cursor.moveToNext()){
            comedien = new Comedien();
            comedien.setComedien_id(cursor.getString(0));
            comedien.setImage(cursor.getString(2));
            comedien.setNom(cursor.getString(1));
            comedien.setPays(cursor.getString(3));
            comedien.setFollowed(cursor.getInt(4) == 1);
            cursor.close();
        }

        bdd.close();

        return comedien;
    }

    public List<String> getLast_Id(){
        List<String> ids = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_COMEDIEN,new String[]{id},null,null,
                null,null,null);

        if (cursor !=null) {
            while (cursor.moveToNext()) {
                ids.add(cursor.getString(0));
            }
            cursor.close();
        }
        bdd.close();
        return ids;
    }

    public boolean verifyComedienId(String source_id){
        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_COMEDIEN,new String[]{id}, id + " LIKE ?", new String[]{source_id},
                null,null,null);

        if (cursor != null && cursor.moveToNext() && cursor.getString(0).equals(source_id)){

            cursor.close();

            return true;

        }

        bdd.close();
        return false;
    }
    public void deleteSource(String source_id){
        bdd = myHelper.getWritableDatabase();
        bdd.delete(TABLE_COMEDIEN, id + " LIKE ?", new String[]{source_id});
        bdd.close();
        new VideosManager(context).deleteVideos(source_id);
    }
}