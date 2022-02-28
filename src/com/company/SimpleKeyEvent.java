package com.company; //Proper package, please :D

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Class name doesn't say what it is responsible for
//Other thing is, I would separate input from logic/map - SRP
public class SimpleKeyEvent implements KeyListener {
    //MAP
    int a; //what is a? If it is width/height - it could be even const.
    int b; //what is b? If it is width/height - it could be even const.
    int[][] gameMap;
    int posX; //You can write classes in java - they are nice! You could have something like Position class, which will hold X and Y.
    int posY; //^ And I think, that you don't need this variable be a class variable, you are using it in one place only (and then pass it to functions in same place) [X]
    int player; //int isn't very good type for that, I would prefer to use enum here.

    SimpleKeyEvent(){
        //You could initialize these variables along with definition
        a = 7;
        b = 9;
        gameMap = createMap(a, b); // besides this one ofc
        player = 1;
        // Pamietac, ze Y liczymy od 0, a X od 1 - why?
        posY = 0;
        posX = 1;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // I would move this to separate method (possibly with direction as param, so it could be reused in next if)
            posX--;
            if(posX < 1){ //why 1, and not 0?
                posX = 7;
            }
            System.out.print(posX); //DRY
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            posX++;
            if(posX > 7){
                posX = 1; //why 1, and not 0?
            }
            System.out.print(posX); //DRY
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Again - move this to separate method. Code will be a lot cleaner.
            posY = returnPositionY(posX, gameMap, a); //[X]
            placeToken(posX, posY, player, gameMap);
            if(checkLastTokenIfWin(posX, posY, gameMap)){
                System.out.print("Gracz " + player + " wygral!");
                System.exit(0);
            }
            player = changePlayer(player);
            printMap(gameMap, a, b);
            posX = 1; //Why 1? It could be a const called StartXPosition or something.
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public int[][] createMap(int a, int b) {
        int[][] gameMap = new int[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                gameMap[i][j] = 0; //what is 0?
            }
        }
        for (int i = 0; i < b; i++) {
            gameMap[a - 1][i] = 3; //what is 3? I would rather use enum for that
        }
        return gameMap;
    }

    // a? b? width? height?
    public void printMap(int[][] gameMap, int a, int b) {
        //I would print empty line here - now it looks weird if you move and place token.
        for (int i = 0; i < a - 1; i++) {
            System.out.print("|");
            for (int j = 1; j < b - 1; j++) { // j = 1? Why not 0?
                //switch case ?
                if (gameMap[i][j] == 1) { //what is 1?
                    System.out.print("z");
                }
                if (gameMap[i][j] == 2) { //what is 2?
                    System.out.print("x");
                }
                if (gameMap[i][j] == 0) { //what is 0?
                    System.out.print("0");
                }
                //And you don't even check for that cryptic '3'?
                System.out.print("|");
            }
            System.out.println();
            System.out.print("_____________");
            System.out.println();
        }
    }

    // a?
    public int returnPositionY(int positionX, int[][] gameMap, int a) {
        int positionY = 0;
        while (positionY < a){
            if(gameMap[positionY][positionX] != 0 && positionY > 0){
                return positionY;
            }
            positionY++;
        }
        return positionY;
    }

    // Why gameMap is passed as param, if it is class variable?
    public void placeToken(int positionX, int positionY, int player, int[][] gameMap){
        gameMap[positionY - 1][positionX] = player;
    }

    public boolean checkLastTokenIfWin(int positionX, int positionY, int[][] gameMap){
        positionY = positionY - 1;
        int player = gameMap[positionY][positionX]; //this variable hides class variable with the same name FYI. I am recommending SonarLint plugin for IntelliJ, it catches such things (and even more!)
        if(gameMap[positionY][positionX - 1] == player){
            return checkForWin(positionY, positionY, positionX, positionX - 1, player, gameMap);

        }
        if(gameMap[positionY + 1][positionX - 1] == player){
            return checkForWin(positionY, positionY + 1, positionX, positionX - 1, player, gameMap);

        }
        if(gameMap[positionY + 1][positionX] == player){
            return checkForWin(positionY, positionY + 1, positionX, positionX, player, gameMap);

        }
        if(gameMap[positionY + 1][positionX + 1] == player){
            return checkForWin(positionY, positionY + 1, positionX, positionX + 1, player, gameMap);

        }
        if(gameMap[positionY][positionX + 1] == player){
            return checkForWin(positionY, positionY, positionX, positionX + 1, player, gameMap);
        }

        //I am not sure, but aren't there three cases missing for positionY-1?
        return false;
    }

    public boolean checkForWin(int positionY, int positionYNext, int positionX, int positionXNext, int player, int[][] gameMap){
        int vectorY = positionYNext - positionY;
        int vectorX = positionXNext - positionX;
        for(int i = 1; i <= 3; i++){
            if(gameMap[positionY + vectorY * i][positionX + vectorX * i] != player)
            {
                return false;
            }
        }
        return true;
    }

    public int changePlayer(int player){
        if(player == 1)
        {
            return  2; //enum?
        }
        else{
            return  1; //enum?
        }
    }

}
