package afrimoov.ke.utilis;

public class Replay {

    /*
    *
    * cette classe représente l'implémentation
    * logique d'un replay
    *
    * */

    // Attributs

    private String title;
    private String description;
    private String link;
    private String plateforme;
    private String chaine;
    private String image;
    private String date;
    private int views;
    private String replay_id;
    private String duree;



    public Replay(String replay_id, String title) {
        this.title = title;
        this.replay_id = replay_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getPlateforme() {
        return plateforme;
    }

    public String getChaine() {
        return chaine;
    }


    public String getImage() {
        return image;
    }

    public int getViews() {
        return views;
    }

    public String getReplay_id() {
        return replay_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPlateforme(String plateforme) {
        this.plateforme = plateforme;
    }

    public void setChaine(String chaine) {
        this.chaine = chaine;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }
}
