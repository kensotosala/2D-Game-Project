package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;

    // Constructor
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        setFocusable(true); // Request focus for keyboard events
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
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

    }

    public void updateGame() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;

    }

}
