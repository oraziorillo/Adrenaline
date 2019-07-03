package common.dto_model;

public class WeaponCardDTO extends AbstractCardDTO {

    private static final String DIRECTORY_NAME = "weapons/";
    private static final String DEFAULT_NAME = "Back";
    private int basicEffects;
    private int upgrades;
    private boolean loaded;


    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

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
        return super.getImagePath() + DIRECTORY_NAME + name + extension;
    }

    @Override
    public boolean isDefaultCard() {
        return name.equals(DEFAULT_NAME);
    }

    public static WeaponCardDTO getCardBack() {
        WeaponCardDTO back = new WeaponCardDTO();
        back.setName(DEFAULT_NAME);
        back.setBasicEffects(0);
        back.setUpgrades(0);
        return back;
    }

    public void setBasicEffects(int basicEffects) {
        this.basicEffects = basicEffects;
    }

    public void setUpgrades(int upgrades) {
        this.upgrades = upgrades;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
