package client.cli;

import client.cli.controller.CliController;
import client.ClientPropertyLoader;

public class LaunchCli {

    public static void main(String[] args) throws Exception {

        String ipAddress = ClientPropertyLoader.getInstance().getMyIPAddress();

        System.setProperty("java.rmi.server.hostname", ipAddress);
        new CliController().run();
    }
}
