package common.model_dtos;

public class WeaponCardDTOFirstVersion extends Card {
   private static final String DIRECTORY_NAME = "weapons/";
   private static final String DEFAULT_NAME = "retro_arma";
   private int basicEffects;
   private int upgrades;
   
   @Override
   public String getImagePath() {
      return super.getImagePath()+DIRECTORY_NAME+name+extension;
   }
   
   @Override
   public boolean isDefaultCard() {
      return name.equals( DEFAULT_NAME );
   }
   
   public WeaponCardDTOFirstVersion(String name, int effects, int upgrades){
      super(name);
      this.basicEffects = effects;
      this.upgrades = upgrades;
   }
   
   public WeaponCardDTOFirstVersion(){
      this(DEFAULT_NAME,1,1);
   }
   
   public int getBasicEffects() {
      return basicEffects;
   }
   
   public int getUpgrades() {
      return upgrades;
   }
}
