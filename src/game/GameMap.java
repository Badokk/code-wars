package game;

import java.io.IOException;

import static game.GameToken.*;

public class GameMap {

    private GameToken[][] gameMap;
    private static final int HEIGHT = 7;
    private static final int WIDTH = 9;

    public GameMap() {
        gameMap = createEmptyMapWithPadding(HEIGHT, WIDTH);
    }

    public GameToken[][] createEmptyMapWithPadding(int height, int width) {
        GameToken[][] newMap = new GameToken[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newMap[i][j] = EMPTY;
            }
        }
        for (int i = 0; i < width; i++) {
            newMap[height - 1][i] = PADDING;
        }
        for (int i = 0; i < height; i++) {
            newMap[i][0] = PADDING;
        }
        for (int i = 0; i < height; i++) {
            newMap[i][width - 1] = PADDING;
        }
        return newMap;
    }

    public void printMapWithoutPadding() {
        System.out.println();
        for (int i = 0; i < HEIGHT - 1; i++) {
            System.out.print("|");
            for (int j = 1; j < WIDTH - 1; j++) {
                switch (gameMap[i][j]) {
                    case EMPTY:
                        System.out.print("0");
                        break;
                    case PLAYER_ONE:
                        System.out.print("z");
                        break;
                    case PLAYER_TWO:
                        System.out.print("x");
                        break;
                    case PADDING:
                        System.out.print("error");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + gameMap[i][j]);
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.print("________________");
            System.out.println();
        }
    }

    public int returnPositionY(int positionX) {
        int positionY = 0;
        while (positionY < HEIGHT) {
            if (gameMap[positionY][positionX] != EMPTY) {
                return positionY;
            }
            positionY++;
        }
        return positionY;
    }

    public void placeToken(int positionX, int positionY, GameToken player) {
        gameMap[positionY - 1][positionX] = player;
    }

    public boolean checkLastTokenIfWin(int positionX, int positionY) {
        positionY = positionY - 1;
        GameToken currentPlayer = gameMap[positionY][positionX];
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
        return false;
    }

    public boolean checkForWin(int positionY, int positionYNext, int positionX, int positionXNext, GameToken player) {
        int vectorY = positionYNext - positionY;
        int vectorX = positionXNext - positionX;
        for (int i = 1; i <= 3; i++) {
            if (gameMap[positionY + vectorY * i][positionX + vectorX * i] != player) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIfEmpty() {
        for (int i = 0; i < HEIGHT - 1; i++) {
            for (int j = 1; j < WIDTH - 1; j++) {
                if (gameMap[i][j] == EMPTY) {
                    return true;
                }
            }
        }
        return false;
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

    public void printPlayerMoves(int position) {
        System.out.print("|");
        for (int i = 1; i <= 7; i++) {
            if (i == position) {
                System.out.print("v|");
            } else {
                System.out.print("_|");
            }
        }
        System.out.println();
    }

    public GameToken changePlayer(GameToken player) {
        if (player == PLAYER_ONE) {
            return PLAYER_TWO;
        } else {
            return PLAYER_ONE;
        }
    }

}
