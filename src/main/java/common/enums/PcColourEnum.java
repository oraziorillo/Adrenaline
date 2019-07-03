package common.enums;

import java.io.Serializable;

/**
 * Enumerates the possible colours of a Pc. Has a method to get the Pc name (Not the Player's username)
 */
public enum PcColourEnum implements Serializable {
    GREY("DOZER", "> background: paramilitary covert ops\n> speciality: hurting people\n> other interests: breaking stuff\n> testosterone level: high", "\t\t"),
    GREEN("SPROG", "> origins: claim to be from Texas\n> disposition: surly\n> turn-ons: crickets, flat rocks and heat lamps\n> turn-offs: lotion commercials that say \"dry, scaly skin\" like it's a bad thing", "\t\t"),
    YELLOW(":D-STRUCT-OR", "> favorite beverage: 5W-30\n> hobbies: tennis, bowling, sheet-metal origami\n> favorite board game: Robo Rally\n> beloved pet: a cordless drill", "\t"),
    PURPLE("VIOLET", "> profession: shooting instructor\n> nail polish: always perfect\n> favorite snacks: chips and salsa\n> favorite weapon: anything that goes BOOM!", "\t"),
    BLUE("BANSHEE", "> home planet: unknown\n> guilty pleasure: karaoke\n> perfect date: a long walk by the ocean - or in the ocean\n> siblings: 900 sisters all the same age", "\t\t");

    private String name;
    private String description;
    private String tabs;

    PcColourEnum (String name, String description, String tabs){
        this.name = name;
        this.description = description;
        this.tabs = tabs;
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


    public String getDescription() {
        return description;
    }


    public String getTabs() {
        return tabs;
    }
}
