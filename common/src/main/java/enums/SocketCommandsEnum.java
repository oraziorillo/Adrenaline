package enums;

/**
 * Contains the possible actions an user can perform.
 * Support enum for hand-made socket stubs
 */
public enum SocketCommandsEnum {
    CHOOSE_MAP,
    CHOOSE_NUMBER_OF_SKULLS,
    CHOOSE_PC_COLOUR,
    DISCARD_AND_SPAWN,
    SHOW_COMMENT,
    RUN_AROUND,
    GRAB_STUFF,
    SHOOT_PEOPLE,
    SELECT_SQUARE,
    GRAB_WEAPON,
    CHOOSE_WEAPON,
    QUIT,
    SWITCH_FIREMODE,
    UPGRADE,
    CHOOSE_ASYNCH_EFFECT_ORDER,
    OK,
    RELOAD,
    PASS,
    SKIP;
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}
