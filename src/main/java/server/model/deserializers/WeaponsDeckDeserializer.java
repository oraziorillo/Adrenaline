package server.model.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import server.model.Deck;
import server.model.WeaponCard;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WeaponsDeckDeserializer implements JsonDeserializer<Deck<WeaponCard>> {


    @Override
    public Deck<WeaponCard> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Type cardsType = new TypeToken<ArrayList<WeaponCard>>(){}.getType();

        List<WeaponCard> cards = context.deserialize(json, cardsType);

        Deck<WeaponCard> deck = new Deck<>();
        deck.setCards(cards);
        return deck;
    }
}
