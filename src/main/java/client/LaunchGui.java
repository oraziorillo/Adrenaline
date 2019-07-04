package client;

import client.controller.GuiController;
import javafx.application.Application;

import static javafx.application.Application.launch;

public class LaunchGui {
    public static void main(String[] args) {
        System.setProperty("java.server.hostname", args[0]);
        Application.launch( GuiController.class );
    }
}