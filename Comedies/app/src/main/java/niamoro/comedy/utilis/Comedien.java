package niamoro.comedy.utilis;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class Comedien implements Comparable<Comedien>{

    private String comedien_id;
    private String image;
    private String nom;
    private String pays;
    private int nbr_videos;
    private int nbr_view;

    private boolean followed;


    public String getComedien_id() {
        return comedien_id;
    }

    public void setComedien_id(String comedien_id) {
        this.comedien_id = comedien_id;
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

    public int getNbr_videos() {
        return nbr_videos;
    }

    public void setNbr_videos(int nbr_videos) {
        this.nbr_videos = nbr_videos;
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
    public int compareTo(@NonNull Comedien comedien) {
        if (this.getNbr_view()> comedien.getNbr_view())
            return -1;
        else if (this.getNbr_view()< comedien.getNbr_view())
            return 1;
        else{
           if (this.getNbr_videos()> comedien.getNbr_videos())
               return -1;
           else if (this.getNbr_videos()< comedien.getNbr_videos())
               return 1;
        }
        return 0;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
        subScription();
    }

    private void subScription(){
        if (this.followed) {
            FirebaseMessaging.getInstance().subscribeToTopic("niamoro_comedy_" + this.comedien_id);
        }
        else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("niamoro_comedy_" + this.comedien_id);
        }
    }
}