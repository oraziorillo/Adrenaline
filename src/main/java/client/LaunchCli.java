package client;

import client.controller.CliController;

public class LaunchCli {

    public static void main(String[] args) throws Exception {
        System.setProperty("java.rmi.server.hostname", args[0]);
        new CliController().run();
    }
}
