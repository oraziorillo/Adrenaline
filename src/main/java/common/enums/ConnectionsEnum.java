package common.enums;

import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum ConnectionsEnum {
    Socket("s","socket"),
    Rmi("r","rmi");
    
    private final Collection<String> recognisedStrings;
    
    ConnectionsEnum(String... recognisedStrings){
        this.recognisedStrings = Arrays.asList( recognisedStrings );
    }
    
    public static ConnectionsEnum parseString(String string){
        for(ConnectionsEnum e: values()){
            if(e.recognisedStrings.contains( string )){
                return e;
            }
        }
        throw new IllegalArgumentException( "Illegal command" );
    }
}
