package niamoro.annonces.utils;

public class Pays {

    private String code;
    private String nom;

    public Pays(String code, String nom) {
        this.code = code;
        this.nom = nom;
    }

    public Pays() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
