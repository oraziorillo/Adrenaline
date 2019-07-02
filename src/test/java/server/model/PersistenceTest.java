package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import server.database.GameInfo;
import server.model.actions.Action;
import server.model.deserializers.*;
import server.model.serializers.ActionSerializer;
import server.model.serializers.DeckSerializer;
import server.model.serializers.TargetCheckerSerializer;
import server.model.squares.Square;
import server.model.target_checkers.TargetChecker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

public class PersistenceTest {

    private GameInfo gameInfo;

    @Before
    public void initSavedGameFine() throws FileNotFoundException {

        Type deckType = new TypeToken<Deck>(){}.getType();
        Type weaponsDeckType = new TypeToken<Deck<WeaponCard>>(){}.getType();
        Type ammoDeckType = new TypeToken<Deck<AmmoTile>>(){}.getType();
        Type powerUpDeckType = new TypeToken<Deck<PowerUpCard>>(){}.getType();

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Action.class, new ActionDeserializer())
                .registerTypeAdapter(Action.class, new ActionSerializer())
                .registerTypeAdapter(TargetChecker.class, new TargetCheckerSerializer())
                .registerTypeAdapter(Square.class, new SquareDeserializer())
                .registerTypeAdapter(deckType, new DeckSerializer<>())
                .registerTypeAdapter(weaponsDeckType, new WeaponsDeckDeserializer())
                .registerTypeAdapter(ammoDeckType, new AmmoDeckDeserializer())
                .registerTypeAdapter(powerUpDeckType, new PowerUpsDeckDeserializer())
                .create();

        JsonReader reader = new JsonReader(new FileReader("src/main/java/server/database/files/game_infos/d2cb1703-b64b-4f9c-95d9-756201d3b7b0.json"));
        gameInfo = gson.fromJson(reader, GameInfo.class);
    }


    @Test
    public void squaresInitFine() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Square s = gameInfo.getGame().getSquare(i, j);
                if (s != null)
                    System.out.print(s.toString() + "\t\t\t");
                else
                    System.out.print("null\t\t\t");
            }
            System.out.print("\n");
        }
    }


}
