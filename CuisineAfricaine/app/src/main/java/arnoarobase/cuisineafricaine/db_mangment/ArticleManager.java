package arnoarobase.cuisineafricaine.db_mangment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import arnoarobase.cuisineafricaine.classes.Article;

import static arnoarobase.cuisineafricaine.db_mangment.ArticleHelper.DATE;
import static arnoarobase.cuisineafricaine.db_mangment.ArticleHelper.DB;
import static arnoarobase.cuisineafricaine.db_mangment.ArticleHelper.DESCRIPTION;
import static arnoarobase.cuisineafricaine.db_mangment.ArticleHelper.ID;
import static arnoarobase.cuisineafricaine.db_mangment.ArticleHelper.IMAGE;
import static arnoarobase.cuisineafricaine.db_mangment.ArticleHelper.TABLE_ARTICLES;
import static arnoarobase.cuisineafricaine.db_mangment.ArticleHelper.TITRE;
import static arnoarobase.cuisineafricaine.db_mangment.ArticleHelper.URL;
import static arnoarobase.cuisineafricaine.db_mangment.ArticleHelper.VERSION;
import static arnoarobase.cuisineafricaine.db_mangment.ArticleHelper.VIEWS;

public class ArticleManager {

    private ArticleHelper helper;
    private SQLiteDatabase db;

    public ArticleManager(Context context){
        helper = new ArticleHelper(context,DB,null,VERSION);
    }

    public void insertArticle(Article article){

        ContentValues values = new ContentValues();
        
        values.put(ID,article.getId());
        values.put(TITRE,article.getTitre());
        values.put(DESCRIPTION,article.getDescription());
        values.put(IMAGE,article.getImage());
        values.put(URL,article.getUrl());
        values.put(VIEWS,article.getViews());
        values.put(DATE,article.getDate());
        
        db = helper.getWritableDatabase();
        db.insert(TABLE_ARTICLES,null,values);

        db.close();


    }

    public void updateArticle(String id, Map<String,String> article){
        ContentValues values = new ContentValues();
        for (String key:article.keySet())
            values.put(key,article.get(key));
        db = helper.getWritableDatabase();
        db.update(TABLE_ARTICLES,values, ID +" LIKE ?",new String[]{id});

        db.close();
    }

    public void updateArticle(Article article){
        ContentValues values = new ContentValues();

        values.put(TITRE,article.getTitre());
        values.put(DESCRIPTION,article.getDescription());
        values.put(IMAGE,article.getImage());
        values.put(URL,article.getUrl());
        values.put(VIEWS,article.getViews());
        values.put(DATE,article.getDate());

        db.update(TABLE_ARTICLES,values, ID +" LIKE ?",new String[]{article.getId()});
        db.close();
    }

    public Article getArticle(String article_id){
        Article article = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_ARTICLES,null,ID + " LIKE ?", new String[]{article_id},
                null, null, null,null);
        if (cursor !=null && cursor.moveToFirst()){

            article = new Article(cursor.getString(0), cursor.getString(1));

            article.setDescription(cursor.getString(2));
            article.setImage(cursor.getString(3));
            article.setDate(cursor.getString(4));
            article.setUrl(cursor.getString(5));
            article.setViews(cursor.getInt(6));
            
            cursor.close();
        }

        db.close();
        return article;
    }
    
    public List<Article> getArticles(){
        List<Article> articles = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_ARTICLES,
                null,
                null,
                null,
                null,
                null,
                VIEWS + " DESC, " + DATE + " DESC" );

        if (cursor != null){
            Article article;
            while (cursor.moveToNext()){

                article = new Article(cursor.getString(0), cursor.getString(1));

                article.setDescription(cursor.getString(2));
                article.setImage(cursor.getString(3));
                article.setDate(cursor.getString(4));
                article.setUrl(cursor.getString(5));
                article.setViews(cursor.getInt(6));

                articles.add(article);
            }

            cursor.close();

        }

        db.close();
        return articles;
    }
    
    public void delete(String article_id){
        db = helper.getWritableDatabase();
        db.delete(TABLE_ARTICLES,ID + " LIKE ?", new String[]{article_id});
        db.close();
    }

    public List<String> getAllIds(){

        List<String> ids = new ArrayList<>();
        db = helper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_ARTICLES,
                new String[]{ID},
                null,
                null,
                null,
                null,
                null);

        if (cursor != null){
            while (cursor.moveToNext())
                ids.add(cursor.getString(0));
            cursor.close();
        }
        db.close();
        return ids;
    }

    public boolean verifyIfExist(String article_id){
        return getAllIds().contains(article_id);
    }
}