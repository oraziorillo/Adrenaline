package server.model.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import server.model.Deck;
import server.model.AmmoTile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AmmoDeckDeserializer implements JsonDeserializer<Deck<AmmoTile>> {

    @Override
    public Deck<AmmoTile> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Type cardsType = new TypeToken<ArrayList<AmmoTile>>(){}.getType();

        List<AmmoTile> cards = context.deserialize(json, cardsType);

        Deck<AmmoTile> deck = new Deck<>();
        deck.setCards(cards);
        return deck;
    }
}