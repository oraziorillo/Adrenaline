package client.view.cli.commands;

import server.controller.RemotePlayer;
import static client.view.cli.Commands.*;

public class CommandFactory {
   private CommandFactory(){}
   public static CliCommand getCommand(String s, RemotePlayer controller, boolean gui){
         switch (s.toLowerCase().trim()){
            case MAP_SHORT: case MAP_LONG: case MAP_EXTENDED:
               return new ChooseMapCommand( controller, gui );
            case SKULLS_SHORT: case SKULLS_LONG: case SKULLS_EXTENDED:
               return new ChooseNumberOfSkullsCommand( controller, gui);
            case COLOUR_SHORT: case COLOUR_LONG: case COLOUR_EXTENDED:
               return new ChoosePcColourCommand(controller, gui);
            case RUN_LONG: case RUN_SHORT:
               return new RunAroundCommand(controller, gui);
            case GRAB_STUFF_SHORT: case GRAB_STUFF_LONG:
               return new GrabStuffCommand(controller, gui);
            case SHOOT_LONG: case SHOOT_SHORT:
               return new ShootCommand(controller, gui);
            case SQUARE_LONG: case SQUARE_SHORT:
               return new SelectSquareCommand(controller, gui);
            case GRAB_WEAPON_SHORT: case GRAB_WEAPON_LONG:
               return new ChooseWeaponOnSpawnPointCommand(controller,gui);
            case CHOOSE_WEAPON_SHORT: case CHOOSE_WEAPON_LONG:
               return new ChooseWeaponOfMineCommand(controller,gui);
            case QUIT_LONG: case QUIT_SHORT:
               return new QuitCommand(controller, gui);
            case SKIP_SHORT: case SKIP_LONG:
               return new SkipCommand(controller,gui);
            default:
               System.out.println("Unsupported command");
               return new UselessCommand();
         }
      }
   
}
