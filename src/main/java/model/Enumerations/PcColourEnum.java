package model.Enumerations;

public enum PcColourEnum {
    GREY("DOZER"),
    GREEN("SPROG"),
    YELLOW(":D-STRUCT-OR"),
    PURPLE("VIOLET"),
    BLUE("BANSHEE");

    private String name;

    PcColourEnum (String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
