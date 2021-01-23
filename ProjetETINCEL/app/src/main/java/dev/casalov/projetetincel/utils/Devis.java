package dev.casalov.projetetincel.utils;

public class Devis {
    private String devis_id;
    private String date_document;
    private String date_realisation;
    private String nom;
    private String ville;
    private String redacteur;
    private String redacteur_poste;

    private String client_id;
    private String client_mail;
    private String client_nom;
    private String client_mobile;
    private String client_fixe;
    private String client_adresse;

    private String charges_client;
    private String charges_etincel;
    private String client_entreprise;

    public Devis(String devis_id, String nom) {
        this.devis_id = devis_id;
        this.nom = nom;
    }

    public String getDevis_id() {
        return devis_id;
    }

    public void setDevis_id(String devis_id) {
        this.devis_id = devis_id;
    }

    public String getDate_document() {
        return date_document;
    }

    public void setDate_document(String date_document) {
        this.date_document = date_document;
    }

    public String getDate_realisation() {
        return date_realisation;
    }

    public void setDate_realisation(String date_realisation) {
        this.date_realisation = date_realisation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRedacteur() {
        return redacteur;
    }

    public void setRedacteur(String redacteur) {
        String[] redact = redacteur.split(";");
        this.redacteur = redact[0];
        if (redact.length>1)
            this.redacteur_poste = redact[1];
    }

    public String getRedacteur_poste() {
        return redacteur_poste;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {

        String[] client = client_id.split(";");
        this.client_id = client[0];
        this.client_nom = client[1];
        this.client_mobile = client[2];
        this.client_fixe = client[3];
        this.client_mail = client[4];
        this.client_adresse = client[5];
        this.client_entreprise = client[6];
    }

    public String getClient_mail() {
        return client_mail;
    }

    public String getClient_nom() {
        return client_nom;
    }

    public String getClient_mobile() {
        return client_mobile;
    }

    public String getClient_fixe() {
        return client_fixe;
    }

    public String getClient_adresse() {
        return client_adresse;
    }

    public String getCharges_client() {
        return charges_client;
    }

    public void setCharges_client(String charges_client) {
        this.charges_client = charges_client;
    }

    public String getCharges_etincel() {
        return charges_etincel;
    }

    public void setCharges_etincel(String charges_etincel) {
        this.charges_etincel = charges_etincel;
    }

    public String getClient() {
        return
                client_id+";"+
                client_nom+";"+
                client_mobile+";"+
                client_fixe+";"+
                client_mail+";"+
                client_adresse+";"+
                client_entreprise;
    }

    public String getClient_entreprise() {
        return client_entreprise;
    }
}
