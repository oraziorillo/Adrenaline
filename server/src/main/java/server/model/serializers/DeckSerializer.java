package server.model.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import server.model.Deck;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DeckSerializer<E> implements JsonSerializer<Deck<E>> {

    @Override
    public JsonElement serialize(Deck<E> src, Type typeOfSrc, JsonSerializationContext context) {

        List<E> cards = src.getCards();

        Type cardsType = new TypeToken<ArrayList<E>>(){}.getType();

        return context.serialize(cards, cardsType);
    }
}
