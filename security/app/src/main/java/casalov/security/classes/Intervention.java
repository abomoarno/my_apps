package casalov.security.classes;

public class Intervention {

    private String statut;
    private String motif;
    private String date;
    private String lieu;
    private String id;
    private double prix;

    public Intervention(String id, String motif) {
        this.motif = motif;
        this.id = id;
    }

    public String getStatut() {
        return statut;
    }

    public String getMotif() {
        return motif;
    }

    public String getDate() {
        return date;
    }

    public String getLieu() {
        return lieu;
    }

    public String getId() {
        return id;
    }

    public double getPrix() {
        return prix;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
