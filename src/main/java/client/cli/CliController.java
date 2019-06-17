package client.cli;

import client.AbstractClientController;
import client.cli.commands.CliCommand;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.UUID;

class CliController implements AbstractClientController, Runnable {
    
    
    private final transient CliView view = new CliView();
    private final transient RemoteLoginController loginController;
    private final transient RemotePlayer player;
    
    
    CliController() throws IOException {
        super();
        UUID token;
        this.loginController = view.aquireConnection();
        boolean wantsRegister = view.wantsRegister();
        if(wantsRegister){
            do {
                token = registerWithNewUsername();
            }while (token == null);
            view.ack( "This is your token: " + token + "\n\nUse it to login next time\n" );
            player = loginController.login( token,view );
        }else {
            RemotePlayer tmpPlayer;
            do{
                token = view.acquireToken();
                tmpPlayer = loginController.login( token, view );
            }while (tmpPlayer == null);
            player = tmpPlayer;
        }
    }

    private UUID registerWithNewUsername() throws IOException {
        String username = view.acquireUnregisteredUsername();
        return loginController.register( username,view );
    }
    
    @Override
    public void run() {
        CliCommand command = view.getCommand(player);
        try {
            command.execute();
        } catch ( IOException e ) {
            view.displayErrorAndExit( "Server unreachable" );
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
