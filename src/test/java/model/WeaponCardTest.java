package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeaponCardTest {
    @Mock Game g;
    private WeaponCard weaponCard;

    @Before
    public void initialize(){
        try {
            JSONArray jsonWeapons = (JSONArray) Server.readJson("weapons");
            weaponCard = new WeaponCard((JSONObject) jsonWeapons.get(0), g);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void attributesFine(){
        assertTrue(weaponCard.isLoaded());
        assertEquals("Lock Rifle", weaponCard.getName());
        short[] ammos = new short[3];
        ammos[0] = 2;
        assertEquals(ammos[0], weaponCard.getAmmos()[0]);
        assertEquals(ammos[1], weaponCard.getAmmos()[1]);
        assertEquals(ammos[2], weaponCard.getAmmos()[2]);
    }

    @Test
    public void updateFiremodesAndUpgradesFine(){
        weaponCard.selectFireMode(0);
        weaponCard.addUpgrade(0);
        assertEquals(2, weaponCard.getCurrentEffect().size());
        weaponCard.removeUpgrade(0);
        assertEquals(1, weaponCard.getCurrentEffect().size());
        weaponCard.addUpgrade(0);
    }
}
