package client.view.gui;

import common.events.ModelEvent;
import common.events.ModelEventListener;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

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
    public ModelEventListener getListener() {
        return this;
    }


    @Override
    public void modelEvent(ModelEvent event) {

    }
}
