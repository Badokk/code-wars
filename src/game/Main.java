package game;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // "f" and "l" are quick and easy, but very bad names. Variable name should signify what it is and what it's for, so f should be more like "Game window" and l "Game title"
        // or whatever they're supposed to be
        Frame f = new Frame("Demo");
        f.setLayout(new FlowLayout());
        f.setSize(200, 200);
        Label l = new Label();
        l.setText("This is a Game");
        f.add(l);
        f.setVisible(true);

        GameMap gameMap = new GameMap();

        GameController gameController = new GameController(gameMap);

        f.addKeyListener(gameController);
    }
}

