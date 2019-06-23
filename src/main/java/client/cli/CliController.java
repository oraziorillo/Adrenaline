package client.cli;

import client.AbstractClientController;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

class CliController extends AbstractClientController  {
    
    CliController() throws IOException {
        super(new CliView());
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        try{
            String cmd = view.requestString( "Insert command" );
            String[] commands = cmd.split( " " );
            CommandParser.executeCommand( commands, player, view );
        } catch ( IOException serverUnreachable ) {
            try{view.displayErrorAndExit( "Server unreachable" );}catch ( RemoteException ignored ){}
        }catch ( IllegalArgumentException unsupportedCommand ){
            System.out.println(unsupportedCommand.getMessage());
        }
    }
    
    public boolean isRunnable() {
        try {
            return player.isConnected();
        } catch ( RemoteException e ) {
            return false;
        }
    }
}
