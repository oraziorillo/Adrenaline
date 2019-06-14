package server.model;

import common.enums.AmmoEnum;
import common.model_dtos.AmmoTileDTO;
import common.model_dtos.PowerUpCardDTO;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import server.controller.CustomizedModelMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomizedModelMapperTest {

    CustomizedModelMapper customizedModelMapper = new CustomizedModelMapper();
    ModelMapper modelMapper = customizedModelMapper.getModelMapper();

    @Test
    public void modelMapperConvertsSuccessfullyAmmoTile(){
        short [] ammo = new short[] {1,1,0};
        AmmoTile ammoTile = new AmmoTile(ammo, true);
        AmmoTileDTO ammoTileDTO = modelMapper.map(ammoTile, AmmoTileDTO.class);
        assertArrayEquals(ammo, ammoTileDTO.getAmmos());
        assertTrue(ammoTileDTO.hasPowerUp());
    }

    @Test
    public void modelMapperConvertsSuccessfullyPoweUpCards(){
        AmmoEnum colour = AmmoEnum.RED;
        PowerUpCard powerUpCard = new PowerUpCard(colour);
        PowerUpCardDTO powerUpCardDTO = modelMapper.map(powerUpCard, PowerUpCardDTO.class);
        assertEquals(AmmoEnum.RED, powerUpCard.getColour());
        //TODO
    }
}
