package controller;

import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class fakeController {

    public static void main(String[] args){
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        Game currentGame= new Game("jSonName");
        Pc currentPc=currentGame.getCurrentPc();
        String[] cmd=new String[2];
        String[] arguments;
        while (!cmd[0].equals("quit")){
            if(currentGame.getRemainigActions()==0){
                currentGame.nextTurn();
                currentPc=currentGame.getCurrentPc();
            }
            try {
                cmd=in.readLine().trim().split(" ",2);
                arguments=cmd[1].split(",");
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            switch (cmd[0]){
                case "move":
                        move(arguments,currentPc);
                    break;
                case "collect":
                        collect(arguments,currentPc);
                    break;
                case "shoot":
                    shoot(arguments,currentPc);
                    break;
                case "powerup":
                    powerup(arguments,currentPc);
                    break;
                    default:
                        System.out.println("Comando non riconosciuto");
            }
        }
    }

    private static void move(String[] args, Pc currentPc){
        if(args.length<2){
            throw new IllegalArgumentException("Comando non valido");
        }
        int x=Integer.parseInt(args[0]);
        int y=Integer.parseInt(args[1]);
        try{
            currentPc.move(x,y,3);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    private static void collect(String[] args,Pc currentPc){
        int code=0;
        int x=0;
        int y=0;
        switch (args.length){
            case 1:
                code=Integer.parseInt(args[0]);
                break;
            case 2:
                x=Integer.parseInt(args[0]);
                y=Integer.parseInt(args[1]);
                break;
            case 3:
                code=Integer.parseInt(args[0]);
                x=Integer.parseInt(args[1]);
                y=Integer.parseInt(args[2]);
                break;
                default:
                    throw new IllegalArgumentException("Comando non valido");
        }
        if(x<0||y<0){
            throw new IllegalArgumentException("Comando non valido");
        }
        if(x>0||y>0){
            try{
                int moveDist;
                if(currentPc.adrenalineLevel()>=1){
                    moveDist=2;
                }else {
                    moveDist=1;
                }
                currentPc.move(x,y,moveDist);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        }
        currentPc.getCurrTile().collect(currentPc,code);
    }

    private static void shoot(String[] args, Pc currentPc){
        //TODO
    }

    private static void powerup(String[] args,Pc currentPc){
        int powerupIndex=Integer.parseInt(args[0]);
        PowerUpCard powerup=currentPc.getPowerUps().get(powerupIndex);
        powerup.useEffect();
    }

}
