package model;

import controller.Server;
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
            weaponCard = new WeaponCard((JSONObject) jsonWeapons.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void attributesFine(){
        assertTrue(weaponCard.isLoaded());
        assertEquals("Lock Rifle", weaponCard.getName());
        short[] expectedAmmo = new short[3];
        expectedAmmo[0] = 2;
        assertEquals(expectedAmmo[0], weaponCard.getAmmo()[0]);
        assertEquals(expectedAmmo[1], weaponCard.getAmmo()[1]);
        assertEquals(expectedAmmo[2], weaponCard.getAmmo()[2]);
    }

    @Test
    public void updateFiremodesAndUpgradesFine(){
        weaponCard.selectFireMode(0);
        weaponCard.addUpgrade(0);
        assertEquals(2, weaponCard.getCurrEffect().size());
        weaponCard.removeUpgrade(0);
        assertEquals(1, weaponCard.getCurrEffect().size());
        weaponCard.addUpgrade(0);
    }

    @Test
    public void currCostFine(){
        short[] expectedCost = new short[3];
        assertEquals(expectedCost[0], weaponCard.getCurrentCost()[0]);
        assertEquals(expectedCost[1], weaponCard.getCurrentCost()[1]);
        assertEquals(expectedCost[2], weaponCard.getCurrentCost()[2]);
        weaponCard.selectFireMode(0);
        assertEquals(expectedCost[0], weaponCard.getCurrentCost()[0]);
        assertEquals(expectedCost[1], weaponCard.getCurrentCost()[1]);
        assertEquals(expectedCost[2], weaponCard.getCurrentCost()[2]);
        weaponCard.addUpgrade(0);
        expectedCost[1] = 1;
        assertEquals(expectedCost[0], weaponCard.getCurrentCost()[0]);
        assertEquals(expectedCost[1], weaponCard.getCurrentCost()[1]);
        assertEquals(expectedCost[2], weaponCard.getCurrentCost()[2]);
        weaponCard.removeUpgrade(0);
        expectedCost[1] = 0;
        assertEquals(expectedCost[0], weaponCard.getCurrentCost()[0]);
        assertEquals(expectedCost[1], weaponCard.getCurrentCost()[1]);
        assertEquals(expectedCost[2], weaponCard.getCurrentCost()[2]);
    }
}
