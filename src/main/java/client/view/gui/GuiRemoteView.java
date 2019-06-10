package client.view.gui;

import common.rmi_interfaces.RemoteView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//TODO: al momento la gui è troppo indietro
class GuiRemoteView extends UnicastRemoteObject implements RemoteView {
   protected GuiRemoteView() throws RemoteException {
   }
   
   @Override
   public void showMessage(String message) {
   
   }
}
