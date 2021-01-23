package afrimoov.nigeria.utilis;

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
    private String plateforme;
    private String image;
    private String langue;
    private String categorie;

    private int vues;


    public Live_Tv(String tv_id, String nom) {
        this.tv_id = tv_id;
        this.nom = nom;
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

    public String getPlateforme() {
        return plateforme;
    }

    public String getImage() {
        return image;
    }

    public String getLangue() {
        return langue;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getVues() {
        return vues;
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

    public void setPlateforme(String plateforme) {
        this.plateforme = plateforme;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setVues(int vues) {
        this.vues = vues;
    }
}
