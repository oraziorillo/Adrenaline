package client;

import client.controller.GuiController;
import javafx.application.Application;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import static javafx.application.Application.launch;

public class LaunchGui {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        String ipAddress = InetAddress.getLocalHost().toString();
    
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                if (i.isSiteLocalAddress())
                    ipAddress = i.getHostAddress();
            }
        }
    
        System.setProperty("java.rmi.server.hostname", ipAddress);
        Application.launch( GuiController.class );
    }
}