package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.dto_model.PowerUpCardDTO;
import org.junit.Before;
import org.junit.Test;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PowerUpDeckTest {


    private Deck<PowerUpCard> deck;

    @Before
    public void initPowerUpsDeck() throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        Type powerUpType = new TypeToken<ArrayList<PowerUpCard>>() {
        }.getType();
        JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/powerUps.json"));
        ArrayList<PowerUpCard> powerUps = customGson.fromJson(reader, powerUpType);

        deck = new Deck<>();
        powerUps.forEach(p -> deck.add(p));
    }


    @Test
    public void initPowerUpsFine() {
        deck.getCards().parallelStream().forEach(p -> {
            assertNotEquals(null, p.getAction());
            assertNotEquals(null, p.getColour());
            assertNotEquals(null, p.getName());
            assertFalse(p.isSelectedAsAmmo());
        });
    }


    @Test
    public void convertToValidDTO() {
        for (PowerUpCard p : deck.getCards()) {
            PowerUpCardDTO powerUpCardDTO = p.convertToDTO();
            assertEquals(p.getName(), powerUpCardDTO.getName());
            assertEquals(p.getColour(), powerUpCardDTO.getColour());
        }
    }
}
