package Controlador;

import Vista.GamePanel;
import Vista.GameWindow;

public class Game {
    // Atributtes
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    // Constructor
    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
    }
}
