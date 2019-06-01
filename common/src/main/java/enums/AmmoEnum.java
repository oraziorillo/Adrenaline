package enums;

import com.google.gson.annotations.SerializedName;

public enum AmmoEnum {
    @SerializedName("BLUE")
    BLUE,
    @SerializedName("RED")
    RED,
    @SerializedName("YELLOW")
    YELLOW;

    public SquareColourEnum toSquareColour(){
        return SquareColourEnum.valueOf(this.toString());
    }
}

