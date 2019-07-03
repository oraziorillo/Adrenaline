package client.controller.socket;

import client.view.AbstractView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import common.enums.ViewMethodsEnum;
import common.events.ModelEvent;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.lobby_events.LobbyEvent;
import common.events.lobby_events.PlayerJoinedEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import server.model.deserializers.ModelEventDeserializer;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

import static common.Constants.REGEX;

public class ClientSocketHandler implements Runnable {

    private Gson gson;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private AbstractView view;
    private BlockingQueue<String[]> buffer;


    public ClientSocketHandler(Socket socket, AbstractView view, BlockingQueue<String[]> buffer) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.view = view;
        this.buffer = buffer;
        this.gson = new GsonBuilder().registerTypeAdapter(ModelEvent.class, new ModelEventDeserializer()).create();
    }


    @Override
    public void run() {
        while (!socket.isClosed()) {
            String[] args = null;
            try {
                args = in.readLine().split(REGEX);
                handle(args);
            } catch (IllegalArgumentException notAViewMethod) {
                buffer.add(args);
            } catch (IOException e) {
                try {
                    socket.close();
                    out.close();
                    in.close();
                } catch (IOException ignore) {
                }
            }
        }
    }


    private void handle(String[] args) throws IOException {
        ViewMethodsEnum viewMethod = ViewMethodsEnum.valueOf(args[0]);
        switch (viewMethod) {
            case ACK:
                StringBuilder ack = new StringBuilder();
                for (String s : Arrays.copyOfRange(args, 1, args.length)) {
                    ack.append(s).append(System.lineSeparator());
                }
                view.ack(ack.toString());
                break;
            case ERROR:
                StringBuilder error = new StringBuilder();
                for (String s : Arrays.copyOfRange(args, 1, args.length)) {
                    error.append(s).append(System.lineSeparator());
                }
                view.error(error.toString());
                break;
            case NOTIFY_EVENT:
                LobbyEvent lobbyEvent = gson.fromJson(
                        new JsonReader(new StringReader(args[1])), PlayerJoinedEvent.class);
                view.notifyEvent(lobbyEvent);
                break;
            case ON_GAME_BOARD_UPDATE:
                GameBoardEvent gameBoardEvent = gson.fromJson(
                        new JsonReader(new StringReader(args[1])), ModelEvent.class);
                view.onGameBoardUpdate(gameBoardEvent);
                break;
            case ON_KILL_SHOT_TRACK_UPDATE:
                KillShotTrackEvent killShotTrackEvent = gson.fromJson(
                        new JsonReader(new StringReader(args[1])), ModelEvent.class);
                view.onKillShotTrackUpdate(killShotTrackEvent);
                break;
            case ON_PC_BOARD_UPDATE:
                PcBoardEvent pcBoardEvent = gson.fromJson(
                        new JsonReader(new StringReader(args[1])), ModelEvent.class);
                view.onPcBoardUpdate(pcBoardEvent);
                break;
            case ON_PC_UPDATE:
                PcEvent pcEvent = gson.fromJson(
                        new JsonReader(new StringReader(args[1])), ModelEvent.class);
                view.onPcUpdate(pcEvent);
                break;
            case ON_SQUARE_UPDATE:
                SquareEvent squareEvent = gson.fromJson(
                        new JsonReader(new StringReader(args[1])), ModelEvent.class);
                view.onSquareUpdate(squareEvent);
                break;
            default:
                throw new IllegalStateException("Unexpected command: " + viewMethod);
        }
    }
}
