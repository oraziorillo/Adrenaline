package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class WeaponsDeckTest {

    private Deck<WeaponCard> deck;

    @Before
    public void weaponsDeckConstrucionFine() throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        Type weaponsType = new TypeToken<ArrayList<WeaponCard>>() {}.getType();
        JsonReader reader = new JsonReader(new FileReader("/home/orazio/Documents/ids_progetto/ing-sw-2019-23/json/weapons.json"));
        ArrayList<WeaponCard> weapons = customGson.fromJson(reader, weaponsType);

        deck = new Deck<>();

        weapons.forEach(w -> deck.add(w));
    }

    @Test
    public void printDeck(){
        deck.print();
    }
}
