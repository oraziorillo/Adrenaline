package common.enums.interfaces_names;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains the possible actions an user can perform.
 * Support enum for hand-made socket stubs
 */
public enum SocketEnum {
    REGISTER,
    LOGIN,
    JOIN_LOBBY(":j",":join"),
    CHOOSE_MAP("m","map","choose map"),
    CHOOSE_NUMBER_OF_SKULLS( "sk", "skulls", "choose number of skulls" ),
    CHOOSE_PC_COLOUR("choose colour","colour","col"),
    SHOW_COMMENT("c","comment"),
    RUN_AROUND("run","r"),
    GRAB_STUFF("grab stuff","gs"),
    SHOOT_PEOPLE("shoot", "s"),
    USE_POWERUP("pw","powerup"),
    CHOOSE_SQUARE("ss","select square"),
    GRAB_WEAPON_ON_SPAWNPOINT("grab weapon","gw","choose weapon"),
    CHOOSE_WEAPON_OF_MINE("cw"),
    SWITCH_FIREMODE("sf","switch","switch firemode"),
    CHOOSE_POWERUP("ch","choose powerup"),
    CHOOSE_UPGRADE("selectUpgrade","u"),
    CHOOSE_ASYNCH_EFFECT_ORDER("asynch","ca"),
    CHOOSE_DIRECTION("choose direction", "choose dir"),
    OK("ok"),
    RELOAD("re","reload"),
    PASS("p","pass"),
    SKIP("skip"),
    USE_POWER_UP("pw","power up"),
    CHOOSE_POWER_UP("ch","choose power up"),
    REMOVE_UPGRADE("ru","remove selectUpgrade"),
    UNDO("undo"),
    QUIT("quit","q"),
    HELP("help");
    
    private Set<String> CliStrings = new HashSet<>();
    
    SocketEnum(String... validCommands){
        CliStrings.addAll( Arrays.asList( validCommands ) );
    }
    
    public static SocketEnum parseString(String s){
        for(SocketEnum e: values()){
            if(e.CliStrings.contains( s ) ){
                return e;
            }
        }
        throw new IllegalArgumentException( s + " is not a recognised command. Type help to display all possible commands." );
    }
    
    public Set<String> getCliStrings() {
        return new HashSet<>(CliStrings);
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}
