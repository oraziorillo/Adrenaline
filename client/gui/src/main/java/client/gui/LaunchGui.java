package client.gui;

import client.ClientPropertyLoader;
import client.gui.controller.GuiController;
import javafx.application.Application;

public class LaunchGui {
    public static void main(String[] args) {

        String ipAddress = ClientPropertyLoader.getInstance().getMyIPAddress();

        System.setProperty("java.rmi.server.hostname", ipAddress);
        Application.launch( GuiController.class );
    }
}