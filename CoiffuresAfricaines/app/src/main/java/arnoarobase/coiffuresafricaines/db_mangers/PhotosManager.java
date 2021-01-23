package arnoarobase.coiffuresafricaines.db_mangers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import arnoarobase.coiffuresafricaines.calsses.Photo;

public class PhotosManager {

    private static String id = "id";
    private static String titre = "titre";
    private static String url = "url";
    private static String genre = "genre";
    private static String liked = "liked";
    private static String description = "description";
    private static String date = "date";
    private static String liks = "liks";

    private static String TABLE_PHOTOS = "table_photos";

    private PhotosHelper helper;
    private SQLiteDatabase db;

    private static String db_name = "afrimoov_photos";
    private static int version = 1;

    public PhotosManager(Context context){
        helper = new PhotosHelper(context,db_name,null, version);
    }

    public void insertPhoto(Photo photo){
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id, photo.getId());
        values.put(description, photo.getDescription());
        values.put(url, photo.getUrl());
        values.put(titre, photo.getTitre());
        values.put(genre, photo.getGenre());
        values.put(liked, photo.getLiked());
        values.put(liks, photo.getLiks());
        values.put(date, photo.getDate());

        db.insert(TABLE_PHOTOS,null,values);
        db.close();

    }

    public Photo getPhoto(String photo_id){
        Photo photo = null;

        db = helper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_PHOTOS,new String[]{"*"}, id + " LIKE ?", new String[]{photo_id},
                null,null,null,null);

        if (cursor != null && cursor.moveToNext()){
            photo = new Photo(cursor.getString(0),cursor.getString(1));
            photo.setUrl(cursor.getString(2));
            photo.setGenre(cursor.getString(3));
            photo.setDate(cursor.getString(4));
            photo.setLiked(cursor.getInt(5));
            photo.setDescription(cursor.getString(6));
            photo.setLiks(cursor.getInt(7));
            cursor.close();
        }

        return photo;
    }

    public List<Photo> getMostPopular(int nbr){
        List<Photo> photos = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PHOTOS,
                new String[]{"*"},
                null,
                null,
                null,
                null,
                liked + " DESC" );
        if (cursor != null){
            Photo photo;
            while (cursor.moveToNext()){
                photo = new Photo(cursor.getString(0),cursor.getString(1));
                photo.setUrl(cursor.getString(2));
                photo.setGenre(cursor.getString(3));
                photo.setDate(cursor.getString(4));
                photo.setLiked(cursor.getInt(5));
                photo.setDescription(cursor.getString(6));
                photo.setLiks(cursor.getInt(7));
                photos.add(photo);
            }
            cursor.close();
        }
        return (photos.size()>nbr)?photos.subList(0,nbr):photos;
    }

    public List<Photo> getMostRecents(int nbr){
        List<Photo> photos = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PHOTOS,
                new String[]{"*"},
                null,
                null,
                null,
                null,
                date + " DESC, " + liked + " DESC");
        if (cursor != null){
            Photo photo;
            while (cursor.moveToNext()){
                photo = new Photo(cursor.getString(0),cursor.getString(1));
                photo.setUrl(cursor.getString(2));
                photo.setGenre(cursor.getString(3));
                photo.setDate(cursor.getString(4));
                photo.setLiked(cursor.getInt(5));
                photo.setDescription(cursor.getString(6));
                photo.setLiks(cursor.getInt(7));
                photos.add(photo);
            }
            cursor.close();
        }
        return (photos.size()>nbr)?photos.subList(0,nbr):photos;
    }

    public List<Photo> getGenre(String gre,int nbr){
        List<Photo> photos = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PHOTOS,
                new String[]{"*"},
                genre + " LIKE ?",
                new String[]{gre},
                null,
                null,
                date + " DESC, " + liked + " DESC");
        if (cursor != null){
            Photo photo;
            while (cursor.moveToNext()){
                photo = new Photo(cursor.getString(0),cursor.getString(1));
                photo.setUrl(cursor.getString(2));
                photo.setGenre(cursor.getString(3));
                photo.setDate(cursor.getString(4));
                photo.setLiked(cursor.getInt(5));
                photo.setDescription(cursor.getString(6));
                photo.setLiks(cursor.getInt(7));
                photos.add(photo);
            }
            cursor.close();
        }
        return (photos.size()>nbr)?photos.subList(0,nbr):photos;
    }

    private List<Photo> getFromGrid(String photo_id){
        List<Photo> photos = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PHOTOS,
                new String[]{"*"},
                id + " NOT LIKE ?",
                new String[]{photo_id},
                null,
                null,
                date + " DESC, " + liked + " ASC" );
        if (cursor != null){
            Photo photo;
            while (cursor.moveToNext()){
                photo = new Photo(cursor.getString(0),cursor.getString(1));
                photo.setUrl(cursor.getString(2));
                photo.setGenre(cursor.getString(3));
                photo.setDate(cursor.getString(4));
                photo.setLiked(cursor.getInt(5));
                photo.setDescription(cursor.getString(6));
                photo.setLiks(cursor.getInt(7));
                photos.add(photo);
            }
            cursor.close();
        }
        return photos;
    }
    public List<Photo> getFromGrid(String photo_id, String gre){
        if (gre == null)
            return getFromGrid(photo_id);

        List<Photo> photos = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PHOTOS,
                new String[]{"*"},
                id + " NOT LIKE ? AND " + genre + " LIKE ?",
                new String[]{photo_id,gre},
                null,
                null,
                date + " DESC, " + liked + " ASC" );
        if (cursor != null){
            Photo photo;
            while (cursor.moveToNext()){
                photo = new Photo(cursor.getString(0),cursor.getString(1));
                photo.setUrl(cursor.getString(2));
                photo.setGenre(cursor.getString(3));
                photo.setDate(cursor.getString(4));
                photo.setLiked(cursor.getInt(5));
                photo.setDescription(cursor.getString(6));
                photo.setLiks(cursor.getInt(7));
                photos.add(photo);
            }
            cursor.close();
        }
        return photos;
    }

    public void updatePhoto(Photo photo){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(titre, photo.getTitre());
        values.put(liks, photo.getLiks());
        values.put(description, photo.getDescription());
        db.update(TABLE_PHOTOS,values,id + " LIKE ?", new String[]{photo.getId()});
        db.close();
    }
    public void updateLiked(Photo photo){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(liked, photo.getLiked());
        db.update(TABLE_PHOTOS,values,id + " LIKE ?", new String[]{photo.getId()});
        db.close();
    }

    public void deletePhoto(String photo_id){
        db = helper.getWritableDatabase();
        db.delete(TABLE_PHOTOS,id + " LIKE ?", new String[]{photo_id});
        db.close();
    }

    public List<String> getIds(){
        List<String> ids = new ArrayList<>();
        db = helper.getWritableDatabase();

        Cursor  cursor = db.query(TABLE_PHOTOS, new String[]{id},
                null,
                null,
                null,
                null,
                null);
        if (cursor != null){
            while (cursor.moveToNext()){
                ids.add(cursor.getString(0));
            }
            cursor.close();
        }
        return ids;
    }


}