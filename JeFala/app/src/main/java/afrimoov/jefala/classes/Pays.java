package afrimoov.jefala.classes;

public class Pays {

    private String iso;
    private String nom;
    private String devise;
    private String logo;

    public Pays(String iso, String nom) {
        this.iso = iso;
        this.nom = nom;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
