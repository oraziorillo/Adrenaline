package view.cli;

/**
 * This is a support class containing the possible cli commands
 */
public abstract class Commands{
    private static final String COMMAND_PREFIX = ":";
    public static final String QUIT_LONG = COMMAND_PREFIX + "quit";
    public static final String QUIT_SHORT = COMMAND_PREFIX + "q";
    public static final String MAP_LONG = COMMAND_PREFIX + "map";
    public static final String MAP_SHORT = COMMAND_PREFIX + "m";
    public static final String MAP_LONGER = COMMAND_PREFIX + "choose_map";
    public static final String SHOOT_LONG = "shoot";
    public static final String SHOOT_SHORT = "s";
    public static final String RUN_LONG = "run";
    public static final String RUN_SHORT = "r";
    public static final String SKULLS_SHORT = COMMAND_PREFIX + "sk";
    public static final String SKULLS_LONG = COMMAND_PREFIX + "skulls";
    public static final String SKULLS_LONGER = COMMAND_PREFIX + "select_skulls";
    public static final String COLOUR_SHORT = COMMAND_PREFIX + "col";
    public static final String COLOUR_LONG = COMMAND_PREFIX + "colour";
    public static final String COLOUR_LONGER = COMMAND_PREFIX + "choose_colour";
    public static final String SPAWN_SHORT = COMMAND_PREFIX + "sp";
    public static final String SPAWN_LONG = COMMAND_PREFIX + "spawn";
    public static final String COMMENT_SHORT = COMMAND_PREFIX + "c";
    public static final String COMMENT_LONG = COMMAND_PREFIX + "comment";
    public static final String GRAB_STUFF_SHORT = "gs";
    public static final String GRAB_STUFF_LONG = "grab_stuff";
    public static final String GRAB_WEAPON_SHORT = "gw";
    public static final String GRAB_WEAPON_LONG = "grab_weapon";
    public static final String CHOOSE_WEAPON_SHORT = "cw";
    public static final String CHOOSE_WEAPON_LONG = "choose_weapon";
    public static final String SQUARE_SHORT = "ss";
    public static final String SQUARE_LONG = "select_square";
    public static final String SKIP_SHORT = "sk";
    public static final String SKIP_LONG ="skip";
}