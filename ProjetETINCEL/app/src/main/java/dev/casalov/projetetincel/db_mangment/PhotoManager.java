package dev.casalov.projetetincel.db_mangment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dev.casalov.projetetincel.utils.Photo;

public class PhotoManager {

    private static String DB_NAME = "photos";
    private static int VERSION = 1;

    private static final String LIEN = "lien";
    private static final String titre = "titre";
    private static final String GAMME = "gamme";
    private static final String STATUS = "status";


    private static String TABLE_PHOTOS = "photos";

    private PhotoHelper helper;
    private SQLiteDatabase db;

    public PhotoManager(Context context){
        helper = new PhotoHelper(context, DB_NAME,null,VERSION);
    }

    public void insertPhoto(Photo photo){

        try {

            ContentValues values = new ContentValues();
            values.put(LIEN, photo.getLien());
            values.put(titre, photo.getNom());
            values.put(GAMME, photo.getGamme());
            values.put(STATUS, photo.getStatus());
            db = helper.getWritableDatabase();
            db.insert(TABLE_PHOTOS, null, values);
            db.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void updateDevis(Photo photo){

        try {

            ContentValues values = new ContentValues();
            values.put(STATUS, photo.getStatus());
            db = helper.getWritableDatabase();
            db.update(TABLE_PHOTOS,values, LIEN + " LIKE ?", new String[]{photo.getLien()});
            db.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<Photo> getFromGamme(String gamme_id){

        List<Photo> devisList = new ArrayList<>();
        Photo photo = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PHOTOS,new String[]{"*"}, GAMME + " LIKE ?",
                new String[]{gamme_id}, null, null,null);

        if (cursor !=  null){
            while (cursor.moveToNext()){
                photo = new Photo(cursor.getString(2), cursor.getString(0));
                photo.setNom(cursor.getString(1));
                photo.setStatus(cursor.getString(3));
                devisList.add(photo);
            }
            cursor.close();
        }
        db.close();
        return devisList;
    }

    public void deletePhoto(String maintenance){
        db = helper.getWritableDatabase();
        db.delete(TABLE_PHOTOS, GAMME + " LIKE ?", new String[]{maintenance});
    }
}
