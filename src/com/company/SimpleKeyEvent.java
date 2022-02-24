package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SimpleKeyEvent implements KeyListener {
    //MAP
    int a;
    int b;
    int[][] gameMap;
    int posY;
    int posX;
    int player;

    SimpleKeyEvent(){
        a = 7;
        b = 9;
        gameMap = createMap(a, b);
        player = 1;
        // Pamietac, ze Y liczymy od 0, a X od 1
        posY = 0;
        posX = 1;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            posX--;
            if(posX < 1){
                posX = 7;
            }
            System.out.print(posX);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            posX++;
            if(posX > 7){
                posX = 1;
            }
            System.out.print(posX);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            posY = returnPositionY(posX, gameMap, a);
            placeToken(posX, posY, player, gameMap);
            if(checkLastTokenIfWin(posX, posY, gameMap)){
                System.out.print("Gracz " + player + " wygral!");
                System.exit(0);
            }
            player = changePlayer(player);
            printMap(gameMap, a, b);
            posX = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public int[][] createMap(int a, int b) {
        int[][] gameMap = new int[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                gameMap[i][j] = 0;
            }
        }
        for (int i = 0; i < b; i++) {
            gameMap[a - 1][i] = 3;
        }
        return gameMap;
    }

    public void printMap(int[][] gameMap, int a, int b) {
        for (int i = 0; i < a - 1; i++) {
            System.out.print("|");
            for (int j = 1; j < b - 1; j++) {
                if (gameMap[i][j] == 1) {
                    System.out.print("z");
                }
                if (gameMap[i][j] == 2) {
                    System.out.print("x");
                }
                if (gameMap[i][j] == 0) {
                    System.out.print("0");
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.print("_____________");
            System.out.println();
        }
    }

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

    public void placeToken(int positionX, int positionY, int player, int[][] gameMap){
        gameMap[positionY - 1][positionX] = player;
    }

    public boolean checkLastTokenIfWin(int positionX, int positionY, int[][] gameMap){
        positionY = positionY - 1;
        int player = gameMap[positionY][positionX];
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
            return  2;
        }
        else{
            return  1;
        }
    }

}
