package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;
import server.model.deserializers.SquareDeserializer;
import server.model.serializers.ActionSerializer;
import server.model.serializers.DeckSerializer;
import server.model.serializers.TargetCheckerSerializer;
import server.model.squares.Square;
import server.model.target_checkers.TargetChecker;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;

public class SerializerTest {

    @Test
    public void initSavedGameFine() throws FileNotFoundException {

        Type deckType = new TypeToken<Deck>() {
        }.getType();

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Action.class, new ActionDeserializer())
                .registerTypeAdapter(Action.class, new ActionSerializer())
                .registerTypeAdapter(TargetChecker.class, new TargetCheckerSerializer())
                .registerTypeAdapter(Square.class, new SquareDeserializer())
                .registerTypeAdapter(deckType, new DeckSerializer<>())
                .create();

        Game game = Game.getGame();
        System.out.println(gson.toJson(game, Game.class));
    }

}
