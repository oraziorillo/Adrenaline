package client.controller.socket;

import client.view.AbstractView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import common.enums.ViewMethodsEnum;
import common.events.ModelEvent;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;
import server.exceptions.PlayerAlreadyLoggedInException;
import server.model.deserializers.ModelEventDeserializer;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import static common.Constants.*;
import static common.enums.ControllerMethodsEnum.*;

public class ClientSocketHandler implements Runnable, RemoteLoginController, RemotePlayer {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private AbstractView view;
    private BlockingQueue<String[]> buffer = new PriorityBlockingQueue<>(10, (a1, a2) -> a1[0].compareToIgnoreCase(a2[0]));
    private UUID token;


    public ClientSocketHandler(Socket socket, AbstractView view) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.view = view;
    }


    @Override
    public void run() {
        while (!socket.isClosed()) {
            String[] args = null;
            try {
                args = in.readLine().split(REGEX);
                parseViewMethods(args);
            } catch (IllegalArgumentException notAViewMethod) {
                buffer.add(args);
            } catch (IOException e) {
                try {
                    quit();
                    socket.close();
                    out.close();
                    in.close();
                } catch (IOException ignore) {
                }
            }

        }
    }


    private void parseViewMethods(String[] args) throws IOException {
        ViewMethodsEnum viewMethod = ViewMethodsEnum.valueOf(args[0]);
        switch (viewMethod) {
            case ACK:
            case ERROR:
                StringBuilder builder = new StringBuilder();
                for (String s : Arrays.copyOfRange(args, 1, args.length)) {
                    builder.append(s).append(System.lineSeparator());
                }
                view.ack(builder.toString());
                break;
            case ON_GAME_BOARD_UPDATE:
                GameBoardEvent gameBoardEvent = customGson().fromJson(
                        new JsonReader(new StringReader(args[1])), ModelEvent.class);
                view.onGameBoardUpdate(gameBoardEvent);
                break;
            case ON_KILL_SHOT_TRACK_UPDATE:
                KillShotTrackEvent killShotTrackEvent = customGson().fromJson(
                        new JsonReader(new StringReader(args[1])), ModelEvent.class);
                view.onKillShotTrackUpdate(killShotTrackEvent);
                break;
            case ON_PC_BOARD_UPDATE:
                PcBoardEvent pcBoardEvent = customGson().fromJson(
                        new JsonReader(new StringReader(args[1])), ModelEvent.class);
                view.onPcBoardUpdate(pcBoardEvent);
                break;
            case ON_PC_UPDATE:
                PcEvent pcEvent = customGson().fromJson(
                        new JsonReader(new StringReader(args[1])), ModelEvent.class);
                view.onPcUpdate(pcEvent);
                break;
            case ON_SQUARE_UPDATE:
                SquareEvent squareEvent = customGson().fromJson(
                        new JsonReader(new StringReader(args[1])), ModelEvent.class);
                view.onSquareUpdate(squareEvent);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewMethod);
        }
    }


    private Gson customGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ModelEvent.class, new ModelEventDeserializer());
        return gsonBuilder.create();
    }


    //LoginController

    @Override
    public synchronized UUID register(String username, RemoteView view) {
        out.println(REGISTER.toString() + REGEX + username);
        out.flush();
        String stringToken;
        try {
            stringToken = buffer.take()[0];
            return UUID.fromString(stringToken);
        } catch (InterruptedException | IllegalArgumentException invalidToken) {
            this.view.printMessage("Connection issues: " + invalidToken.getMessage());
            return null;
        }
    }


    @Override
    public synchronized RemotePlayer login(UUID token, RemoteView view) throws IOException, PlayerAlreadyLoggedInException {
        if (token != null) {
            this.token = token;
            new Thread(this).start();
            out.println(LOGIN + REGEX + token);
            out.flush();
            try {
                String outcome = buffer.take()[0];
                if (outcome.equals(SUCCESS))
                    return this;
                else if (outcome.equals(FAIL)){
                    throw new PlayerAlreadyLoggedInException();
                }
            } catch (InterruptedException e) {
                this.view.printMessage("Connection issues: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public synchronized void joinLobby(UUID token) {
        out.println(JOIN_LOBBY.toString() + REGEX + token);
        out.flush();
    }


    @Override
    public synchronized void chooseMap(int n) {
        out.println(CHOOSE_MAP.toString() + REGEX + n);
        out.flush();
    }


    @Override
    public synchronized void chooseNumberOfSkulls(int n) {
        out.println(CHOOSE_NUMBER_OF_SKULLS.toString() + REGEX + n);
        out.flush();
    }


    @Override
    public void choosePcColour(String colour) {
        out.println(CHOOSE_PC_COLOUR + REGEX + colour);
        out.flush();
    }


    @Override
    public void runAround() {
        out.println(RUN_AROUND);
        out.flush();
    }


    @Override
    public void grabStuff() {
        out.println(GRAB_STUFF);
        out.flush();
    }


    @Override
    public void usePowerUp() {
        out.println(USE_POWER_UP);
        out.flush();
    }


    @Override
    public void shootPeople() {
        out.println(SHOOT_PEOPLE);
        out.flush();
    }


    @Override
    public void chooseSquare(int x, int y) {
        out.println(CHOOSE_SQUARE + REGEX + x + REGEX + y);
        out.flush();
    }


    @Override
    public void choosePowerUp(int index) {
        out.println(CHOOSE_POWER_UP + REGEX + index);
        out.flush();
    }


    @Override
    public void chooseWeaponOnSpawnPoint(int n) {
        out.println(GRAB_WEAPON_ON_SPAWN_POINT + REGEX + n);
        out.flush();
    }


    @Override
    public void chooseWeaponOfMine(int n) {
        out.println(CHOOSE_WEAPON_OF_MINE + REGEX + n);
        out.flush();
    }


    @Override
    public void switchFireMode() {
        out.println(SWITCH_FIRE_MODE);
        out.flush();
    }


    @Override
    public void chooseUpgrade(int index) {
        out.println(CHOOSE_UPGRADE + REGEX + index);
        out.flush();
    }


    @Override
    public void chooseAsynchronousEffectOrder(boolean beforeBasicEffect) {
        out.println(CHOOSE_ASYNCHRONOUS_EFFECT_ORDER + REGEX + beforeBasicEffect);
        out.flush();
    }


    @Override
    public void chooseDirection(int index) {
        out.println(CHOOSE_DIRECTION + REGEX + index);
        out.flush();
    }


    @Override
    public void skip() {
        out.println(SKIP);
        out.flush();
    }


    @Override
    public void undo() {
        out.println(UNDO);
        out.flush();
    }


    @Override
    public void ok() {
        out.println(OK);
        out.flush();
    }


    @Override
    public void reload() {
        out.println(RELOAD);
        out.flush();
    }


    @Override
    public void pass() {
        out.println(PASS);
        out.flush();
    }


    @Override
    public void quit() {
        out.println(QUIT);
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException ignored) {
        } //if already closed it's ok
    }

    @Override
    public boolean isConnected() {
        return !socket.isClosed();
    }

    @Override
    public UUID getToken() {
        return this.token;
    }
    
    @Override
    public void sendMessage(String s) throws IOException {
        //TODO: metodo per mandare messaggi in chat
    }
}
