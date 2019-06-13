package common.model_dtos;


public class AmmoTileDTO {

    private short[] ammo;
    private boolean hasPowerUp;

    /*protected String extension = ".png";

    AmmoTileDTO(AmmoTile ammoTile){
        this.ammo = ammoTile.getAmmo();
        this.hasPowerUp = ammoTile.containsPowerup();
    }

    public String getImagePath() {
        //TODO
        return null;
    }*/


    public void setAmmo(short[] ammo) {
        this.ammo = ammo;
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
}
