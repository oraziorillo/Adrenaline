package enums;

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
    PASS;
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    public static SocketCommandsEnum fromString(String s){
        for( SocketCommandsEnum cmd: SocketCommandsEnum.values()){
            if(s.equals( cmd.toString())){
                return cmd;
            }
        }
        throw new IllegalArgumentException("Invalid command");
    }
}
