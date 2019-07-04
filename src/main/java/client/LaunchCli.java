package client;

import client.controller.CliController;

public class LaunchCli {

    public static void main(String[] args) throws Exception {

        String ipAddress = ClientPropertyLoader.getInstance().getMyIPAddress();

        System.setProperty("java.rmi.server.hostname", ipAddress);
        new CliController().run();
    }
}
