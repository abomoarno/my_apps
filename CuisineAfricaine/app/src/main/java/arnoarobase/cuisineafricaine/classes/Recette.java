package arnoarobase.cuisineafricaine.classes;

import java.util.ArrayList;
import java.util.Arrays;

public class Recette {

    // PARAMETRES GEENERAUX

    private String id;
    private String name;
    private String description;
    private String illustration;
    private String video;
    private int note;
    private int reviews;
    private String cuisson;
    private String prix;
    private String diificulte;
    private String categories;
    private String date;
    private String pays;
    private int personnes;
    private int vues;
    private int favories;
    private int add_list;
    private String ingrediens;
    private String etapes;

    // PARAMETRES LIES A L'UTILISATEUR

    private boolean InFavourites;
    private boolean InMyList;

    public Recette(){}
    public Recette(String id, String titre) {
        this.id = id;
        this.name = titre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCuisson() {
        return cuisson;
    }

    public void setCuisson(String cuisson) {
        this.cuisson = cuisson;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDiificulte() {
        return diificulte;
    }

    public void setDiificulte(String diificulte) {
        this.diificulte = diificulte;
    }

    public int getPersonnes() {
        return personnes;
    }

    public void setPersonnes(int personnes) {
        this.personnes = personnes;
    }

    public String getIngrediens() {

        return this.ingrediens;
    }

    public void setIngrediens(String ingrediens) {
        this.ingrediens = ingrediens;
    }

    public String getEtapes() {

        return etapes;
    }

    public void setEtapes(String etapes) {
        this.etapes = etapes;
    }

    public boolean isInFavourites() {
        return InFavourites;
    }

    public void setInFavourites(boolean inFavourites) {
        this.InFavourites = inFavourites;
    }

    public boolean isInMyList() {
        return InMyList;
    }

    public void setInMyList(boolean inMyList) {
        this.InMyList = inMyList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public int getVues() {
        return vues;
    }

    public void setVues(int vues) {
        this.vues = vues;
    }

    public int getFavories() {
        return favories;
    }

    public void setFavories(int favories) {
        this.favories = favories;
    }

    public int getAdd_list() {
        return add_list;
    }

    public void setAdd_list(int add_list) {
        this.add_list = add_list;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}
