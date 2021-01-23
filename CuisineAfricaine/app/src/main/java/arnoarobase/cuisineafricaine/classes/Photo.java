package arnoarobase.cuisineafricaine.classes;

public class Photo {

    private String id;
    private String titre;
    private String description;
    private String image;
    private String date;
    private String recette;
    private String user;

    private String recette_id;
    private String recette_nom;

    public Photo(String id, String titre) {
        this.id = id;
        this.titre = titre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecette() {
        return recette;
    }

    public void setRecette(String recette) {
        this.recette = recette;
        this.recette_id = this.recette.split(";")[0];
        this.recette_nom = this.recette.split(";")[1];
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecette_id() {
        return recette_id;
    }

    public String getRecette_nom() {
        return recette_nom;
    }
}
