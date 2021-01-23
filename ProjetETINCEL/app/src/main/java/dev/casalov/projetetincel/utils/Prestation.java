package dev.casalov.projetetincel.utils;

public class Prestation {
    private String prestation_id;
    private String devis_id;
    private String nom;
    private int quantite;
    private int prix;

    public Prestation(String prestation_id, String nom) {
        this.prestation_id = prestation_id;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrestation_id() {
        return prestation_id;
    }

    public String getDevis_id() {
        return devis_id;
    }

    public void setDevis_id(String devis_id) {
        this.devis_id = devis_id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}
