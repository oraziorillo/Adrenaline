package client;

import client.controller.GuiController;
import javafx.application.Application;

import static javafx.application.Application.launch;

public class LaunchGui {
    public static void main(String[] args) {
        Application.launch( GuiController.class );
    }
}