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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeaponsDeckTest {

    private Deck<WeaponCard> deck;

    @Before
    public void weaponsDeckConstrucionFine() throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        Type weaponsType = new TypeToken<ArrayList<WeaponCard>>(){}.getType();

        JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/weapons.json"));
        ArrayList<WeaponCard> weapons = customGson.fromJson(reader, weaponsType);

        deck = new Deck<>();
        weapons.forEach(w -> deck.add(w));
    }

    @Test
    public void testOnWeaponDrawnFromDeck(){
        WeaponCard weaponCard;
        deck.getCards().forEach(WeaponCard::init);
        while (deck.size() != 0) {
            weaponCard = deck.draw();
            assertTrue(weaponCard.getEffectsToApply().contains(weaponCard.getFireModes().get(0)));
        }
    }
}
