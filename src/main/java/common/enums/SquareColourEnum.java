package common.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Contains the possible colours of a square (room)
 */
public enum SquareColourEnum {
    @SerializedName("BLUE")
    BLUE,
    @SerializedName("RED")
    RED,
    @SerializedName("YELLOW")
    YELLOW,
    @SerializedName("GREEN")
    GREEN,
    @SerializedName("WHITE")
    WHITE,
    @SerializedName("VIOLET")
    VIOLET;
}
