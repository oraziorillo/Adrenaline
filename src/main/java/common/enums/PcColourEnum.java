package common.enums;

import java.io.Serializable;

/**
 * Enumerates the possible colours of a Pc. Has a method to get the Pc name (Not the Player's username)
 */
public enum PcColourEnum implements Serializable {
    GREY("DOZER"),
    GREEN("SPROG"),
    YELLOW(":D-STRUCT-OR"),
    PURPLE("VIOLET"),
    BLUE("BANSHEE");

    private String name;

    PcColourEnum (String name){
        this.name = name;
    }

    public static PcColourEnum fromString(String stringed) {
        for(PcColourEnum e: values()){
            if(stringed.trim().equalsIgnoreCase( e.toString() )){
                return e;
            }
        }
        return null;
    }


    public String getName(){
        return name;
    }

}
