package common.dto_model;

public class WeaponCardDTO extends AbstractCardDTO {
    
    private static final String DIRECTORY_NAME = "weapons/";
    private static final String DEFAULT_NAME = "retro_arma";
    private int basicEffects;
    private int upgrades;

    
    public String getName() {
        return name;
    }

    
    public int getBasicEffects() {
        return basicEffects;
    }

    public int getUpgrades() {
        return upgrades;
    }

    @Override
    public String getImagePath() {
        return super.getImagePath()+DIRECTORY_NAME+name+extension;
    }

    @Override
    public boolean isDefaultCard() {
        return name.equals( DEFAULT_NAME );
    }

//   public static WeaponCardDTO getCardBack() {
//      return new WeaponCardDTO(DEFAULT_NAME,0,0);
//   }

    public void setBasicEffects(int basicEffects) {
        this.basicEffects = basicEffects;
    }

    public void setUpgrades(int upgrades) {
        this.upgrades = upgrades;
    }

    public void setName(String name) {
        this.name = name;
    }
}
