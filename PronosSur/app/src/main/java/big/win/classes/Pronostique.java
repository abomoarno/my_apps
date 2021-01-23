package big.win.classes;

public class Pronostique {

    private int id;

    private String name_teamA;
    private String scoreA;
    private String name_teamB;
    private String scoreB;

    private String cote;
    private String pays;
    private String premium;
    private String hours;
    private String result;
    private String pronostic;
    private int notified;

    public Pronostique(){}

    public Pronostique(int id, String pays, String hours, String cote,String pronostic, String premium, String result) {
        this.id = id;
        this.cote = cote;
        this.pays = pays;
        this.premium = premium;
        this.hours = hours;
        this.result = result;
        this.pronostic = pronostic;
    }

    public Pronostique(int id){
        this.id = id;
    }

    public String getPronostic() {
        return pronostic;
    }

    public void setPronostic(String pronostic) {
        this.pronostic = pronostic;
    }

    public String getResult() {
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_teamA() {
        return name_teamA;
    }

    public void setName_teamA(String name_teamA) {
        this.name_teamA = name_teamA;
    }

    public String getScoreA() {
        return scoreA;
    }

    public void setScoreA(String scoreA) {
        this.scoreA = scoreA;
    }

    public String getName_teamB() {
        return name_teamB;
    }

    public void setName_teamB(String name_teamB) {
        this.name_teamB = name_teamB;
    }

    public String getScoreB() {
        return scoreB;
    }

    public void setScoreB(String scoreB) {
        this.scoreB = scoreB;
    }

    public String getCote() {
        return cote;
    }

    public void setCote(String cote) {
        this.cote = cote;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String object) {
        this.pays = object;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public int getNotified() {
        return notified;
    }

    public void setNotified(int notified) {
        this.notified = notified;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
