package main;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100;
    private float yDelta = 100;
    private float xDir = 1f;
    private float yDir = 1f;
    private int frames = 0;
    private long lastCheck = 0;
    private Color color = Color.RED;

    // Constructor
    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        // Adds the key listener
        addKeyListener(new KeyboardInputs(this));
        // Adds the mouse listener
        addMouseListener(mouseInputs);
        // Adds the mouse motion listener
        addMouseMotionListener(mouseInputs);
    }

    // Gets the mouse inputs
    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    // Gets the mouse inputs
    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    // Sets the position of the rectangle
    public void setRecPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }

    // Paints the panel
    public void paintComponent(Graphics g) {

        updateRectangle();
        g.setColor(color);
        super.paintComponent(g);

        // Draws a rectangle
        g.fillRect((int) xDelta, (int) yDelta, 200, 50);

        
    }

    // Updates the rectangle
    private void updateRectangle() {
        xDelta += xDir;
        if (xDelta > 400 || xDelta < 0) {
            xDir *= -1;
            color = getRandomColor();
        }

        yDelta += yDir;
        if (yDelta > 400 || yDelta < 0) {
            yDir *= -1;
            color = getRandomColor();
        }
    }

    private Color getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }
}
