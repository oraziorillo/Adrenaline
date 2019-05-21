package enums;

public enum AmmoEnum {
    BLUE,
    RED,
    YELLOW;

    public SquareColourEnum toSquareColour(){
        return SquareColourEnum.valueOf(this.toString());
    }
}

