package controller;

import model.Game;
import model.Pc;
import model.Tile;
import model.WeaponCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class fakeController {

    public static void main(String[] args){
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        Game currentGame= new Game();
        Pc currrentPc=currentGame.getCurrentPc();
        Tile currentTile;
        String cmd[]=new String[2];
        while (!cmd[0].equals("quit")){
            if(currentGame.getRemainigActions()==0){
                currentGame.nextTurn();
                currrentPc=currentGame.getCurrentPc();
            }
            try {
                cmd=in.readLine().trim().split(" ",2);

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            switch (cmd[0]){
                case "move":
                    String[] xy=cmd[1].split(",",2);
                    int x=Integer.parseInt(xy[0]);
                    int y=Integer.parseInt(xy[1]);
                    currentTile=currrentPc.getCurrentTile();
                    Tile targetTile=currentGame.getMap()[x][y];

                    //TODO: l'attuazione dello spostamento si potrebbe gestire nel model. lo spostamento attraverso le cardinalDirections è terribile, sarebbe meglio un setCurrentTile
                    try{
                        currrentPc.move(x,y);
                    }catch (IllegalArgumentException e){
                        e.printStackTrace();
                    }
                    /*if(!currentTile.distanceOf(3).contains(targetTile)){
                        System.out.println("Troppo distante");
                        continue;
                    }
                    currentTile.removeCharacter(currrentPc);
                    targetTile.addCharacter(currrentPc);
                    currrentPc.setCurrentTile(targetTile);*/
                    break;
                case "collect":
                    String[] cmdArgs=cmd[1].split(",");
                    if(cmdArgs.length==3){  //mi muovo prima
                        try{
                            int moveDist;
                            if(currrentPc.adrenalineLevel()>=1){
                                moveDist=2;
                            }else {
                                moveDist=1;
                            }
                            currrentPc.move(x,y,moveDist);
                        }catch (IllegalArgumentException e){
                            e.printStackTrace();
                        }
                    }
                    //raccogli
                    break;
                case "shoot":
                    cmdArgs=cmd[1].split(",",2);
                    WeaponCard weaponCard
                    do{
                        try {
                            cmd=in.readLine().split(" ");
                        } catch (IOException e) {
                            e.printStackTrace();
                            continue;
                        }
                        switch (cmd[0]){
                            case "select":

                        }
                    }while (!cmd[0].equals("shoot"));
                    break;
                    default:
                        System.out.println("Comando non riconosciuto");
                        continue;
            }
        }
    }
}
