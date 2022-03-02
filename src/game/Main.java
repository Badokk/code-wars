package game;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

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

