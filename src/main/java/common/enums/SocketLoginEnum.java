package common.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum SocketLoginEnum {
   REGISTER,
   CONTAINS_PLAYER,
   LOGIN,
   ENTER_SAVED_GAME,
   SAVE_GAME(":s",":save"),
   JOIN_LOBBY(":j",":join");
   
   
   private Set<String> CliStrings = new HashSet<>();
   
   SocketLoginEnum(String... validCommands){
      CliStrings.addAll( Arrays.asList( validCommands ) );
   }
   
   public static SocketLoginEnum parseString(String s){
      for(SocketLoginEnum e: values()){
         if(e.CliStrings.contains( s ) ){
            return e;
         }
      }
      throw new IllegalArgumentException( s+"is not a recognised command" );
   }
   
   public Set<String> getCliStrings() {
      return new HashSet<>(CliStrings);
   }
}
