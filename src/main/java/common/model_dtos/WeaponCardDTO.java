package common.model_dtos;

public class WeaponCardDTO extends Card {
   private static final String DEFAULT_NAME = "retro_arma";

   public WeaponCardDTO(String name){
      super(name);
   }

   @Override
   public String getImagePath() {
      return super.getImagePath()+name+extension;
   }
   
   @Override
   public boolean isDefaultCard() {
      return name.equals( DEFAULT_NAME );
   }
   
   public WeaponCardDTO(){
      super(DEFAULT_NAME);
   }
   
}
