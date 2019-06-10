package common.enums;

/**
 * Contains the possible actions an user can perform.
 * Support enum for hand-made socket stubs
 */
public enum SocketPlayerEnum {
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
    SKIP,
    USE_POWERUP,
    CHOOSE_POWERUP,
    REMOVE_UPGRADE,
    UNDO,
    SET_REMOTE_VIEW,
    GET_REMOTE_VIEW;
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}
