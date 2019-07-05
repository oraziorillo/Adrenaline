package common;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import common.events.ModelEvent;
import common.events.game_board_events.GameBoardSetEvent;
import common.events.kill_shot_track_events.FinalFrenzyEvent;
import common.events.kill_shot_track_events.KillShotTrackSetEvent;
import common.events.pc_board_events.*;
import common.events.pc_events.*;
import common.events.square_events.ItemCollectedEvent;
import common.events.square_events.SquareRefilledEvent;
import common.events.square_events.TargetableSetEvent;

import java.lang.reflect.Type;

import static common.Constants.*;

/**
 * Custom json deserialyzer
 */
public class ModelEventDeserializer implements JsonDeserializer<ModelEvent> {

    @Override
    public ModelEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        switch (json.getAsJsonObject().get("eventID").getAsInt()){
            case GAME_BOARD_SET:
                return context.deserialize(json, GameBoardSetEvent.class);
            case FINAL_FRENZY:
                return context.deserialize(json, FinalFrenzyEvent.class);
            case KILL_SHOT_TRACK_SET:
                return context.deserialize(json, KillShotTrackSetEvent.class);
            case PC_COLOUR_CHOSEN:
                return context.deserialize(json, PcColourChosenEvent.class);
            case ADRENALINE_UP:
                return context.deserialize(json, AdrenalineUpEvent.class);
            case MOVEMENT:
                return context.deserialize(json, MovementEvent.class);
            case POWER_UP_DISCARDED:
                return context.deserialize(json, PowerUpDiscardedEvent.class);
            case POWER_UP_DROWN:
                return context.deserialize(json, PowerUpDrownEvent.class);
            case SPAWN:
                return context.deserialize(json, SpawnEvent.class);
            case COLLECT_EVENT:
                return context.deserialize(json, CollectEvent.class);
            case AMMO_CHANGED:
                return context.deserialize(json, AmmoChangedEvent.class);
            case DAMAGE_MARKS_TAKEN:
                return context.deserialize(json, DamageMarksTakenEvent.class);
            case DEATH:
                return context.deserialize(json, DeathEvent.class);
            case NUMBER_OF_DEATH_INCREASED:
                return context.deserialize(json, NumberOfDeathIncreasedEvent.class);
            case POINTS_INCREASED:
                return context.deserialize(json, PointsIncreasedEvents.class);
            case ITEM_COLLECTED:
                return context.deserialize(json, ItemCollectedEvent.class);
            case SQUARE_REFILLED:
                return context.deserialize(json, SquareRefilledEvent.class);
            case TARGETABLE_SET:
                return context.deserialize(json, TargetableSetEvent.class);
            default:
                throw new IllegalStateException("Unexpected value: " + json.getAsJsonObject().get("eventID").getAsInt());
        }
    }
}
