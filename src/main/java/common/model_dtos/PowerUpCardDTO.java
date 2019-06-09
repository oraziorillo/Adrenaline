package common.model_dtos;

import common.enums.AmmoEnum;

public class PowerUpCardDTO extends Card{
   public static final String DEFAULT_NAME = "retro_powerup";
   private AmmoEnum color;
   
   public PowerUpCardDTO(String name, AmmoEnum color){
      super(name);
      this.color = color;
   }
   
   public PowerUpCardDTO(){
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
