package client;

import client.controller.SocketLoginController;
import client.view.RemoteView;
import client.view.cli.commands.CliCommand;
import client.view.cli.commands.CommandFactory;
import server.RemoteLoginController;
import server.controller.RemotePlayer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.UUID;

public class CliClient implements RemoteView {


   //TODO: notifica l'utente quando viene inserito nella partita.
   //TODO: gestisci la risposta al metodo eseguito
   while (true) {
      out.println("Insert command: ");
      CliCommand command = CommandFactory.getCommand(in.next(), player, false);
      command.execute();
      }
   }


   public void init() {
      initInOut();
   }




}
