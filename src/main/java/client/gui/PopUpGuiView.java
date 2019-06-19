package client.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

public class PopUpGuiView extends GuiView {
    
    
    @Override
    public void ack(String message) {
        Alert infos = new Alert( Alert.AlertType.INFORMATION );
        infos.setTitle( "infos" );
        infos.setHeaderText( null );
        infos.setContentText( message );
        infos.setResizable( true );
        infos.show();
    }
}
