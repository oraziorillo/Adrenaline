package client.view.cli;

import client.controller.socket.ClientSocketHandler;
import client.view.AbstractView;
import client.view.InputReader;
import common.dto_model.PcBoardDTO;
import common.enums.ConnectionsEnum;
import common.enums.PcColourEnum;
import common.remote_interfaces.ModelChangeListener;
import common.remote_interfaces.RemoteLoginController;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class CliView extends UnicastRemoteObject implements AbstractView {
    private transient InputReader inputReader = new CliInputReader();
    
    public CliView() throws RemoteException {
    }
    
    @Override
    public RemoteLoginController acquireConnection(){
        ConnectionsEnum cmd = null;
        do {
            try {
                cmd = ConnectionsEnum.parseString( inputReader.requestString( "Choose a connection method:" + System.lineSeparator() + " - (s)ocket" + System.lineSeparator() + " - (r)mi" + System.lineSeparator() ).toLowerCase());
            }catch ( IllegalArgumentException e ){
                System.out.println(e.getMessage());
            }
        } while (cmd == null);
        switch (cmd){
            case Socket:
                try {
                    Socket socket = new Socket( HOST, SOCKET_PORT );
                    ClientSocketHandler handler = new ClientSocketHandler( socket );
                    new Thread( handler ).start();
                    return handler;
                } catch (IOException e ) {
                    error( "Server unreachable" );
                }
            case Rmi:
                try {
                    Registry registry = LocateRegistry.getRegistry( HOST, RMI_PORT );
                    return ( RemoteLoginController ) registry.lookup( "LoginController" );
                } catch ( RemoteException | NotBoundException e ) {
                    error( "Server unreachable" );
                }
            default:
                    throw new IllegalArgumentException( cmd + "is not supported yet" );
        }
    }
    
    @Override
    public boolean wantsRegister(){
        HashSet<String> yesAnswers = new HashSet<>( Arrays.asList("y", "yes"));
        HashSet<String> noAnswers = new HashSet<>(Arrays.asList("n", "no"));
        String cmd;
        do{
            cmd = inputReader.requestString("Do you have a login token?"+System.lineSeparator()).toLowerCase();
        }while (!yesAnswers.contains( cmd ) && ! noAnswers.contains( cmd ));
        return noAnswers.contains( cmd );
    }
    
    @Override
    public String acquireUsername() {
        return inputReader.requestString("Insert an username");
    }
    
    @Override
    public UUID acquireToken() {
        return UUID.fromString(inputReader.requestString("Insert your token"));
    }
    
    @Override
    public String requestString(String message) {
        return inputReader.requestString( message );
    }
    
    @Override
    public void error(String msg) {
        inputReader.requestString( msg + System.lineSeparator() + "Press enter to exit." );
        System.exit( 1 );
    }
    
    /**
     * Cause every message is immediatly displayed in the cli, no acks are pending
     * @return an empty Collection
     */
    @Override
    public Collection<String> getPendingAcks() {
        return new HashSet<>();
    }
    
    @Override
    public void ack(String message) {
        System.out.println(message);
    }

    @Override
    public ModelChangeListener getListener() {
        return this;
    }

    @Override
    public void onSquareTargetableChange(int row, int col, boolean newValue) {

    }

    @Override
    public void onMovement(PcColourEnum pc, int oldRow, int oldCol, int newRow, int newCol) {

    }

    @Override
    public void onWeaponCollect(PcColourEnum pc, int droppedWeapon, int grabbedWeapon) {

    }

    @Override
    public void onAmmoCollect(PcColourEnum pc) {

    }

    @Override
    public void onDrawPowerUp(PcColourEnum pc, int newIndex) {

    }

    @Override
    public void onDiscardPowerUp(PcColourEnum pc, int oldIndex) {

    }

    @Override
    public void onPcBoardChange(PcBoardDTO newPcBoard) {

    }

    @Override
    public void onRefill(int typeOfDeck, int row, int col) {

    }

    @Override
    public void onKill(PcColourEnum shooter, PcColourEnum killed, boolean isOverkill) {

    }

    @Override
    public void onSpawn(PcColourEnum pc, int newRow, int newCol) {

    }

    @Override
    public void onAdrenaline(int level) {

    }

    @Override
    public void onFinalFrenzy() {

    }
}
