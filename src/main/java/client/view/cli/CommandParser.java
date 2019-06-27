package client.view.cli;

import client.view.AbstractView;
import common.enums.SocketEnum;
import common.remote_interfaces.RemotePlayer;
import java.io.IOException;

public abstract class CommandParser {
    public static void executeCommand(String[] commands, RemotePlayer player, AbstractView view) throws IOException {
        switch (SocketEnum.parseString( commands[0] )) {
            //TODO: aggiungi i comandi restanti
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
            case USE_POWER_UP:
                player.usePowerUp();
                break;
            case CHOOSE_SQUARE:
                player.chooseSquare( Integer.parseInt( commands[1] ), Integer.parseInt( commands[1] ) );
                break;
            case CHOOSE_POWER_UP:
                player.choosePowerUp( Integer.parseInt( commands[1] ) );
                break;
            case GRAB_WEAPON_ON_SPAWN_POINT:
                player.chooseWeaponOnSpawnPoint( Integer.parseInt( commands[1] ) );
                break;
            case CHOOSE_WEAPON_OF_MINE:
                player.chooseWeaponOfMine( Integer.parseInt( commands[1] ) );
                break;
            case SWITCH_FIRE_MODE:
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
            case HELP:
                printHelp();
            default:
                StringBuilder errorMessage = new StringBuilder("Unrecognized command: "+commands[0]+" with arguments: ");
                String separator = ", ";
                for(int i=1;i<commands.length;i++){
                    errorMessage.append( commands[i] ).append( separator );
                }
                throw new IllegalArgumentException( errorMessage.toString() );
        }
    }

    private static void printHelp(){
        System.out.println("m\t" + "map\t" + "choose map\n" +
                        "sk\t" + "skulls\t" + "choose number of skulls\n" +
                        "choose colour\t" + "colour\t" + "col\n" +
                        "choose colour\t" + "colour\t" + "col\n" +
                        "c\t" + "comment\n" +
                        "run\t" +"r\n" +
                        "grab stuff\t" + "gs\n" +
                        "shoot\t" + "s\n" +
                        "pw\t" + "powerup\n" +
                        "ss\t" + "select square\n" +
                        "grab weapon\t" + "gw\t" + "choose weapon\n" +
                        "cw\n" +
                        "sf\t" + "switch\t" + "switch firemode\n" +
                        "ch\t" + "choose powerup\n" +
                        "selectUpgrade\t" + "u\n" +
                        "asynch\t" + "ca\n" +
                        "choose direction\t" + "choose dir\n" +
                        "ok\n" +
                        "re\t" + "reload\n" +
                        "p\t" + "pass\n" +
                        "skip\n" +
                        "pw\t" + "power up\n" +
                        "ch\t" + "choose power up\n" +
                        "ru\t" + "remove selectUpgrade\n" +
                        "undo\n" +
                        "quit\t" + "q");
    }
}
