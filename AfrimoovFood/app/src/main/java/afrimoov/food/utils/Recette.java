package afrimoov.food.utils;

public class Recette {

    private String id;
    private String name;
    private String description;
    private String categories;
    private String cuisson;
    private long date;
    private String difficulte;
    private String etapes;
    private int favories;
    private int add_list;
    private String illustration;
    private String ingrediens;
    private int reviews;
    private int note;
    private String pays;
    private int personnes;
    private String prix;
    private String video;
    private String vues;

    private boolean IsInMyList;
    private boolean IsMyFavourite;

    public Recette (){ }

    public Recette(String id, String name){

        this.id = id;
        this.name = name;

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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCuisson() {
        return cuisson;
    }

    public void setCuisson(String cuisson) {
        this.cuisson = cuisson;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public String getEtapes() {
        return etapes;
    }

    public void setEtapes(String etapes) {
        this.etapes = etapes;
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

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getIngrediens() {
        return ingrediens;
    }

    public void setIngrediens(String ingrediens) {
        this.ingrediens = ingrediens;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public int getPersonnes() {
        return personnes;
    }

    public void setPersonnes(int personnes) {
        this.personnes = personnes;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVues() {
        return vues;
    }

    public void setVues(String vues) {
        this.vues = vues;
    }

    public boolean isInMyList() {
        return IsInMyList;
    }

    public void setInMyList(boolean inMyList) {
        IsInMyList = inMyList;
    }

    public boolean isMyFavourite() {
        return IsMyFavourite;
    }

    public void setMyFavourite(boolean myFavourite) {
        IsMyFavourite = myFavourite;
    }
}
