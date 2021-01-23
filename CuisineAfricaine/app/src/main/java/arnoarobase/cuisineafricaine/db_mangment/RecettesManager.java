package arnoarobase.cuisineafricaine.db_mangment;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import arnoarobase.cuisineafricaine.classes.Recette;
import arnoarobase.cuisineafricaine.utils.Utils;

import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.ADD_LIST;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.CATEGORY;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.DATE;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.DB;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.DESCRIPTION;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.DIFFICULTE;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.FAVORIES;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.FAVORIS;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.ID;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.IMAGE;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.INGREDIENTS;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.MA_LISTE;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.NOTE;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.NUM_PERSONNES;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.PAYS;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.PREPARATION;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.PRIX;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.TABLE_RECETTES;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.TEMPS;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.TITRE;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.VERSION;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.VIDEO;
import static arnoarobase.cuisineafricaine.db_mangment.RecetteHelper.VIEWS;

public class RecettesManager {

    private RecetteHelper helper;
    private SQLiteDatabase db;

    private String pays;

    public RecettesManager(Context context){
        helper = new RecetteHelper(context,DB,null,VERSION);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        pays = preferences.getString(Utils.COUNTRY_ID,"all");
    }

    public void inserRecette(Recette recette){

        ContentValues values = new ContentValues();
        values.put(ID,recette.getId());
        values.put(TITRE,recette.getName());
        values.put(DESCRIPTION,recette.getDescription());
        values.put(IMAGE,recette.getIllustration());
        values.put(VIDEO,recette.getVideo());
        values.put(NOTE,recette.getNote());
        values.put(TEMPS,recette.getCuisson());
        values.put(PRIX,recette.getPrix());
        values.put(DIFFICULTE,recette.getDiificulte());
        values.put(CATEGORY,recette.getCategories());
        values.put(DATE,recette.getDate());
        values.put(NUM_PERSONNES,recette.getPersonnes());
        values.put(INGREDIENTS,recette.getIngrediens());
        values.put(PREPARATION,recette.getEtapes());
        values.put(FAVORIS,(recette.isInFavourites())?1:0);
        values.put(MA_LISTE,(recette.isInMyList())?1:0);
        values.put(PAYS,recette.getPays());
        values.put(VIEWS,recette.getVues());
        values.put(FAVORIES,recette.getFavories());
        values.put(ADD_LIST,recette.getAdd_list());
        db = helper.getWritableDatabase();
        db.insert(TABLE_RECETTES,null,values);

        db.close();


    }

    public void inserRecettes(List<Recette> recettes){

        ContentValues values;
        db = helper.getWritableDatabase();
        db.beginTransaction();

        for(Recette recette:recettes) {
            values = new ContentValues();
            values.put(ID, recette.getId());
            values.put(TITRE, recette.getName());
            values.put(DESCRIPTION, recette.getDescription());
            values.put(IMAGE, recette.getIllustration());
            values.put(VIDEO, recette.getVideo());
            values.put(NOTE, recette.getNote());
            values.put(TEMPS, recette.getCuisson());
            values.put(PRIX, recette.getPrix());
            values.put(DIFFICULTE, recette.getDiificulte());
            values.put(CATEGORY, recette.getCategories());
            values.put(DATE, recette.getDate());
            values.put(NUM_PERSONNES, recette.getPersonnes());
            values.put(INGREDIENTS, recette.getIngrediens());
            values.put(PREPARATION, recette.getEtapes());
            values.put(FAVORIS, (recette.isInFavourites()) ? 1 : 0);
            values.put(MA_LISTE, (recette.isInMyList()) ? 1 : 0);
            values.put(PAYS, recette.getPays());
            values.put(VIEWS, recette.getVues());
            values.put(FAVORIES, recette.getFavories());
            values.put(ADD_LIST, recette.getAdd_list());
            db = helper.getWritableDatabase();
            db.insert(TABLE_RECETTES, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void updateRecette(String id, Map<String,String> recette){
        ContentValues values = new ContentValues();
        for (String key:recette.keySet())
            values.put(key,recette.get(key));
        db = helper.getWritableDatabase();
        db.update(TABLE_RECETTES,values, ID +" LIKE ?",new String[]{id});

        db.close();
    }

    public void updateRecette(Recette recette){
        ContentValues values = new ContentValues();

        values.put(TITRE,recette.getName());
        values.put(DESCRIPTION,recette.getDescription());
        values.put(IMAGE,recette.getIllustration());
        values.put(VIDEO,recette.getVideo());
        values.put(NOTE,recette.getNote());
        values.put(TEMPS,recette.getCuisson());
        values.put(PRIX,recette.getPrix());
        values.put(DIFFICULTE,recette.getDiificulte());
        values.put(CATEGORY,recette.getCategories());
        values.put(NUM_PERSONNES,recette.getPersonnes());
        values.put(INGREDIENTS,recette.getIngrediens());
        values.put(PREPARATION,recette.getEtapes());
        values.put(PAYS,recette.getPays());
        values.put(VIEWS,recette.getVues());
        values.put(FAVORIES,recette.getFavories());
        values.put(ADD_LIST,recette.getAdd_list());

        db.update(TABLE_RECETTES,values, ID +" LIKE ?",new String[]{recette.getId()});
        db.close();
    }

    public void updateRecettes(List<Recette> recettes){
        ContentValues values;

        db = helper.getWritableDatabase();
        db.beginTransaction();
        for(Recette recette:recettes) {
            values = new ContentValues();
            values.put(TITRE, recette.getName());
            values.put(DESCRIPTION, recette.getDescription());
            values.put(IMAGE, recette.getIllustration());
            values.put(VIDEO, recette.getVideo());
            values.put(NOTE, recette.getNote());
            values.put(TEMPS, recette.getCuisson());
            values.put(PRIX, recette.getPrix());
            values.put(DIFFICULTE, recette.getDiificulte());
            values.put(CATEGORY, recette.getCategories());
            values.put(NUM_PERSONNES, recette.getPersonnes());
            values.put(INGREDIENTS, recette.getIngrediens());
            values.put(PREPARATION, recette.getEtapes());
            values.put(PAYS, recette.getPays());
            values.put(VIEWS, recette.getVues());
            values.put(FAVORIES, recette.getFavories());
            values.put(ADD_LIST, recette.getAdd_list());
            db.update(TABLE_RECETTES, values, ID + " LIKE ?", new String[]{recette.getId()});
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public Recette getRecette(String recette_id){
        Recette recette = null;

        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_RECETTES,null,ID + " LIKE ?", new String[]{recette_id},
                null, null, null,null);
        if (cursor !=null && cursor.moveToFirst()){

            recette = new Recette(cursor.getString(0), cursor.getString(1));

            recette.setDescription(cursor.getString(2));
            recette.setIllustration(cursor.getString(3));
            recette.setVideo(cursor.getString(4));
            recette.setNote(cursor.getInt(5));
            recette.setCuisson(cursor.getString(6));
            recette.setPrix(cursor.getString(7));
            recette.setDiificulte(cursor.getString(8));
            recette.setCategories(cursor.getString(9));
            recette.setDate(cursor.getString(10));
            recette.setPersonnes(cursor.getInt(11));
            recette.setIngrediens(cursor.getString(12));
            recette.setEtapes(cursor.getString(13));
            recette.setInFavourites(cursor.getInt(14) == 1);
            recette.setInMyList(cursor.getInt(15) == 1);
            recette.setPays(cursor.getString(16));
            recette.setVues(cursor.getInt(17));
            recette.setFavories(cursor.getInt(18));
            recette.setAdd_list(cursor.getInt(19));

            cursor.close();
        }

        db.close();
        return recette;
    }

    public List<Recette> getTeasers(int number){
        List<Recette> recettes = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_RECETTES,null,
                CATEGORY + " LIKE ?",
                new String[]{"teaser"}, null, null,null);

        if (cursor != null){
            Recette recette;
            while (cursor.moveToNext()){

                recette = new Recette(cursor.getString(0), cursor.getString(1));

                recette.setDescription(cursor.getString(2));
                recette.setIllustration(cursor.getString(3));
                recette.setVideo(cursor.getString(4));
                recette.setNote(cursor.getInt(5));
                recette.setCuisson(cursor.getString(6));
                recette.setPrix(cursor.getString(7));
                recette.setDiificulte(cursor.getString(8));
                recette.setCategories(cursor.getString(9));
                recette.setDate(cursor.getString(10));
                recette.setPersonnes(cursor.getInt(11));
                recette.setIngrediens(cursor.getString(12));
                recette.setEtapes(cursor.getString(13));
                recette.setInFavourites(cursor.getInt(14) == 1);
                recette.setInMyList(cursor.getInt(15) == 1);
                recette.setPays(cursor.getString(16));
                recette.setVues(cursor.getInt(17));
                recette.setFavories(cursor.getInt(18));
                recette.setAdd_list(cursor.getInt(19));

                recettes.add(recette);
            }

            cursor.close();

        }

        db.close();
        return (recettes.size()>number) ? recettes.subList(0,number):recettes;
    }

    public List<Recette> getRecents(int number){
        List<Recette> recettes = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_RECETTES,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor != null){
            Recette recette;
            while (cursor.moveToNext()){

                recette = new Recette(cursor.getString(0), cursor.getString(1));

                recette.setDescription(cursor.getString(2));
                recette.setIllustration(cursor.getString(3));
                recette.setVideo(cursor.getString(4));
                recette.setNote(cursor.getInt(5));
                recette.setCuisson(cursor.getString(6));
                recette.setPrix(cursor.getString(7));
                recette.setDiificulte(cursor.getString(8));
                recette.setCategories(cursor.getString(9));
                recette.setDate(cursor.getString(10));
                recette.setPersonnes(cursor.getInt(11));
                recette.setIngrediens(cursor.getString(12));
                recette.setEtapes(cursor.getString(13));
                recette.setInFavourites(cursor.getInt(14) == 1);
                recette.setInMyList(cursor.getInt(15) == 1);
                recette.setPays(cursor.getString(16));
                recette.setVues(cursor.getInt(17));
                recette.setFavories(cursor.getInt(18));
                recette.setAdd_list(cursor.getInt(19));

                recettes.add(recette);
            }

            cursor.close();

        }

        db.close();
        return (recettes.size()>number) ? recettes.subList(0,number):recettes;
    }

    public List<Recette> getPopulaires(int number){
        List<Recette> recettes = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_RECETTES,
                null,
                PAYS + " LIKE ?",
                new String[]{pays},
                null,
                null,
                DATE + " DESC, " + VIEWS + " DESC");

        if (cursor != null){
            Recette recette;
            while (cursor.moveToNext()){

                recette = new Recette(cursor.getString(0), cursor.getString(1));

                recette.setDescription(cursor.getString(2));
                recette.setIllustration(cursor.getString(3));
                recette.setVideo(cursor.getString(4));
                recette.setNote(cursor.getInt(5));
                recette.setCuisson(cursor.getString(6));
                recette.setPrix(cursor.getString(7));
                recette.setDiificulte(cursor.getString(8));
                recette.setCategories(cursor.getString(9));
                recette.setDate(cursor.getString(10));
                recette.setPersonnes(cursor.getInt(11));
                recette.setIngrediens(cursor.getString(12));
                recette.setEtapes(cursor.getString(13));
                recette.setInFavourites(cursor.getInt(14) == 1);
                recette.setInMyList(cursor.getInt(15) == 1);
                recette.setPays(cursor.getString(16));
                recette.setVues(cursor.getInt(17));
                recette.setFavories(cursor.getInt(18));
                recette.setAdd_list(cursor.getInt(19));

                recettes.add(recette);
            }

            cursor.close();

        }

        db.close();
        return (recettes.size()>number) ? recettes.subList(0,number):recettes;
    }

    public List<Recette> getWithVideo(int number){
        List<Recette> recettes = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_RECETTES,
                null,
                PAYS + " LIKE ? AND " + VIDEO + " NOT LIKE ?",
                new String[]{pays," "},
                null,
                null,
                DATE + " DESC, " + VIEWS + " DESC");

        if (cursor != null){
            Recette recette;
            while (cursor.moveToNext()){

                recette = new Recette(cursor.getString(0), cursor.getString(1));

                recette.setDescription(cursor.getString(2));
                recette.setIllustration(cursor.getString(3));
                recette.setVideo(cursor.getString(4));
                recette.setNote(cursor.getInt(5));
                recette.setCuisson(cursor.getString(6));
                recette.setPrix(cursor.getString(7));
                recette.setDiificulte(cursor.getString(8));
                recette.setCategories(cursor.getString(9));
                recette.setDate(cursor.getString(10));
                recette.setPersonnes(cursor.getInt(11));
                recette.setIngrediens(cursor.getString(12));
                recette.setEtapes(cursor.getString(13));
                recette.setInFavourites(cursor.getInt(14) == 1);
                recette.setInMyList(cursor.getInt(15) == 1);
                recette.setPays(cursor.getString(16));
                recette.setVues(cursor.getInt(17));
                recette.setFavories(cursor.getInt(18));
                recette.setAdd_list(cursor.getInt(19));

                recettes.add(recette);
            }

            cursor.close();

        }

        db.close();
        return (recettes.size()>number) ? recettes.subList(0,number):recettes;
    }

    public List<Recette> getClassiques(int number){
        List<Recette> recettes = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_RECETTES,
                null,
                PAYS + " LIKE ? AND " + CATEGORY + " LIKE ?",
                new String[]{pays,"classique"},
                null,
                null,
                DATE + " DESC, " + VIEWS + " DESC");

        if (cursor != null){
            Recette recette;
            while (cursor.moveToNext()){

                recette = new Recette(cursor.getString(0), cursor.getString(1));

                recette.setDescription(cursor.getString(2));
                recette.setIllustration(cursor.getString(3));
                recette.setVideo(cursor.getString(4));
                recette.setNote(cursor.getInt(5));
                recette.setCuisson(cursor.getString(6));
                recette.setPrix(cursor.getString(7));
                recette.setDiificulte(cursor.getString(8));
                recette.setCategories(cursor.getString(9));
                recette.setDate(cursor.getString(10));
                recette.setPersonnes(cursor.getInt(11));
                recette.setIngrediens(cursor.getString(12));
                recette.setEtapes(cursor.getString(13));
                recette.setInFavourites(cursor.getInt(14) == 1);
                recette.setInMyList(cursor.getInt(15) == 1);
                recette.setPays(cursor.getString(16));
                recette.setVues(cursor.getInt(17));
                recette.setFavories(cursor.getInt(18));
                recette.setAdd_list(cursor.getInt(19));

                recettes.add(recette);
            }

            cursor.close();

        }

        db.close();
        return (recettes.size()>number) ? recettes.subList(0,number):recettes;
    }

    public List<Recette> getFavoris(int number){
        List<Recette> recettes = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_RECETTES,
                null,
                PAYS + " LIKE ? AND " + FAVORIS + " LIKE ?",
                new String[]{pays,"1"},
                null,
                null,
                null);

        if (cursor != null){
            Recette recette;
            while (cursor.moveToNext()){

                recette = new Recette(cursor.getString(0), cursor.getString(1));

                recette.setDescription(cursor.getString(2));
                recette.setIllustration(cursor.getString(3));
                recette.setVideo(cursor.getString(4));
                recette.setNote(cursor.getInt(5));
                recette.setCuisson(cursor.getString(6));
                recette.setPrix(cursor.getString(7));
                recette.setDiificulte(cursor.getString(8));
                recette.setCategories(cursor.getString(9));
                recette.setDate(cursor.getString(10));
                recette.setPersonnes(cursor.getInt(11));
                recette.setIngrediens(cursor.getString(12));
                recette.setEtapes(cursor.getString(13));
                recette.setInFavourites(cursor.getInt(14) == 1);
                recette.setInMyList(cursor.getInt(15) == 1);
                recette.setPays(cursor.getString(16));
                recette.setVues(cursor.getInt(17));
                recette.setFavories(cursor.getInt(18));
                recette.setAdd_list(cursor.getInt(19));

                recettes.add(recette);
            }

            cursor.close();

        }

        db.close();
        return (recettes.size()>number) ? recettes.subList(0,number):recettes;
    }

    public void delete(String recette_id){
        db = helper.getWritableDatabase();
        db.delete(TABLE_RECETTES,ID + " LIKE ?", new String[]{recette_id});
        db.close();
    }

    public void delete(List<String> ids){
        db = helper.getWritableDatabase();
        db.beginTransaction();
        for (String recette_id:ids)
            db.delete(TABLE_RECETTES,ID + " LIKE ?", new String[]{recette_id});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public List<String> getAllIds(){

        List<String> ids = new ArrayList<>();
        db = helper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_RECETTES,
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

    public boolean verifyIfExist(String recette_id){
        return getAllIds().contains(recette_id);
    }
}