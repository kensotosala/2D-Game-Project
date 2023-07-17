package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import levels.LevelManager;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;
    private LevelManager levelManager; // Add LevelManager instance

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        levelManager = new LevelManager(game); // Initialize LevelManager

        setPanelSize();
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        System.out.println("Size: " + GAME_WIDTH + " : " + GAME_HEIGHT);
    }

    public void updateGame() {
        game.getPlayer().update(); // Update the player during the game loop
        levelManager.update(); // Update the level manager during the game loop
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Use the LevelManager to render all game elements
        levelManager.draw(g2d);
    }

    public Game getGame() {
        return game;
    }
}
