package niamoro.annonces.utils;

import java.util.Date;

import static niamoro.annonces.utils.Utils.TYPE_LOCATION;
import static niamoro.annonces.utils.Utils.TYPE_MAISON;

public class Recherche {
    private String id;
    private String pays;
    private String ville;
    private String typeBien;
    private String typeOperation;
    private long date;
    private int status;

    public Recherche(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        String titre = (typeBien.equals(TYPE_MAISON)) ? "Une maison à " : "Un Terrain à ";
        titre += (typeOperation.equals(TYPE_LOCATION)) ? "louer à " : "acheter à ";
        titre += ville;
        return titre;
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

    public String getTypeBien() {
        return typeBien;
    }

    public void setTypeBien(String typeBien) {
        this.typeBien = typeBien;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInterval(){
        String interval = "Il y a ";

        long now = new Date().getTime();
        long difference = (now-date)/1000; // Interval en secondes

        if (difference <90)
            interval += "1 min";
        else if ((difference/60) <60)
            interval += (difference/60) + " min";
        else if ((difference/3600) <24)
            interval += (difference/3600) + " h";
        else if ((difference/86400) <30)
            interval += (difference/86400) + " j";
        else if ((difference/259200) <12)
            interval += (difference/259200) + " mois";
        else
            interval += (difference/3110400) + " an(s)";
        return interval;
    }

    public String getNotifMessage(){
        String titre = (typeBien.equals(TYPE_MAISON)) ? "Maison à " : "Terrain à ";
        titre += (typeOperation.equals(TYPE_LOCATION)) ? "louer (" : "acheter (";
        titre += ville + ")";
        return titre;
    }

    public String getTopic() {
        String textVille = ville
                .replaceAll("[é,è,ê]","e")
                .replaceAll("[à,â]","a")
                .replaceAll("[ù,û,ü]","u")
                .replaceAll("[ô,ö]","o")
                .replaceAll("[î,ï]","i");

        return pays+textVille+typeBien+typeOperation;
    }
}