package client.cli;

import client.ClientView;
import common.enums.interfaces_names.SocketPlayerEnum;
import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

public abstract class CommandParser {
    public static void executeCommand(String[] commands, RemotePlayer player, ClientView view) throws IOException {
        switch (SocketPlayerEnum.parseString( commands[0] )) {//TODO: aggiungi i comandi restanti
            case CHOOSE_MAP:
                player.chooseMap( Integer.parseInt( commands[1] ) );
                break;
            case CHOOSE_NUMBER_OF_SKULLS:
                player.chooseNumberOfSkulls( Integer.parseInt( commands[1] ) );
                break;
            case CHOOSE_PC_COLOUR:
                player.choosePcColour( commands[1] );
                break;
            case RUN_AROUND:
                player.runAround();
                break;
            case GRAB_STUFF:
                player.grabStuff();
                break;
            case SHOOT_PEOPLE:
                player.shootPeople();
                break;
            case USE_POWERUP:
                player.usePowerUp();
                break;
            case CHOOSE_SQUARE:
                player.chooseSquare( Integer.parseInt( commands[1] ), Integer.parseInt( commands[1] ) );
                break;
            case CHOOSE_POWERUP:
                player.choosePowerUp( Integer.parseInt( commands[1] ) );
                break;
            case GRAB_WEAPON_ON_SPAWNPOINT:
                player.chooseWeaponOnSpawnPoint( Integer.parseInt( commands[1] ) );
                break;
            case CHOOSE_WEAPON_OF_MINE:
                player.chooseWeaponOfMine( Integer.parseInt( commands[1] ) );
                break;
            case SWITCH_FIREMODE:
                player.switchFireMode();
                break;
            case RELOAD:
                player.reload();
                break;
            case OK:
                player.ok();
                break;
            case QUIT:
                player.quit();
                break;
            case SKIP:
                player.skip();
                break;
            default:
                StringBuilder errorMessage = new StringBuilder("Unrecognized command: "+commands[0]+" with arguments: ");
                String separator = ", ";
                for(int i=1;i<commands.length;i++){
                    errorMessage.append( commands[i] ).append( separator );
                }
                throw new IllegalArgumentException( errorMessage.toString() );
        }
    }
}
