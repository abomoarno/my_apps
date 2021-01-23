package fallapro.landcrowdy.classes;

public abstract class Annonce {

    private int id;
    private String titre;
    private String description;
    private String lien;
    private String image;
    private String pays;
    private String ville;
    private String type;
    private String date;
    private int affichages;
    private int liked;
    private String quartier;
    private int superficie;
    private int prix;

    public Annonce(int id, String titre) {
        this.id = id;
        this.titre = titre;
        this.liked = 0;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getAffichages() {
        return affichages;
    }

    public void setAffichages(int affichages) {
        this.affichages = affichages;
    }


    public void doLike(){
        liked = 1;
    }
    public void doClick(){

        //TODO mise à jour de la base de donnée
    }
    public void doAfficher(){
        affichages ++;

    }

    public void doDisLike(){
        liked = 0;
    }

    public static int getLastId(){

        return 0;
    }

    public abstract String getTypeAnnonce();
}
