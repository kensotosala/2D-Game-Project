package main;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame jframe;

    public GameWindow() {
        jframe = new JFrame();
        jframe.setSize(800, 600);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}