package afrimoov.tg.db_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import afrimoov.tg.utilis.Replay;
import afrimoov.tg.utilis.Source;

public class SourcesManager {

    private static final int VERSION_BDD = 1;
    private static final String NAME_DB = "afrimoov_crtv_sources";

    private static final String TABLE_SOURCE = "sources";

    private static final String id ="source_id";
    private static final String title = "title";
    private static final String image = "image";
    private static final String pays = "pays";

    private SQLiteDatabase bdd;
    private SourceHelper myHelper;
    private Context context;

    public SourcesManager(Context context) {
        myHelper = new SourceHelper(context,NAME_DB,null,VERSION_BDD);
        this.context = context;
    }

    public void insertSource(Source source){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title,source.getNom());
        values.put(image,source.getImage());
        values.put(id,source.getSource_id());
        values.put(pays,source.getPays());

        bdd.insert(TABLE_SOURCE,null,values);
        bdd.close();
    }

    public void updateSource(Source source){
        bdd = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title,source.getNom());
        values.put(image,source.getImage());
        values.put(id,source.getSource_id());
        values.put(pays,source.getPays());

        bdd.update(TABLE_SOURCE,values,id + " LIKE ?", new String[]{source.getSource_id()});
        bdd.close();
    }

    public List<Source> getAll(){
        List<Source> sources = new ArrayList<>();

        bdd = myHelper.getWritableDatabase();
        Source source;
        Cursor cursor = bdd.query(TABLE_SOURCE, new String[]{"*"},null,null,null,
                null, null);
        while (cursor.moveToNext()){
            source = new Source();
            source.setSource_id(cursor.getString(0));
            source.setImage(cursor.getString(2));
            source.setNom(cursor.getString(1));
            source.setPays(cursor.getString(3));
            Map<String, Integer> infos = new ReplaysManager(context).getSourceInfos(cursor.getString(0));
            source.setNbr_view(infos.get("views"));
            source.setNbr_replays(infos.get("total"));
            sources.add(source);
        }
        cursor.close();
        bdd.close();
        Collections.sort(sources);
        return sources;
    }

    public List<String> getLast_Id(){
        List<String> ids = new ArrayList<>();
        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_SOURCE,new String[]{id},null,null,
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

    public boolean verifySourceId(String source_id){
        bdd = myHelper.getWritableDatabase();

        Cursor cursor = bdd.query(TABLE_SOURCE,new String[]{id}, id + " LIKE ?", new String[]{source_id},
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
        bdd.delete(TABLE_SOURCE, id + " LIKE ?", new String[]{source_id});
        bdd.close();
    }
}