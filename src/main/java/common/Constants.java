package common;

/**
 * Some game contants
 */
public final class Constants {

    // --------------> CONTROL
    public static final String SUCCESS = "Y";
    public static final String FAIL = "N";

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

    //decks
    public static final int AMMO_DECK = 0;
    public static final int WEAPONS_DECK = 1;
    public static final int POWER_UPS_DECK = 2;

    //rules
    public static final int FIRST_MAP = 1;
    public static final int LAST_MAP = 4;
    public static final int MIN_KILL_SHOT_TRACK_SIZE = 5;
    public static final int MAX_KILL_SHOT_TRACK_SIZE = 8;

    //about player actions
    public static final int ACTIONS_PER_TURN = 2;
    public static final int ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER = 1;


    // --------------> EVENTS

    //game board events
    public static final int GAME_BOARD_SET = 0;

    //kill shot track events
    public static final int FINAL_FRENZY = 1;
    public static final int KILL_SHOT_TRACK_SET = 2;

    //pc events
    public static final int ADRENALINE_UP = 3;
    public static final int MOVEMENT = 4;
    public static final int POWER_UP_DISCARDED = 5;
    public static final int POWER_UP_DROWN = 6;
    public static final int SPAWN = 7;

    //pcBoard events
    public static final int AMMO_CHANGED = 8;
    public static final int DAMAGE_MARKS_TAKEN = 9;
    public static final int DEATH = 10;
    public static final int MARKS_TAKEN = 11;
    public static final int NUMBER_OF_DEATH_INCREASED = 12;
    public static final int POINTS_INCREASED = 13;

    //square events
    public static final int ITEM_COLLECTED = 14;
    public static final int SQUARE_REFILLED = 15;
    public static final int TARGETABLE_SET = 16;


    // --------------> SOCKET

    public static final String REGEX = "#";


    Constants() {
    }
}
