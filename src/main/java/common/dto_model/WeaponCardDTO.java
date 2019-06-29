package common.dto_model;

public class WeaponCardDTO extends AbstractCardDTO {
    
    private static final String DIRECTORY_NAME = "weapons/";
    private static final String DEFAULT_NAME = "retro_arma";
    private int basicEffects;
    private int upgrades;
    
    public WeaponCardDTO(String name,int basicEffects,int upgrades) {
        super( name );
        this.basicEffects=basicEffects;
        this.upgrades=upgrades;
    }
    
    public WeaponCardDTO(){
        super(DEFAULT_NAME);
        setBasicEffects( 1 );
        setUpgrades( 1 );
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setBasicEffects(int basicEffects) {
        this.basicEffects = basicEffects;
    }
    
    public int getBasicEffects() {
        return basicEffects;
    }
    
    public void setUpgrades(int upgrades) {
        this.upgrades = upgrades;
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
   
   public static WeaponCardDTO getCardBack() {
      return new WeaponCardDTO(DEFAULT_NAME,0,0);
   }
   
}
