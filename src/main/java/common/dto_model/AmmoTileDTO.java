package common.dto_model;

import common.enums.AmmoEnum;
import javafx.scene.image.ImageView;
import server.model.AmmoTile;

public class AmmoTileDTO {

    private short[] ammo;
    private boolean hasPowerUp;
    protected static final String EXTENSION = ".png";
    protected static final String DIR = "/images/ammos/";

    AmmoTileDTO(AmmoTile ammoTile){
        this.ammo = ammoTile.getAmmo();
        this.hasPowerUp = ammoTile.containsPowerUp();
    }
   
   public static String getEmptyPath() {
       return DIR+"empty"+EXTENSION;
   }
   
   public String getImagePath() {
        StringBuilder pathBuilder = new StringBuilder();
        if(hasPowerUp){
            pathBuilder.append( "p" );
        }
        for(int i=0;i<AmmoEnum.values().length;i++){
            AmmoEnum color = AmmoEnum.values()[i];
            pathBuilder.append( color.toString().toLowerCase() );
        }
        return pathBuilder.toString();
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (AmmoEnum a : AmmoEnum.values())
            stringBuilder.append("+ ").append(ammo[a.ordinal()]).append(" ").append(a).append("\n");
        if (hasPowerUp)
            stringBuilder.append("+ 1 Power Up Card");
        return stringBuilder.toString();
    }
}
