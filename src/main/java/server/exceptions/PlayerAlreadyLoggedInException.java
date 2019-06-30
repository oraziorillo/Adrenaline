package server.exceptions;

public class PlayerAlreadyLoggedInException extends Exception {

    public PlayerAlreadyLoggedInException(){
        super("You are not a Java Object! You cannot clone yourself!");
    }
}
