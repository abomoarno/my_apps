package niamoro.comedy.utilis;

public class Video {

    /*
    *
    * cette classe représente l'implémentation
    * logique d'une video
    *
    * */

    // Attributs

    private String titre;
    private String description;
    private String link;
    private String comedien;
    private String image;
    private String date;
    private int vues;
    private int month_views;
    private int today_views;
    private int week_views;
    private String video_id;
    private String duration;


    public Video(){}
    public Video(String video_id, String title) {
        this.titre = title;
        this.video_id = video_id;
    }

    public Video(String video_id) {
        this.video_id = video_id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getMonth_views() {
        return month_views;
    }

    public void setMonth_views(int month_views) {
        this.month_views = month_views;
    }

    public int getToday_views() {
        return today_views;
    }

    public void setToday_views(int today_views) {
        this.today_views = today_views;
    }

    public int getWeek_views() {
        return week_views;
    }

    public void setWeek_views(int week_views) {
        this.week_views = week_views;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
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

    public String getComedien() {
        return comedien;
    }

    public String getImage() {
        return image;
    }

    public int getVues() {
        return vues;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setComedien(String comedien) {
        this.comedien = comedien;
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
