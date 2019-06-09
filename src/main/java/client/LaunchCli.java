package client;

import client.view.cli.CliView;

import java.io.IOException;

public class LaunchCli {

    public static void main(String[] args) throws IOException {

        CliClient client = new CliClient();

        CliView view = new CliView();

        view.getConnection();

    }
}
