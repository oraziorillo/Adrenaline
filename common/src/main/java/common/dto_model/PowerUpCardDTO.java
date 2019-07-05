package common.dto_model;

import common.enums.AmmoEnum;

public class PowerUpCardDTO extends AbstractCardDTO {
    
    private static final String DEFAULT_NAME = "Back";
    private static final String DIRECTORY_NAME = "powerups/";
    private AmmoEnum colour;

    @Override
    public String getImagePath() {
        String appendedColor = colour==null?"":("_"+colour.toString().toLowerCase());
        return super.getImagePath() + DIRECTORY_NAME + name + appendedColor + extension;
    }

    public String getName() {
        return name;
    }

    public AmmoEnum getColour() {
        return colour;
    }
    
    @Override
    public boolean isDefaultCard() {
        return name.equals( DEFAULT_NAME );
    }

    public static PowerUpCardDTO getCardBack(){
        PowerUpCardDTO back = new PowerUpCardDTO();
        back.setName( DEFAULT_NAME );
        return back;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setColour(AmmoEnum colour) {
        this.colour = colour;
    }
}
