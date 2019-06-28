package common.dto_model;

import common.enums.AmmoEnum;

public class PowerUpCardDTO extends AbstractCardDTO {
    
    private static final String DEFAULT_NAME = "retro_powerup";
    private String name;
    private AmmoEnum colour;
    
    public PowerUpCardDTO(String name, AmmoEnum color) {
        super( name );
        this.colour=color;
    }
    
    public PowerUpCardDTO(){
        this(DEFAULT_NAME,AmmoEnum.RED);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AmmoEnum getColour() {
        return colour;
    }

    public void setColour(AmmoEnum colour) {
        this.colour = colour;
    }
    
    @Override
    public boolean isDefaultCard() {
        return name.equals( DEFAULT_NAME );
    }
}
