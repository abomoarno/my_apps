package niamoro.comedy.db_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import niamoro.comedy.utilis.Video;

public class VideosManager {

    private static final int VERSION_BDD = 2;
    private static final String NAME_DB = "afrimoov_comediens_videos";

    private static final String TABLE_VIDEOS = "videos";

    private static final String id ="video_id";
    private static final String title = "title";
    private static final String description = "description";
    private static final String link = "link";
    private static final String image = "image";
    private static final String views = "views";
    private static final String month_views = "month_views";
    private static final String day_views = "day_views";
    private static final String week_views = "week_views";
    private static final String comedien = "comedien";
    private static final String duration = "duration";
    private static final String date = "date";

    private SQLiteDatabase bdd;
    private VideoHelper myHelper;

    public VideosManager(Context context) {
        myHelper = new VideoHelper(context,NAME_DB,null,VERSION_BDD);
    }

    public void insertVideo(Video video){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(title, video.getTitre());
        values.put(description, video.getDescription());
        values.put(link, video.getLink());
        values.put(image, video.getImage());
        values.put(views, video.getVues());
        values.put(id, video.getVideo_id());
        values.put(comedien, video.getComedien());
        values.put(duration, video.getDuration());
        values.put(date, video.getDate());
        values.put(day_views,video.getToday_views());
        values.put(month_views,video.getMonth_views());
        values.put(week_views,video.getWeek_views());

        bdd.insert(TABLE_VIDEOS,null,values);
        bdd.close();
    }
    public void insertVideos(List<Video> videos){

        bdd = myHelper.getWritableDatabase();
        bdd.beginTransaction();

        for (Video video:videos) {
            ContentValues values = new ContentValues();
            values.put(title, video.getTitre());
            values.put(description, video.getDescription());
            values.put(link, video.getLink());
            values.put(image, video.getImage());
            values.put(views, video.getVues());
            values.put(id, video.getVideo_id());
            values.put(comedien, video.getComedien());
            values.put(duration, video.getDuration());
            values.put(date, video.getDate());
            values.put(day_views,video.getToday_views());
            values.put(month_views,video.getMonth_views());
            values.put(week_views,video.getWeek_views());

            bdd.insert(TABLE_VIDEOS, null, values);
        }

        bdd.setTransactionSuccessful();
        bdd.endTransaction();
        bdd.close();
    }
    public Video getVideo(String replay_id){
        bdd = myHelper.getWritableDatabase();
        Cursor cursor = bdd.query(TABLE_VIDEOS, new String[]{"*"},id + " LIKE ?",new String[]{replay_id}, null,
                null, null);
        if (cursor.moveToNext()){
            Video video = new Video(cursor.getString(0),cursor.getString(1));
            video.setDescription(cursor.getString(2));
            video.setComedien(cursor.getString(3));
            video.setLink(cursor.getString(4));
            video.setImage(cursor.getString(5));
            video.setVues(cursor.getInt(6));
            video.setDuration(cursor.getString(7));
            video.setDate(cursor.getString(8));
            return video;
        }
        cursor.close();
        bdd.close();

        return null;
    }

    public List<Video> getMoreREcents(int nbr){
        List<Video> videos = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Video video;
        Cursor cursor = bdd.query(TABLE_VIDEOS, new String[]{"*"},null,null, date,
                null, date + " DESC");
        while (cursor.moveToNext() && videos.size()<50){
            video = new Video(cursor.getString(0),cursor.getString(1));
            video.setDescription(cursor.getString(2));
            video.setComedien(cursor.getString(3));
            video.setLink(cursor.getString(4));
            video.setImage(cursor.getString(5));
            video.setVues(cursor.getInt(6));
            video.setDuration(cursor.getString(7));
            video.setDate(cursor.getString(8));
            videos.add(video);
        }
        cursor.close();
        bdd.close();
        Collections.shuffle(videos);
        return (videos.size()>=nbr)? videos.subList(0,nbr): videos;
    }

    public List<Video> getMorePopular(int nbr,String delay){
        String comparator = "";
        switch (delay){
            case "week":
                comparator = week_views;
                break;
            case "month":
                comparator = month_views;
                break;
            case "day":
                comparator = day_views;
                break;
        }

        List<Video> videos = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Video video;
        Cursor cursor = bdd.query(TABLE_VIDEOS, new String[]{"*"},null,null, null,
                null, comparator + " DESC");
        while (cursor.moveToNext()){
            video = new Video(cursor.getString(0),cursor.getString(1));
            video.setDescription(cursor.getString(2));
            video.setComedien(cursor.getString(3));
            video.setLink(cursor.getString(4));
            video.setImage(cursor.getString(5));
            video.setVues(cursor.getInt(6));
            video.setDuration(cursor.getString(7));
            video.setDate(cursor.getString(8));
            videos.add(video);
        }
        cursor.close();
        bdd.close();
        return (videos.size()>=nbr)? videos.subList(0,nbr): videos;

    }

    public List<Video> getFromComedien(String comedien_id,String order){

        String orderBy = (order.equals("date") || order.equals("all")) ? date : views;
        List<Video> videos = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();
        Video video;
        Cursor cursor = bdd.query(TABLE_VIDEOS, new String[]{"*"}, comedien + " LIKE ?",new String[]{comedien_id},
                null, null, orderBy + " DESC");
        while (cursor.moveToNext()){
            video = new Video(cursor.getString(0),cursor.getString(1));
            video.setDescription(cursor.getString(2));
            video.setComedien(cursor.getString(3));
            video.setLink(cursor.getString(4));
            video.setImage(cursor.getString(5));
            video.setVues(cursor.getInt(6));
            video.setDuration(cursor.getString(7));
            video.setDate(cursor.getString(8));
            videos.add(video);
        }
        cursor.close();
        bdd.close();

        if (order.equals("all")) {
            Collections.shuffle(videos);
        }
        else if (videos.size() > 15){
            videos = videos.subList(0,15);
        }

        return videos;
    }

    public List<Video> getRandom(int nbr){
        List<Video> videos = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Video video;
        Cursor cursor = bdd.query(TABLE_VIDEOS, new String[]{"*"},null,null,null,
                null, views + " ASC");
        while (cursor.moveToNext() && videos.size()<25){
            video = new Video(cursor.getString(0),cursor.getString(1));
            video.setDescription(cursor.getString(2));
            video.setComedien(cursor.getString(3));
            video.setLink(cursor.getString(4));
            video.setImage(cursor.getString(5));
            video.setVues(cursor.getInt(6));
            video.setDuration(cursor.getString(7));
            video.setDate(cursor.getString(8));
            videos.add(video);
        }
        cursor.close();
        bdd.close();
        Collections.shuffle(videos);
        return (videos.size()>=nbr)? videos.subList(0,nbr): videos;
    }

    public void updateViews(Map<String,List<Integer>> videos){
        bdd = myHelper.getWritableDatabase();
        bdd.beginTransaction();
        for (String key:videos.keySet()) {
            List<Integer> video = videos.get(key);
            if (video == null)
                continue;
            ContentValues values = new ContentValues();
            values.put(VideosManager.views, video.get(0));
            values.put(VideosManager.day_views, video.get(1));
            values.put(VideosManager.week_views, video.get(2));
            values.put(VideosManager.month_views, video.get(3));
            bdd.update(TABLE_VIDEOS, values, id + " LIKE ?", new String[]{key});
        }
        bdd.setTransactionSuccessful();
        bdd.endTransaction();
        bdd.close();
    }
    public void updateViews(List<Video> videos){
        bdd = myHelper.getWritableDatabase();
        bdd.beginTransaction();
        for (Video video:videos) {
            ContentValues values = new ContentValues();
            values.put(VideosManager.views, video.getVues());
            values.put(VideosManager.day_views, video.getToday_views());
            values.put(VideosManager.week_views, video.getWeek_views());
            values.put(VideosManager.month_views, video.getMonth_views());
            bdd.update(TABLE_VIDEOS, values, id + " LIKE ?", new String[]{video.getVideo_id()});
        }
        bdd.setTransactionSuccessful();
        bdd.endTransaction();
        bdd.close();
    }

    public void updateViews(String replay_id, int day, int week, int month){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VideosManager.day_views,day);
        values.put(VideosManager.week_views,week);
        values.put(VideosManager.month_views,month);
        bdd.update(TABLE_VIDEOS,values,id + " LIKE ?",new String[]{replay_id});
        bdd.close();
    }

    public List<String> getAllIds(){
        List<String> ids = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_VIDEOS,new String[]{id},null,null,
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

    public boolean verifyVideoId(String video_id){
        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_VIDEOS,new String[]{id}, id + " LIKE ?", new String[]{video_id},
                null,null,null);

        if (cursor != null && cursor.moveToNext() && cursor.getString(0).equals(video_id)){

            cursor.close();

            return true;

        }
        bdd.close();
        return false;
    }

    public void deleteVideos(String comedien_id){
        bdd = myHelper.getWritableDatabase();
        bdd.delete(TABLE_VIDEOS, comedien + " LIKE ?", new String[]{comedien_id});
        bdd.close();
    }

    public void deleteVideos(List<String> videos){
        bdd = myHelper.getWritableDatabase();
        bdd.beginTransaction();
        for (String video_id:videos) {
            bdd.delete(TABLE_VIDEOS, comedien + " LIKE ?", new String[]{video_id});
        }
        bdd.setTransactionSuccessful();
        bdd.endTransaction();
        bdd.close();
    }

    public Map<String,Integer> getComedienInfos(String comedien_id){

        HashMap<String,Integer> infos = new HashMap<>();
        bdd = myHelper.getWritableDatabase();

        int nbr_views = 0;

        Cursor cursor = bdd.query(TABLE_VIDEOS,new String[]{views}, comedien + " LIKE ?",new String[]{comedien_id},
                null,null,null);

        if (cursor !=null) {
            while (cursor.moveToNext())
                nbr_views += cursor.getInt(0);
            infos.put("views",nbr_views);
            infos.put("total",cursor.getCount());
            cursor.close();
        }
        bdd.close();
        return infos;
    }
}