package afrimoov.ci.utilis;

public class Live_Tv {

    /*
    *
    * Cette classe est une représentation
    * logique d'une chaine de télévidion
    *
    * */

    private String tv_id;
    private String nom;
    private String description;
    private String pays;
    private String lien;
    private String platefome;
    private String image;
    private String langue;
    private String categories;

    private int live_vues;

    public Live_Tv(){}
    public Live_Tv(String tv_id, String nom) {
        this.tv_id = tv_id;
        this.nom = nom;
    }

    public void setTv_id(String tv_id) {
        this.tv_id = tv_id;
    }

    public String getTv_id() {
        return tv_id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getPays() {
        return pays;
    }

    public String getLien() {
        return lien;
    }

    public String getPlatefome() {
        return platefome;
    }

    public String getImage() {
        return image;
    }

    public String getLangue() {
        return langue;
    }

    public String getCategories() {
        return categories;
    }

    public int getLive_vues() {
        return live_vues;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public void setPlatefome(String platefome) {
        this.platefome = platefome;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setLive_vues(int live_vues) {
        this.live_vues = live_vues;
    }
}
