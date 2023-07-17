package main;

import javax.swing.JFrame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame {
    private GamePanel gamePanel;

    // Constructor
    public GameWindow(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        initializeWindow();
        add(gamePanel);
        setGamePanelProperties();
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }

    private void initializeWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void setGamePanelProperties() {
        pack();
        setVisible(true);
        gamePanel.requestFocus();
    }

}
