package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static game.GameToken.*;

//Other thing is, I would separate input from logic/map - SRP todo
public class GameController implements KeyListener {

    private static final int height = 7;
    private static final int width = 9;
    private static final int startXPosition = 1;
    private static final int changeXPos = 1;
    int[][] gameMap;
    int posX; //You can write classes in java - they are nice! You could have something like Position class, which will hold X and Y.
    int player; //int isn't very good type for that, I would prefer to use enum here.

    GameController() {
        gameMap = createEmptyMapWithPadding(height, width);
        player = PLAYER_ONE.value;
        posX = startXPosition;
        System.out.println("Ruch gracza " + player);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            posX = movePosX(posX, -changeXPos);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            posX = movePosX(posX, +changeXPos);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            executeTurn(gameMap,height, width, player);
            player = changePlayer(player);
        }
    }

    private void executeTurn(int[][] gameMap, int height, int width, int player) {
        int posY = returnPositionY(posX, gameMap, height);
        placeToken(posX, posY, player, gameMap);
        if (checkLastTokenIfWin(posX, posY, gameMap)) {
            System.out.print("Gracz " + player + " wygral!");
            System.exit(0);
        }
        printMapWithoutPadding(gameMap, height, width);
        posX = startXPosition;
        System.out.println("Ruch gracza " + player);

    }

    @Override
    public void keyReleased(KeyEvent e) {
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

    public void printMapWithoutPadding(int[][] gameMap, int height, int width) {
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

    public int returnPositionY(int positionX, int[][] gameMap, int height) {
        int positionY = 0;
        while (positionY < height) {
            if (gameMap[positionY][positionX] != 0 && positionY > 0) {
                return positionY;
            }
            positionY++;
        }
        return positionY;
    }

    // Why gameMap is passed as param, if it is class variable?
    //I will be easier to move it to separate class I hope todo
    public void placeToken(int positionX, int positionY, int player, int[][] gameMap) {
        gameMap[positionY - 1][positionX] = player;
    }

    public boolean checkLastTokenIfWin(int positionX, int positionY, int[][] gameMap) {
        positionY = positionY - 1;
        int currentPlayer = gameMap[positionY][positionX];
        if (gameMap[positionY][positionX - 1] == currentPlayer) {
            return checkForWin(positionY, positionY, positionX, positionX - 1, currentPlayer, gameMap);

        }
        if (gameMap[positionY + 1][positionX - 1] == currentPlayer) {
            return checkForWin(positionY, positionY + 1, positionX, positionX - 1, currentPlayer, gameMap);

        }
        if (gameMap[positionY + 1][positionX] == currentPlayer) {
            return checkForWin(positionY, positionY + 1, positionX, positionX, currentPlayer, gameMap);

        }
        if (gameMap[positionY + 1][positionX + 1] == currentPlayer) {
            return checkForWin(positionY, positionY + 1, positionX, positionX + 1, currentPlayer, gameMap);

        }
        if (gameMap[positionY][positionX + 1] == currentPlayer) {
            return checkForWin(positionY, positionY, positionX, positionX + 1, currentPlayer, gameMap);
        }

        //I am not sure, but aren't there three cases missing for positionY-1?
        //There are not missing, because there is no need to check them
        return false;
    }

    public boolean checkForWin(int positionY, int positionYNext, int positionX, int positionXNext, int player, int[][] gameMap) {
        int vectorY = positionYNext - positionY;
        int vectorX = positionXNext - positionX;
        for (int i = 1; i <= 3; i++) {
            if (gameMap[positionY + vectorY * i][positionX + vectorX * i] != player) {
                return false;
            }
        }
        return true;
    }

    public int changePlayer(int player) {
        if (player == PLAYER_ONE.value) {
            return PLAYER_TWO.value;
        } else {
            return PLAYER_ONE.value;
        }
    }

    public int movePosX(int actualXpos, int change) {
        int newXpos;
        newXpos = actualXpos + change;
        if (newXpos < 1) {
            newXpos = 7;
        }
        if (newXpos > 7) {
            newXpos = 1;
        }
        System.out.print(newXpos); //todo cleaning and printing map after every change
        return newXpos;
    }
}
