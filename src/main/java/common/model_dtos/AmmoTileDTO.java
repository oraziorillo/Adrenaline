package common.model_dtos;

public class AmmoTileDTO {

    private short[] ammo;
    private boolean hasPowerUp;
    protected String extension = ".png";


    public String getImagePath() {
        //TODO
        return "Ciao";
    }

    public short[] getAmmo() {
        return ammo;
    }



    public void setHasPowerUp(boolean hasPowerUp) {
        this.hasPowerUp = hasPowerUp;
    }

    public boolean hasPowerUp() {
        return hasPowerUp;
    }

    public void setAmmo(short[] ammo) {
        this.ammo = ammo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
