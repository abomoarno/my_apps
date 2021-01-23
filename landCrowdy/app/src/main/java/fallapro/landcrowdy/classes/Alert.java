package fallapro.landcrowdy.classes;

public class Alert {

    private String id;
    private String type;
    private String operation;
    private String date;
    private String pays;
    private String ville;
    private String quartier;
    private String superficieMin;
    private String superficieMax;
    private String prix_min;
    private String prix_max;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getSuperficieMin() {
        return superficieMin;
    }

    public void setSuperficieMin(String superficieMin) {
        this.superficieMin = superficieMin;
    }

    public String getSuperficieMax() {
        return superficieMax;
    }

    public void setSuperficieMax(String superficieMax) {
        this.superficieMax = superficieMax;
    }

    public String getPrix_min() {
        return prix_min;
    }

    public void setPrix_min(String prix_min) {
        this.prix_min = prix_min;
    }

    public String getPrix_max() {
        return prix_max;
    }

    public void setPrix_max(String prix_max) {
        this.prix_max = prix_max;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle(){

        return operation + " " + type + " " + ville;
    }

    public String getDetails(){
        String details = "Un prix max de " + prix_max + ", " + "une surface min de " + superficieMin + ", à " + ville;
        if (! quartier.equals(""))
            details += " quartier " + quartier;
        return details;

    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }
}
