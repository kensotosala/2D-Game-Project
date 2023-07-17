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

    // Constructor
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        levelManager = new LevelManager(game); // Initialize LevelManager

        setPanelSize();
        setFocusable(true); // Request focus for keyboard events
        setFocusable(true);
        requestFocusInWindow();
        // Adds the key listener
        addKeyListener(new KeyboardInputs(this));
        // Adds the mouse listener
        addMouseListener(mouseInputs);
        // Adds the mouse motion listener
        addMouseMotionListener(mouseInputs);
    }

    // Sets the size of the panel
    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

        System.out.println("Size: " + GAME_WIDTH + " : " + GAME_HEIGHT);
    }

    public void updateGame() {
        game.getPlayer().update(); // Update the player during the game loop
        levelManager.update(null); // Update the level manager during the game loop
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
        Graphics2D g2d = (Graphics2D) g;
    }

    public Game getGame() {
        return game;

    }

}