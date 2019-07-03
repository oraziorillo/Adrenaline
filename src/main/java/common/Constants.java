package common;

import java.util.concurrent.TimeUnit;

/**
 * Some game contants
 */
public final class Constants {

    // --------------> CONTROL
    public static final String SUCCESS = "Y";
    public static final String FAIL = "N";

    // --------------> TIMER

    public static final int TIME = Math.toIntExact(TimeUnit.SECONDS.toMillis(10));
    public static final int REQUEST_TIME = Math.toIntExact(TimeUnit.SECONDS.toMillis(30));

    // --------------> RULES

    public static final int LIFE_POINTS = 11;

    //card limits
    public static final int MAX_WEAPONS_IN_HAND = 3;
    public static final int MAX_POWER_UPS_IN_HAND = 3;
    public static final int MAX_NUMBER_OF_MARKS_PER_COLOUR = 3;

    //ammo limits
    public static final int MAX_AMMO_PER_COLOUR = 3;
    public static final int CARDS_ON_SPAWN_POINT = 3;
    public static final int AMMO_COLOURS_NUMBER = 3;

    //pc values
    public static final int[] PC_VALUES = {8, 6, 4, 2, 1, 1};
    public static final int[] FINAL_FRENZY_PC_VALUES = {2, 1, 1, 1};

    //rules
    public static final int FIRST_MAP = 1;
    public static final int LAST_MAP = 4;
    public static final int MIN_KILL_SHOT_TRACK_SIZE = 5;
    public static final int MAX_KILL_SHOT_TRACK_SIZE = 8;

    //about player actions
    public static final int ACTIONS_PER_TURN = 2;
    public static final int ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER = 1;


    // --------------> EVENTS

    //lobby events
    public static final int PLAYER_JOINED = -1;

    //game board events
    public static final int GAME_BOARD_SET = 0;

    //kill shot track events
    public static final int FINAL_FRENZY = 1;
    public static final int KILL_SHOT_TRACK_SET = 2;

    //pc events
    public static final int PC_COLOUR_CHOSEN = 3;
    public static final int ADRENALINE_UP = 4;
    public static final int MOVEMENT = 5;
    public static final int POWER_UP_DISCARDED = 6;
    public static final int POWER_UP_DROWN = 7;
    public static final int SPAWN = 8;
    public static final int COLLECT_EVENT = 9;

    //pcBoard events
    public static final int AMMO_CHANGED = 10;
    public static final int DAMAGE_MARKS_TAKEN = 11;
    public static final int DEATH = 12;
    public static final int NUMBER_OF_DEATH_INCREASED = 13;
    public static final int POINTS_INCREASED = 14;

    //square events
    public static final int ITEM_COLLECTED = 15;
    public static final int SQUARE_REFILLED = 16;
    public static final int TARGETABLE_SET = 17;


    // --------------> SOCKET

    public static final String REGEX = "#";


    // --------------> MESSAGES

    public static final String WRONG_TIME = "\nOak's words echoed... There's a time and place for everything, but not now";


    Constants() {
    }
}
