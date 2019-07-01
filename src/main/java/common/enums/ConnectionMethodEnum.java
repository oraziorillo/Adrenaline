package common.enums;

public enum ConnectionMethodEnum {
    SOCKET(":s"),
    RMI(":r");

    private String command;
    
    ConnectionMethodEnum(String command){
        this.command = command;
    }
    
    public static ConnectionMethodEnum parseString(String string){
        for(ConnectionMethodEnum e: values()){
            if(e.command.equals(string)){
                return e;
            }
        }
        throw new IllegalArgumentException("Oak's words echoed... There's a time and place for everything, but not now");
    }
}
