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
        System.out.print(newXpos); //todo cleaning and printing map after every change
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
        gameMap.printMapWithoutPadding();
        posX = startXPosition;
        System.out.println("Ruch gracza " + player);

    }
}
