package common.enums;
import com.google.gson.annotations.SerializedName;

/**
 * Enumerates the possible colours for the ammunitions.
 * AmmoEnums.valuse() must always be contained in SquareColourEnum.values()
 * @see SquareColourEnum
 */
public enum AmmoEnum {
    @SerializedName("BLUE")
    BLUE,
    @SerializedName("RED")
    RED,
    @SerializedName("YELLOW")
    YELLOW;


    public static AmmoEnum fromString(String stringed) {
        for(AmmoEnum e: values()){
            if(stringed.trim().equalsIgnoreCase( e.toString() )){
                return e;
            }
        }
        return null;
    }

    /**
     * Converts an ammo colour to a square (room) colour.
     * @return a SquareColour representing the same colour of this
     */
    public SquareColourEnum toSquareColour(){
        return SquareColourEnum.valueOf(this.toString());
    }
}

