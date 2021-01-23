package afrimoov.tg.utilis;

import android.support.annotation.NonNull;

public class Source implements Comparable<Source>{

    private String source_id;
    private String image;
    private String nom;
    private String pays;
    private int nbr_replays;
    private int nbr_view;


    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbr_replays() {
        return nbr_replays;
    }

    public void setNbr_replays(int nbr_replays) {
        this.nbr_replays = nbr_replays;
    }

    public int getNbr_view() {
        return nbr_view;
    }

    public void setNbr_view(int nbr_view) {
        this.nbr_view = nbr_view;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public int compareTo(@NonNull Source source) {
        if (this.getNbr_view()>source.getNbr_view())
            return -1;
        else if (this.getNbr_view()<source.getNbr_view())
            return 1;
        else{
           if (this.getNbr_replays()>source.getNbr_replays())
               return -1;
           else if (this.getNbr_replays()<source.getNbr_replays())
               return 1;
        }
        return 0;
    }
}
