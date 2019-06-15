package client.view.cli.commands;

import common.enums.interfaces_names.SocketPlayerEnum;
import common.remote_interfaces.RemotePlayer;

public class CommandFactory {


   private CommandFactory(){}


   public static CliCommand getCommand(String s, RemotePlayer controller){
      try{
         return parsePlayer( s,controller );
      }catch ( IllegalArgumentException ignored ){
         return new UselessCommand();
      }//Command is invalid, ignore it
   }
   
   
   private static CliCommand parsePlayer(String s,RemotePlayer controller){
      switch (SocketPlayerEnum.parseString( s.toLowerCase() )){//TODO: aggiungi i comandi restanti
      
         case CHOOSE_MAP:
            return new ChooseMapCommand(controller);
      
         case CHOOSE_NUMBER_OF_SKULLS:
            return new ChooseNumberOfSkullsCommand(controller);
      
         case CHOOSE_PC_COLOUR:
            return new ChoosePcColourCommand(controller);
      
         case RUN_AROUND:
            return new RunAroundCommand(controller);
      
         case GRAB_STUFF:
            return new GrabStuffCommand(controller);
      
         case SHOOT_PEOPLE:
            return new ShootCommand(controller);

         case USE_POWERUP:
            return new UsePowerUpCommand(controller);
      
         case SELECT_SQUARE:
            return new SelectSquareCommand(controller);

         case CHOOSE_POWERUP:
            return new ChoosePowerUpCommand(controller);
      
         case GRAB_WEAPON:
            return new ChooseWeaponOnSpawnPointCommand(controller);
      
         case CHOOSE_WEAPON:
            return new ChooseWeaponOfMineCommand(controller);

         case SWITCH_FIREMODE:
            return new SwitchFireModeCommand(controller);

         case RELOAD:
            return new ReloadCommand(controller);

         case OK:
            return new OKCommand(controller);
      
         case QUIT:
            return new QuitCommand(controller);
      
         case SKIP:
            return new SkipCommand(controller);

         default:
            return new UselessCommand();
      }
   }
   
   
}
