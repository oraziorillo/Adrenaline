package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AmmoTileDeckTest {

    private Deck<AmmoTile> deck;

    @Before
    public void ammoTileDeckConstructionFine() throws FileNotFoundException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Type ammoTileType = new TypeToken<ArrayList<AmmoTile>>(){}.getType();

        JsonReader reader;

        reader = new JsonReader(new FileReader("src/main/resources/json/ammoTiles.json"));
        ArrayList<AmmoTile> ammoTiles = gson.fromJson(reader, ammoTileType);

        deck = new Deck<>();
        ammoTiles.forEach(a -> deck.add(a));
    }


    @Test
    public void printDeck(){
        deck.print();
    }


    @Test
    public void validAmmoTiles() {
        for (AmmoTile a : deck.getCards()) {
            short numOfAmmo = sumArray(a.getAmmo());
            if (numOfAmmo == 3)
                assertFalse(a.hasPowerUp());
            else if (numOfAmmo == 2)
                assertTrue(a.hasPowerUp());
            else
                fail();
        }
    }


    @Test
    public void sizeFine(){
        assertEquals(36, deck.size());
    }


    private short sumArray(short[] array){
        short counter = 0;
        for (short s : array)
            counter += s;
        return counter;
    }
}
