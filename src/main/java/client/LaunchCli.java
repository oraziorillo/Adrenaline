package client;

import client.controller.CliController;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class LaunchCli {

    public static void main(String[] args) throws Exception {
        String ipAddress = InetAddress.getLocalHost().toString();

        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                if (i.isSiteLocalAddress())
                    ipAddress = i.getHostAddress();
            }
        }

        System.setProperty("java.rmi.server.hostname", ipAddress);
        new CliController().run();
    }
}
