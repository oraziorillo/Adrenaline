package client.cli;

import java.io.IOException;
import java.util.Scanner;

public class LaunchCli {

    public static void main(String[] args)  {
        CliController clientController = null;
        try {
            clientController = new CliController();
        }catch ( IOException e ){
            System.out.println("Server unreachable. Press enter to exit");
            e.printStackTrace();
            new Scanner( System.in ).next();
            System.exit( 1);
        }
        while (clientController.isRunnable()) {
            clientController.run();
        }
    }
}
