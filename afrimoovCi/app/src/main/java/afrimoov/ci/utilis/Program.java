package afrimoov.ci.utilis;

public class Program {

    private String chaine;
    private String title;
    private String hour;


    private int heure;
    private int minutes;
    private int day;
    private int month;
    private int year;
    private int id;


    public Program(String title,String chaine) {
        this.chaine = chaine;
        this.title = title;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getChaine() {
        return chaine;
    }

    public String getTitle() {
        return title;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
