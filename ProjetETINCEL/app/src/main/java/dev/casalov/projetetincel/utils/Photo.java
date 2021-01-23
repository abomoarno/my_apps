package dev.casalov.projetetincel.utils;

public class Photo {
    private String lien;
    private String gamme;
    private String status;
    private String nom;

    public Photo(String gamme, String lien) {
        this.gamme = gamme;
        this.lien = lien;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getGamme() {
        return gamme;
    }

    public void setGamme(String gamme) {
        this.gamme = gamme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
