package enums;

/**
 * Enumerates the possible colours for the ammunitions.
 * AmmoEnums.valuse() must always be contained in SquareColourEnum.values()
 * @see SquareColourEnum
 */
public enum AmmoEnum {
    BLUE,
    RED,
    YELLOW;
    
    /**
     * Converts an ammo colour to a square (room) colour.
     * @return a SquareColour representing the same colour of this
     */
    public SquareColourEnum toSquareColour(){
        return SquareColourEnum.valueOf(this.toString());
    }
}

