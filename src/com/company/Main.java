package com.company;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

//CONTROLER

        Frame f = new Frame("Demo");
        f.setLayout(new FlowLayout());
        f.setSize(200, 200);
        Label l = new Label();
        l.setText("This is a Game");
        f.add(l);
        f.setVisible(true);

        SimpleKeyEvent simpleKeyEvent = new SimpleKeyEvent();

        f.addKeyListener(simpleKeyEvent);
    }
}

