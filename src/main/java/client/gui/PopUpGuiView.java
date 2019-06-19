package client.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
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
            infos.setContentText( "The king of the arena says:" );
            infos.setResizable( true );
            TextArea messageArea = new TextArea(message);
            messageArea.setWrapText( true );
            infos.getDialogPane().setExpandableContent( messageArea );
            infos.show();
        } );
    }
}
