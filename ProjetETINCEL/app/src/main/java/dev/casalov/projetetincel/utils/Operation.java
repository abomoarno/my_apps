package dev.casalov.projetetincel.utils;

public class Operation {
    private String operation_id;
    private String projet_id;
    private String gamme_id;
    private String compartiment_id;
    private int statut;
    private String nom;
    private String observations;

    public Operation(String operation_id, String nom) {
        this.operation_id = operation_id;
        this.nom = nom;
    }

    public String getOperation_id() {
        return operation_id;
    }

    public String getProjet_id() {
        return projet_id;
    }

    public void setProjet_id(String projet_id) {
        this.projet_id = projet_id;
    }

    public int isStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public String getNom() {
        return nom;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public String getGamme_id() {
        return gamme_id;
    }

    public void setGamme_id(String gamme_id) {
        this.gamme_id = gamme_id;
    }

    public String getCompartiment_id() {
        return compartiment_id;
    }

    public void setCompartiment_id(String compartiment_id) {
        this.compartiment_id = compartiment_id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}