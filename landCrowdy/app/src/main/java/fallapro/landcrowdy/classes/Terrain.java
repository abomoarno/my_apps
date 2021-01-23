package fallapro.landcrowdy.classes;

import fallapro.landcrowdy.dbManagment.TerrainsManager;

public class Terrain extends Annonce {

    private String status;
    public Terrain(int id, String titre) {
        super(id, titre);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void doDisLike() {
        super.doDisLike();
        new TerrainsManager(null).doDisLike(getId());
    }

    @Override
    public void doLike() {
        super.doLike();
        new TerrainsManager(null).doLike(getId());
    }

    public static int getLastId(){
        return new TerrainsManager(null).getLastId();
    }

    @Override
    public String getTypeAnnonce() {
        return "Terrain";
    }
}