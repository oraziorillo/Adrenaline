package client.view.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static javafx.application.Platform.runLater;

public class PopUpGuiView extends GuiView {
    
    /**
     * contains every alert displayed by this and still not closed
     */
    private final ArrayList<String> pendingAcks = new ArrayList<>();
    private final ArrayList<Alert> visibleAlerts = new ArrayList<>();
    //TODO: usa una coda che permetta di rimuovere un oggetto a caso dato il riferimento
    
    public PopUpGuiView() throws RemoteException {
    }
    
    /**
     * Builds an alert containing the ack and shows it
     * @param message the message of the ack
     */
    @Override
    public synchronized void ack(String message) {
        runLater( () -> {
            Alert infos = new Alert( Alert.AlertType.INFORMATION );
            infos.setTitle( "infos" );
            infos.setHeaderText( null );
            infos.setContentText( "The king of the arena says:" );
            infos.setResizable( true );
            TextArea messageArea = new TextArea(message);
            messageArea.setWrapText( true );
            messageArea.setEditable( false );
            infos.getDialogPane().setExpandableContent( messageArea );
            infos.getDialogPane().setExpanded( true );
            infos.show();
            infos.setOnCloseRequest( e-> {pendingAcks.remove( message );visibleAlerts.remove( infos );} );
            pendingAcks.add( message );
            visibleAlerts.add( infos );
        } );
    }

    @Override
    public synchronized Collection<String> getPendingAcks() {
        Collection<String> returned = new HashSet<>();
        for(Alert a:visibleAlerts){
            returned.add( ((TextArea)a.getDialogPane().getExpandableContent()).getText() );
            runLater( a::close );
        }
        return returned;
    }


    @Override
    public PropertyChangeListener getListener() {
        return this;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
