package common.dto_model;

import common.enums.AmmoEnum;
import server.model.PowerUpCard;

public class PowerUpCardDTO extends AbstractCardDTO implements DTO {
    
 //   private static final String DEFAULT_NAME = "retro_powerup";
    private String name;
    private AmmoEnum colour;

//    @Override
//    public String getImagePath() {
//        String appendedColor = colour==null?"":("_"+colour.toString().toLowerCase());
//        return super.getImagePath() + name + appendedColor + extension;
//    }
//
    public String getName() {
        return name;
    }

    public AmmoEnum getColour() {
        return colour;
    }
    
//    @Override
//    public boolean isDefaultCard() {
//        return name.equals( DEFAULT_NAME );
//    }
//
//    public static PowerUpCardDTO getCardBack(){
//        return new PowerUpCardDTO();
//    }


    public void setName(String name) {
        this.name = name;
    }

    public void setColour(AmmoEnum colour) {
        this.colour = colour;
    }
}
