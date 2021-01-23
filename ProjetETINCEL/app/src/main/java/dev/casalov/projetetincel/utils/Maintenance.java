package dev.casalov.projetetincel.utils;

public class Maintenance {

    private String title;
    private String maintenance_id;
    private String initial_id;
    private String projet_id;

    public Maintenance(String title, String initial_id) {
        this.title = title;
        this.initial_id = initial_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaintenance_id() {
        return maintenance_id;
    }

    public void setMaintenance_id(String maintenance_id) {
        this.maintenance_id = maintenance_id;
    }

    public String getProjet_id() {
        return projet_id;
    }

    public void setProjet_id(String projet_id) {
        this.projet_id = projet_id;
    }

    public String getInitial_id() {
        return initial_id;
    }

    public void setInitial_id(String initial_id) {
        this.initial_id = initial_id;
    }
}
