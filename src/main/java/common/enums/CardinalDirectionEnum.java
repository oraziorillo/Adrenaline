package common.enums;

/**
 * Enumerates the 4 cardinal directions
 */
public enum CardinalDirectionEnum {
    NORTH("n"),
    EAST("e"),
    SOUTH("s"),
    WEST("w");

    private String abbreviation;

    CardinalDirectionEnum(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static CardinalDirectionEnum parseString(String direction){
        for (CardinalDirectionEnum c : values()) {
            if (c.toString().equalsIgnoreCase(direction) ||
                    c.abbreviation.equalsIgnoreCase(direction))
                return c;
        }
        return null;
    }

}
