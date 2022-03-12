package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static game.GameToken.*;

public class GameController implements KeyListener {

    private static final int START_X_POSITION = 1;
    private static final int CHANGE_X_POS = 1;
    private final GameMap gameMap;
    int posX;
    GameToken player;

    GameController(GameMap gameMap) {
        this.gameMap = gameMap;
        player = PLAYER_ONE;
        posX = START_X_POSITION;
        refreshScreen(posX);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            posX = movePosX(posX, -CHANGE_X_POS);
            refreshScreen(posX);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            posX = movePosX(posX, +CHANGE_X_POS);
            refreshScreen(posX);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            try {
                executeTurn(player);
            } catch (IndexOutOfBoundsException er) {
                System.out.print("Nie da się włożyć w tej kolumnie więcej tokenów. Spróbuj innej.");
                player = gameMap.changePlayer(player);
            }
            player = gameMap.changePlayer(player);
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

    private void executeTurn(GameToken player) {
        int posY = gameMap.returnPositionY(posX);
        gameMap.placeToken(posX, posY, player);
        if (!gameMap.checkIfEmpty()) {
            System.out.print("Brak wolnych miejsc. Koniec gry!");
            System.exit(0);
        }
        if (gameMap.checkLastTokenIfWin(posX, posY)) {
            System.out.print("Gracz " + player + " wygral!");
            System.exit(0);
        }
        posX = START_X_POSITION;
        refreshScreen(posX);
    }

    public void refreshScreen(int position) {
        gameMap.clearConsole();
        System.out.println("Ruch gracza " + player);
        gameMap.printPlayerMoves(position);
        gameMap.printMapWithoutPadding();
    }

}
