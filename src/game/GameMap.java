package game;

import java.io.IOException;

import static game.GameToken.*;

public class GameMap {

    private GameToken[][] gameMap;
    // Why static? Nothing suggests there can only be one instance of GameMap
    private static final int HEIGHT = 7;
    private static final int WIDTH = 9;

    // I'd add a constructor with height/width arguments
    public GameMap() {
        gameMap = createEmptyMapWithPadding(HEIGHT, WIDTH);
    }

    // Should be private
    public GameToken[][] createEmptyMapWithPadding(int height, int width) {
        GameToken[][] newMap = new GameToken[height][width];
        // Neat way to initialize an array: https://stackoverflow.com/a/7118214
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newMap[i][j] = EMPTY;
            }
        }
        // Minor: You could use "j" as index in this for loop to signify you're iterating on conlums, not rows
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

    // Name too specific, change to "print()"
    // First: The class name is "GameMap" so no need to say "map"
    // Second: Padding is an implementation detail that should be transparent to the outside
    public void printMapWithoutPadding() {
        System.out.println();
        // In general, not only here: You could use for-each loops instead of explicitly iterating ofer every index, this will improve readability
        for (int i = 0; i < HEIGHT - 1; i++) {
            System.out.print("|");
            for (int j = 1; j < WIDTH - 1; j++) {
                switch (gameMap[i][j]) {
                    case EMPTY:
                        // Magic strings. Use some constants instead (char emptyField='0'). Same for other case statements
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

    // Function name too vague. It doesn't just return a magic Y, it returns first empty Y in a column. Also, use "get" instead of "return" in such names
    public int returnPositionY(int positionX) {
        int positionY = 0;
        while (positionY < HEIGHT) {
            if (gameMap[positionY][positionX] != EMPTY) {
                return positionY;
            }
            // Minor: This incrementation is alright, but looks kinda off. See if you can build it into the loop (maybe use for or do-while?)
            positionY++;
        }
        return positionY;
    }

    // rename "player" to "token". GameMap doesn't need to know about any players
    public void placeToken(int positionX, int positionY, GameToken player) {
        gameMap[positionY - 1][positionX] = player;
    }

    // Very meaningless name. Change it to something expressing the function's behaviour
    public boolean checkLastTokenIfWin(int positionX, int positionY) {
        // Why the decrementation? Why do you correct the caller without warning? This can and will cause off by one errors
        positionY = positionY - 1;
        // Minor: Again, wouldn't call it "player", more like "lastToken" "activeToken" or sth like this
        GameToken currentPlayer = gameMap[positionY][positionX];
        // Logic error: What if placing the token creates multiple potential lines, only one of which is 4-long? Consider the case (upper-case Z is the last token placed):
        // OOOOOO
        // OzzZOO
        // yyyzOO
        // yyyzOO
        // yyyzOO
        // You will enter the first if block and return false, even though the victory condition is fulfilled
        //
        // Potential logic error: Consider the case:
        // OzZzzO
        if (gameMap[positionY][positionX - 1] == currentPlayer) {
            return checkForWin(positionY, positionY, positionX, positionX - 1, currentPlayer);
            // Unnecessary whiteline, same below

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

    // CheckLastTokenIfWin's arguments are X,Y. Here the order is reversed, that's confusing
    // Also: Private
    // Also: I don't love this function name. "checkWinCondition"? Dunno.
    // Minor: Also: player->activeToken 
    public boolean checkForWin(int positionY, int positionYNext, int positionX, int positionXNext, GameToken player) {
        // Consider using some kind of Vector2D as input instead of two ints (it would be {0, -1}, {1,0} etc)
        int vectorY = positionYNext - positionY;
        int vectorX = positionXNext - positionX;
        for (int i = 1; i <= 3; i++) {
            if (gameMap[positionY + vectorY * i][positionX + vectorX * i] != player) {
                return false;
            }
        }
        return true;
    }

    // Function name is bad, it suggests that the function returns true only when the entire map is empty. As it is used in Controller, the name should be "anySpaceEmpty" or similar
    // Minor: Fairly inefficient, consider keeping a counter of free spaces left and decrement when any is filled.
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

    // This class is not the place for this
    public void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Kinda on the fence whether this class is a right place for this
    public void printPlayerMoves(int position) {
        System.out.print("|");
        // Magical numbers, use HEIGHT and WIDTH
        for (int i = 1; i <= 7; i++) {
            if (i == position) {
                System.out.print("v|");
            } else {
                System.out.print("_|");
            }
        }
        System.out.println();
    }

    // This class is not the place for this
    public GameToken changePlayer(GameToken player) {
        if (player == PLAYER_ONE) {
            return PLAYER_TWO;
        } else {
            return PLAYER_ONE;
        }
    }

}
