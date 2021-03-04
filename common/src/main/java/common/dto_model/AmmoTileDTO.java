package common.dto_model;

import common.enums.AmmoEnum;

public class AmmoTileDTO implements DTO {

    private short[] ammo;
    private boolean hasPowerUp;
    protected static final String EXTENSION = ".png";
    protected static final String DIR = "/images/ammos/";


    public static String getEmptyPath() {
        return DIR + "empty" + EXTENSION;
    }

    public String getImagePath() {
        StringBuilder pathBuilder = new StringBuilder(DIR);
        if (hasPowerUp) {
            pathBuilder.append("p");
        }
        for (int colIndex = 0; colIndex < AmmoEnum.values().length; colIndex++) {
            AmmoEnum color = AmmoEnum.values()[colIndex];
            for(int i=0;i<ammo[colIndex];i++)
                pathBuilder.append(color.toString().toLowerCase());
        }
        pathBuilder.append( EXTENSION );
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
            stringBuilder.append(System.lineSeparator()).append("+ ").append(ammo[a.ordinal()]).append(" ").append(a);
        if (hasPowerUp)
            stringBuilder.append(System.lineSeparator()).append("+ 1 Power Up");
        return stringBuilder.toString();
    }
}
