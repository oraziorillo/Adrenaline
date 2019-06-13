package common.model_dtos;

import common.enums.AmmoEnum;

public class PowerUpCardDTO {

    private String name;
    private AmmoEnum colour;


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
}
