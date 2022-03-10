package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static game.GameToken.*;

public class GameController implements KeyListener {

    private static final int startXPosition = 1;
    private static final int changeXPos = 1;
    private final GameMap gameMap;
    int posX;
    int player;

    GameController(GameMap gameMap) {
        this.gameMap = gameMap;
        player = PLAYER_ONE.value;
        posX = startXPosition;
        refreshScreen(posX);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            posX = movePosX(posX, -changeXPos);
            refreshScreen(posX);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            posX = movePosX(posX, +changeXPos);
            refreshScreen(posX);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            executeTurn(player);
            player = changePlayer(player);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
        return newXpos;
    }

    public int changePlayer(int player) {
        if (player == PLAYER_ONE.value) {
            return PLAYER_TWO.value;
        } else {
            return PLAYER_ONE.value;
        }
    }

    private void executeTurn(int player) {
        int posY = gameMap.returnPositionY(posX);
        gameMap.placeToken(posX, posY, player);
        if (gameMap.checkLastTokenIfWin(posX, posY)) {
            System.out.print("Gracz " + player + " wygral!");
            System.exit(0);
        }
        posX = startXPosition;
        refreshScreen(posX);
    }

    public void refreshScreen(int position) {
        gameMap.clearConsole();
        System.out.println("Ruch gracza " + player);
        printPlayerMoves(position);
        gameMap.printMapWithoutPadding();
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
}
