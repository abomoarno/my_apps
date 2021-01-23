package afrimoov.ci.utilis;

public class Replay {

    /*
    *
    * cette classe représente l'implémentation
    * logique d'un replay
    *
    * */

    // Attributs

    private String titre;
    private String description;
    private String link;
    private String plateforme;
    private String channel;
    private String image;
    private String date;
    private int vues;
    private String replay_id;
    private String duration;



    public Replay(){}

    public Replay(String replay_id, String title) {
        this.titre = title;
        this.replay_id = replay_id;
    }

    public void setReplay_id(String replay_id) {
        this.replay_id = replay_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitre() {
        return titre;
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

    public String getChannel() {
        return channel;
    }


    public String getImage() {
        return image;
    }

    public int getVues() {
        return vues;
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

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setVues(int vues) {
        this.vues = vues;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
