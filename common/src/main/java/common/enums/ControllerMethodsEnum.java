package common.enums;

import static common.Constants.WRONG_TIME;

/**
 * Contains the possible actions an user can perform.
 * Support enum for hand-made socket stubs
 */
public enum ControllerMethodsEnum {
    SIGN_UP("s", "sign up"),
    LOG_IN("l", "log in"),
    JOIN_LOBBY("j", "join lobby"),
    CHOOSE_MAP("map", "choose map number x"),
    CHOOSE_NUMBER_OF_SKULLS( "skulls", "set the number of skulls to x" ),
    CHOOSE_PC_COLOUR("colour", "choose pc colour"),
    SHOW_COMMENT("hero", "show your ACTION-HERO comment to the other players"),
    RUN_AROUND("run", "select the action run around"),
    GRAB_STUFF("grab", "select the action grab stuff"),
    SHOOT_PEOPLE("shoot", "select the action shoot people"),
    USE_POWER_UP("power up", "use power up"),
    CHOOSE_SQUARE("cs", "choose a square on the map"),
    CHOOSE_WEAPON_ON_SPAWN_POINT("cws", "choose a weapon on a spawn point"),
    CHOOSE_WEAPON_OF_MINE("cwm", "choose one of your weapons"),
    SWITCH_FIRE_MODE("sfm", "switch fire mode"),
    CHOOSE_POWER_UP("cpu", "choose a power up"),
    CHOOSE_TARGET("ct", "choose a target for your card"),
    CHOOSE_UPGRADE("cu", "choose an upgrade"),
    CHOOSE_AMMO("ca", "choose an ammo for the targeting scope"),
    CHOOSE_ASYNCHRONOUS_EFFECT_ORDER("async", "choose the order of the selected asynchronous upgrade"),
    CHOOSE_DIRECTION("dir", "choose a direction"),
    RESPONSE("r", "use it to answer to a request"),
    OK("ok", "confirm your command"),
    RELOAD("reload", "reload a weapon"),
    PASS("pass", "pass turn"),
    SKIP("skip", "skip the action (if optional) for which your are selecting targets"),
    UNDO("undo", "undo last action (you can do it only if your last action has not been confirmed yet"),
    SEND_MESSAGE("msg", "send message"),
    QUIT("q", "quitFromLobby"),
    HELP("help", "print help");


    private String command;
    private String description;
    
    ControllerMethodsEnum(String command, String description){
        this.command = command;
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

    public String getDescription(){
        return description;
    }


    public static String help() {
        return ">>> Available commands:\n" +
                        "\t\"" + CHOOSE_MAP.getCommand() + " + x\", 1<=x<=4 \t\t\t\t\t| "+ CHOOSE_MAP.getDescription() + "\n" +
                        "\t\"" + CHOOSE_NUMBER_OF_SKULLS.getCommand() + " + x\", 5<=x<=8\t\t\t\t| " + CHOOSE_NUMBER_OF_SKULLS.getDescription() + "\n" +
                        "\t\"" + CHOOSE_PC_COLOUR.getCommand() + " + x\", x a colour \t\t\t| " + CHOOSE_PC_COLOUR.getDescription() + "\n" +
                        "\t\"" + SHOW_COMMENT.getCommand() + "\"\t\t\t\t\t\t\t\t| " + SHOW_COMMENT.getDescription() + "\n" +
                        "\t\"" + RUN_AROUND.getCommand() + "\"\t\t" + RUN_AROUND.getDescription() + "\n" +
                        "\t\"" + GRAB_STUFF.getCommand() + "\"\t\t" + GRAB_STUFF.getDescription() + "\n" +
                        "\t\"" + SHOOT_PEOPLE.getCommand() + "\"\t\t" + SHOOT_PEOPLE.getDescription() + "\n" +
                        "\t\"" + USE_POWER_UP.getCommand() + "\"\t\t" + USE_POWER_UP.getDescription() + "\n" +
                        "\t\"" + CHOOSE_SQUARE.getCommand() + "\"\t\t\t" + CHOOSE_SQUARE.getDescription() + "\n" +
                        "\t\"" + CHOOSE_WEAPON_ON_SPAWN_POINT.getCommand() + "\"\t\t\t" + CHOOSE_WEAPON_ON_SPAWN_POINT.getDescription() + "\n" +
                        "\t\"" + CHOOSE_WEAPON_OF_MINE.getCommand() + "\"\t\t" + CHOOSE_WEAPON_OF_MINE.getDescription() + "\n" +
                        "\t\"" + SWITCH_FIRE_MODE.getCommand() + "\"\t\t" + SWITCH_FIRE_MODE.getDescription() + "\n" +
                        "\t\"" + CHOOSE_TARGET.getCommand() + "\"\t\t" + CHOOSE_TARGET.getDescription() + "\n" +
                        "\t\"" + CHOOSE_AMMO.getCommand() + "\"\t\t" + CHOOSE_AMMO.getDescription() + "\n" +
                        "\t\"" + CHOOSE_POWER_UP.getCommand() + "\"\t\t" + CHOOSE_POWER_UP.getDescription() + "\n" +
                        "\t\"" + CHOOSE_UPGRADE.getCommand() + "\"\t\t\t" + CHOOSE_UPGRADE.getDescription() + "\n" +
                        "\t\"" + CHOOSE_ASYNCHRONOUS_EFFECT_ORDER.getCommand() + "\"\t\t" + CHOOSE_ASYNCHRONOUS_EFFECT_ORDER.getDescription() + "\n" +
                        "\t\"" + CHOOSE_DIRECTION.getCommand() + "\"\t\t" + CHOOSE_DIRECTION.getDescription() + "\n" +
                        "\t\"" + RELOAD.getCommand() + "\"\t\t" + RELOAD.getDescription() + "\n" +
                        "\t\"" + SKIP.getCommand() + "\"\t\t" + SKIP.getDescription() + "\n" +
                        "\t\"" + OK.getCommand() + "\"\t\t\t" + OK.getDescription() + "\n" +
                        "\t\"" + PASS.getCommand() + "\"\t\t" + PASS.getDescription() + "\n" +
                        "\t\"" + UNDO.getCommand() + "\"\t\t" + UNDO.getDescription() + "\n" +
                        "\t\"" + QUIT.getCommand() + "\"\t\t" + QUIT.getDescription() + "\n" ;
    }


}
