package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PowerUpDeckTest {


    private Deck<PowerUpCard> deck;

    @Before
    public void initPowerUpsDeck() throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        Type powerUpType = new TypeToken<ArrayList<PowerUpCard>>() {
        }.getType();
        JsonReader reader = null;

        reader = new JsonReader(new FileReader("src/main/resources/json/powerUps.json"));
        ArrayList<PowerUpCard> powerUps = customGson.fromJson(reader, powerUpType);

        deck = new Deck<>();
        powerUps.forEach(p -> deck.add(p));
    }

    @Test
    public void printDeck(){
        deck.print();
    }
}
