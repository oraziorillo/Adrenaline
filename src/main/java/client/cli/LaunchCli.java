package client.cli;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class LaunchCli {

    public static void main(String[] args) throws Exception {   //Exception is declared because AbstractClientController inherits from application, but no exceptions are really thrown
        CliController clientController = null;
        try {
            clientController = new CliController();
        }catch ( RemoteException e ){
            System.out.println("Possible rmi issue");
            e.printStackTrace();
            System.exit( 1 );
        }catch ( IOException e ){
            System.out.println("Server unreachable. Press enter to exit");
            new Scanner( System.in ).next();
            System.exit( 1);
        }
        while (clientController.isRunnable()) {
            clientController.start(null);
        }
    }
}
