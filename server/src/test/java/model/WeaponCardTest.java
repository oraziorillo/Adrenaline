package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import model.actions.Action;
import model.deserializers.ActionDeserializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeaponCardTest {
    @Mock Game g;
    private WeaponCard weaponCard;

    @Before
    public void initFine() throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.create();
        JsonReader reader = new JsonReader(new FileReader("/home/orazio/Documents/ids_progetto/ing-sw-2019-23/json/weaponCard.json"));
        weaponCard = customGson.fromJson(reader, WeaponCard.class);
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
        assertEquals(2, weaponCard.getEffectsToApply().size());
        weaponCard.removeUpgrade(0);
        assertEquals(1, weaponCard.getEffectsToApply().size());
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
