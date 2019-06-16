package common.model_dtos;

import common.enums.AmmoEnum;

public class PowerUpCardDTOFirstVersion extends Card{
   public static final String DEFAULT_NAME = "retro_powerup";
   private AmmoEnum color;
   
   public PowerUpCardDTOFirstVersion(String name, AmmoEnum color){
      super(name);
      this.color = color;
   }
   
   public PowerUpCardDTOFirstVersion(){
      super(DEFAULT_NAME);
   }
   
   @Override
   public String getImagePath() {
      String appendedColor = color==null?"":("_"+color.toString().toLowerCase()+"_");
      return super.getImagePath() + name + appendedColor + extension;
   }
   
   @Override
   public boolean isDefaultCard() {
      return name.equals( DEFAULT_NAME );
   }
}