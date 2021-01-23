package afrimoov.jefala.classes;

import afrimoov.jefala.db_managment.MaisonsManager;

public class Maison extends Annonce {

    private String style;

    public Maison(int id, String titre) {
        super(id, titre);
    }

    public String getStyle() {
        return style;
    }
    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public void doDisLike() {
        super.doDisLike();
        new MaisonsManager(null).doDisLike(getId());
    }

    @Override
    public void doLike() {
        super.doLike();
        new MaisonsManager(null).doLike(getId());
    }


    public static int getLastId() {
        return new MaisonsManager(null).getLastId();
    }

    @Override
    public String getTypeAnnonce() {
        return style;
    }
}
