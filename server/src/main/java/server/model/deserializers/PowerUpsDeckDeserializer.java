package server.model.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import server.model.Deck;
import server.model.PowerUpCard;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PowerUpsDeckDeserializer implements JsonDeserializer<Deck<PowerUpCard>> {

    @Override
    public Deck<PowerUpCard> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Type cardsType = new TypeToken<ArrayList<PowerUpCard>>(){}.getType();

        List<PowerUpCard> cards = context.deserialize(json, cardsType);

        Deck<PowerUpCard> deck = new Deck<>();
        deck.setCards(cards);
        return deck;
    }
}
