package server.model;

import common.dto_model.AmmoTileDTO;
import common.enums.AmmoEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AmmoTileTest {

    private AmmoTile ammoTile;

    @Before
    public void init(){
        short[] ammo = new short[AmmoEnum.values().length];
        ammo[0] = 1;
        ammo[1] = 1;
        ammo[2] = 1;
        ammoTile = new AmmoTile(ammo, false);
    }


    @Test
    public void DTOBuiltCorrectly() {
        AmmoTileDTO ammoTileDTO = ammoTile.convertToDTO();
        for (int i = 0; i < ammoTile.getAmmo().length; i++)
            assertEquals(ammoTile.getAmmo()[i], ammoTileDTO.getAmmo()[i]);
        assertEquals(ammoTile.hasPowerUp(), ammoTileDTO.hasPowerUp());
    }
}
