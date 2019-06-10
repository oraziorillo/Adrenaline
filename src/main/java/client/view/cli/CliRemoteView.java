package client.view.cli;

import common.rmi_interfaces.RemoteView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class CliRemoteView extends UnicastRemoteObject implements RemoteView {
   
   protected CliRemoteView() throws RemoteException {
   }
   
   @Override
   public void showMessage(String message) {
      System.out.println( message );
   }
   
}
