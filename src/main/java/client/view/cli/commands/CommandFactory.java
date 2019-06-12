package client.view.cli.commands;

import common.remote_interfaces.RemotePlayer;
import static client.view.cli.CommandsStrings.*;

public class CommandFactory {


   private CommandFactory(){}


   public static CliCommand getCommand(String s, RemotePlayer controller){

         switch (s.toLowerCase().trim()){

            case MAP_SHORT: case MAP_LONG: case MAP_EXTENDED:
               return new ChooseMapCommand( controller );

            case SKULLS_SHORT: case SKULLS_LONG: case SKULLS_EXTENDED:
               return new ChooseNumberOfSkullsCommand( controller);

            case COLOUR_SHORT: case COLOUR_LONG: case COLOUR_EXTENDED:
               return new ChoosePcColourCommand(controller);

            case RUN_LONG: case RUN_SHORT:
               return new RunAroundCommand(controller);

            case GRAB_STUFF_SHORT: case GRAB_STUFF_LONG:
               return new GrabStuffCommand(controller);

            case SHOOT_LONG: case SHOOT_SHORT:
               return new ShootCommand(controller);

            case SQUARE_LONG: case SQUARE_SHORT:
               return new SelectSquareCommand(controller);

            case GRAB_WEAPON_SHORT: case GRAB_WEAPON_LONG:
               return new ChooseWeaponOnSpawnPointCommand(controller);

            case CHOOSE_WEAPON_SHORT: case CHOOSE_WEAPON_LONG:
               return new ChooseWeaponOfMineCommand(controller);

            case QUIT_LONG: case QUIT_SHORT:
               return new QuitCommand(controller);

            case SKIP_SHORT: case SKIP_LONG:
               return new SkipCommand(controller);
            default:
               System.out.println("Illegal command");
               return new UselessCommand();
         }
      }
}
