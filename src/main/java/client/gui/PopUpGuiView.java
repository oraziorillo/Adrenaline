package client.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.rmi.RemoteException;

import static javafx.application.Platform.runLater;

public class PopUpGuiView extends GuiView {
    
    
    public PopUpGuiView() throws RemoteException {
    }
    
    @Override
    public void ack(String message) throws RemoteException {
        runLater( () -> {
            Alert infos = new Alert( Alert.AlertType.INFORMATION );
            infos.setTitle( "infos" );
            infos.setHeaderText( null );
            infos.setContentText( message );
            infos.setResizable( true );
            infos.show();
        } );
    }
}
