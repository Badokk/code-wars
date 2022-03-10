package game;

import java.io.IOException;

import static game.GameToken.*;

public class GameMap {

    public int[][] gameMap;
    private static final int height = 7;
    private static final int width = 9;

    public GameMap() {
        gameMap = createEmptyMapWithPadding(height, width);
    }

    public int[][] createEmptyMapWithPadding(int height, int width) {
        int[][] newMap = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newMap[i][j] = EMPTY.value;
            }
        }

        for (int i = 0; i < width; i++) {
            newMap[height - 1][i] = PADDING.value;
        }
        for (int i = 0; i < height; i++) {
            newMap[i][0] = PADDING.value;
        }
        for (int i = 0; i < height; i++) {
            newMap[i][width - 1] = PADDING.value;
        }
        return newMap;
    }

    public void printMapWithoutPadding() {
        System.out.println();
        for (int i = 0; i < height - 1; i++) {
            System.out.print("|");
            for (int j = 1; j < width - 1; j++) {
                //switch case ?
                if (gameMap[i][j] == PLAYER_ONE.value) {
                    System.out.print("z");
                }
                if (gameMap[i][j] == PLAYER_TWO.value) {
                    System.out.print("x");
                }
                if (gameMap[i][j] == EMPTY.value) {
                    System.out.print("0");
                }
                if (gameMap[i][j] == PADDING.value) {
                    System.out.println("error");
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.print("_____________");
            System.out.println();
        }
    }

    public int returnPositionY(int positionX) {
        int positionY = 0;
        while (positionY < height) {
            if (gameMap[positionY][positionX] != 0 && positionY > 0) {
                return positionY;
            }
            positionY++;
        }
        return positionY;
    }

    public void placeToken(int positionX, int positionY, int player) {
        gameMap[positionY - 1][positionX] = player;
    }

    public boolean checkLastTokenIfWin(int positionX, int positionY) {
        positionY = positionY - 1;
        int currentPlayer = gameMap[positionY][positionX];
        if (gameMap[positionY][positionX - 1] == currentPlayer) {
            return checkForWin(positionY, positionY, positionX, positionX - 1, currentPlayer);

        }
        if (gameMap[positionY + 1][positionX - 1] == currentPlayer) {
            return checkForWin(positionY, positionY + 1, positionX, positionX - 1, currentPlayer);

        }
        if (gameMap[positionY + 1][positionX] == currentPlayer) {
            return checkForWin(positionY, positionY + 1, positionX, positionX, currentPlayer);

        }
        if (gameMap[positionY + 1][positionX + 1] == currentPlayer) {
            return checkForWin(positionY, positionY + 1, positionX, positionX + 1, currentPlayer);

        }
        if (gameMap[positionY][positionX + 1] == currentPlayer) {
            return checkForWin(positionY, positionY, positionX, positionX + 1, currentPlayer);
        }

        //I am not sure, but aren't there three cases missing for positionY-1?
        //There are not missing, because there is no need to check them
        return false;
    }

    public boolean checkForWin(int positionY, int positionYNext, int positionX, int positionXNext, int player) {
        int vectorY = positionYNext - positionY;
        int vectorX = positionXNext - positionX;
        for (int i = 1; i <= 3; i++) {
            if (gameMap[positionY + vectorY * i][positionX + vectorX * i] != player) {
                return false;
            }
        }
        return true;
    }

    public void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
