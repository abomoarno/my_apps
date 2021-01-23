package dev.casalov.projetetincel.utils;

public class Rapport {
    private String rapport_id;
    private String type;
    private String date_debut;
    private String date_fin;
    private String contact_sur_site;
    private String charge_affaires;
    private String charge_travaux;
    private String client_id;
    private String redacteur;
    private String approbateur;
    private String verificateur;
    private String techniciens;
    private String nom;

    public Rapport(String rapport_id, String nom) {
        this.rapport_id = rapport_id;
        this.nom = nom;
    }

    public String getRapport_id() {
        return rapport_id;
    }

    public void setRapport_id(String rapport_id) {
        this.rapport_id = rapport_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getContact_sur_site() {
        return contact_sur_site;
    }

    public void setContact_sur_site(String contact_sur_site) {
        this.contact_sur_site = contact_sur_site;
    }

    public String getCharge_affaires() {
        return charge_affaires;
    }

    public void setCharge_affaires(String charge_affaires) {
        this.charge_affaires = charge_affaires;
    }

    public String getCharge_travaux() {
        return charge_travaux;
    }

    public void setCharge_travaux(String charge_travaux) {
        this.charge_travaux = charge_travaux;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRedacteur() {
        return redacteur;
    }

    public void setRedacteur(String redacteur) {
        this.redacteur = redacteur;
    }

    public String getApprobateur() {
        return approbateur;
    }

    public void setApprobateur(String approbateur) {
        this.approbateur = approbateur;
    }

    public String getVerificateur() {
        return verificateur;
    }

    public void setVerificateur(String verificateur) {
        this.verificateur = verificateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTechniciens() {
        return techniciens;
    }

    public void setTechniciens(String techniciens) {
        this.techniciens = techniciens;
    }
}
