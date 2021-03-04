package common.enums;

import java.util.Formatter;

import static common.Constants.WRONG_TIME;

/**
 * Contains the possible actions an user can perform.
 * Support enum for hand-made socket stubs
 */
public enum ControllerMethodsEnum {
    SIGN_UP("s",  "\"s\"", "sign up"),
    LOG_IN("l", "\"l\"", "log in"),
    JOIN_LOBBY("j", "\"j\"", "join lobby"),
    CHOOSE_MAP("map", "\"map x\", 1<=x<=4", "choose map number x"),
    CHOOSE_NUMBER_OF_SKULLS( "skulls", "\"skulls x\", 5<=x<=8", "set the number of skulls to x" ),
    CHOOSE_PC_COLOUR("colour", "\"colour x\", x a colour", "choose pc colour"),
    SHOW_PC_STATUS("status", "\"status\"", "show the status of you character"),
    RUN_AROUND("run", "\"run\"","select the action run around"),
    GRAB_STUFF("grab", "\"grab\"", "select the action grab stuff"),
    SHOOT_PEOPLE("shoot", "\"shoot\"", "select the action shoot people"),
    USE_POWER_UP("upu", "\"cpu x\", x the index of the power up",  "use power up"),
    CHOOSE_SQUARE("cs", "\"cs x y\", 0<=x<=2 and 0<=y<=3", "choose the square (x,y) on the map"),
    CHOOSE_WEAPON_ON_SPAWN_POINT("cws", "\"cws x\", x the index of the weapon", "choose a weapon on a spawn point"),
    CHOOSE_WEAPON_OF_MINE("cwm", "\"cwm x\", x the index of the weapon", "choose one of your weapons"),
    SWITCH_FIRE_MODE("sfm", "\"sfm\"", "switch fire mode"),
    CHOOSE_POWER_UP("cpu", "\"cpu x\", the index of the power up","select the power up with index x in your inventory"),
    CHOOSE_TARGET("ct", "\"ct\"", "choose a target for your card"),
    CHOOSE_UPGRADE("cu", "\"cu\"", "choose an upgrade"),
    CHOOSE_AMMO("ca", "\"ca\"", "choose an ammo for the targeting scope"),
    CHOOSE_ASYNCHRONOUS_EFFECT_ORDER("async", "\"async\"", "choose the order of the selected asynchronous upgrade"),
    CHOOSE_DIRECTION("dir", "\"dir\"", "choose a direction"),
    RESPONSE("r", "\"r\"", "use it to answer to a request"),
    OK("ok", "\"ok\"", "confirm your command"),
    RELOAD("reload", "\"reload\"", "reload a weapon"),
    PASS("pass", "\"pass\"", "pass turn"),
    SKIP("skip", "\"skip\"", "skip the action (if optional) for which your are selecting targets"),
    UNDO("undo", "\"undo\"", "undo last action (you can do it only if your last action has not been confirmed yet"),
    SEND_MESSAGE("msg", "\"msg x\", x a string", "send a message to all players"),
    QUIT("q", "\"q\"", "quit the lobby"),
    HELP("h", "\"h\"", "print help");


    private String command;
    private String usage;
    private String description;
    
    ControllerMethodsEnum(String command, String usage, String description){
        this.command = command;
        this.usage = usage;
        this.description = description;
    }
    
    public static ControllerMethodsEnum parseString(String s){
        for(ControllerMethodsEnum e: values()){
            if(e.command.equals(s)){
                return e;
            }
        }
        throw new IllegalArgumentException(WRONG_TIME);
    }

    public String getCommand(){
        return command;
    }

    public String getUsage(){
        return usage;
    }

    public String getDescription(){
        return description;
    }


    public static String help() {
        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);
        sb.append(">>> Available commands:\n");
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n", "COMMAND", "USAGE", "DESCRIPTION");
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_MAP.getCommand(), CHOOSE_MAP.getUsage(), CHOOSE_MAP.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_NUMBER_OF_SKULLS.getCommand(), CHOOSE_NUMBER_OF_SKULLS.getUsage(), CHOOSE_NUMBER_OF_SKULLS.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_PC_COLOUR.getCommand(), CHOOSE_PC_COLOUR.getUsage(), CHOOSE_PC_COLOUR.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                SHOW_PC_STATUS.getCommand(), SHOW_PC_STATUS.getUsage(), SHOW_PC_STATUS.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                RUN_AROUND.getCommand(), RUN_AROUND.getUsage(), RUN_AROUND.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                GRAB_STUFF.getCommand(), GRAB_STUFF.getUsage(), GRAB_STUFF.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                SHOOT_PEOPLE.getCommand(), SHOOT_PEOPLE.getUsage(), SHOOT_PEOPLE.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                USE_POWER_UP.getCommand(), USE_POWER_UP.getUsage(), USE_POWER_UP.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_SQUARE.getCommand(), CHOOSE_SQUARE.getUsage(), CHOOSE_SQUARE.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_WEAPON_ON_SPAWN_POINT.getCommand(), CHOOSE_WEAPON_ON_SPAWN_POINT.getUsage(), CHOOSE_WEAPON_ON_SPAWN_POINT.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_WEAPON_OF_MINE.getCommand(), CHOOSE_WEAPON_OF_MINE.getUsage(), CHOOSE_WEAPON_OF_MINE.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                SWITCH_FIRE_MODE.getCommand(), SWITCH_FIRE_MODE.getUsage(), SWITCH_FIRE_MODE.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_TARGET.getCommand(), CHOOSE_TARGET.getUsage(), CHOOSE_TARGET.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_AMMO.getCommand(), CHOOSE_AMMO.getUsage(), CHOOSE_AMMO.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_POWER_UP.getCommand(), CHOOSE_POWER_UP.getUsage(), CHOOSE_POWER_UP.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_UPGRADE.getCommand(), CHOOSE_UPGRADE.getUsage(), CHOOSE_UPGRADE.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_ASYNCHRONOUS_EFFECT_ORDER.getCommand(), CHOOSE_ASYNCHRONOUS_EFFECT_ORDER.getUsage(), CHOOSE_ASYNCHRONOUS_EFFECT_ORDER.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                CHOOSE_DIRECTION.getCommand(), CHOOSE_DIRECTION.getUsage(), CHOOSE_DIRECTION.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                RELOAD.getCommand(), RELOAD.getUsage(), RELOAD.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                SKIP.getCommand(), SKIP.getUsage(), SKIP.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                OK.getCommand(), OK.getUsage(), OK.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                PASS.getCommand(), PASS.getUsage(), PASS.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                UNDO.getCommand(), UNDO.getUsage(), UNDO.getDescription());
        fmt.format("\t%-40.40s | %-40.40s | %-40.80s%n",
                QUIT.getCommand(), QUIT.getUsage(), QUIT.getDescription());
        return sb.toString();
    }


}
