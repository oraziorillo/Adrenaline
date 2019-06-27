package client.controller;

import client.view.cli.CliView;
import client.view.cli.CommandParser;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class CliController extends AbstractClientController  {
    
    public CliController() throws IOException {
        super(new CliView());
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        try{
            String cmd = view.requestString("Insert command");
            String[] commands = cmd.split("-");
            CommandParser.executeCommand( commands, player, view );
        } catch ( IOException serverUnreachable ) {
            try{view.error( "Server unreachable" );}
            catch ( RemoteException ignored ){}
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
