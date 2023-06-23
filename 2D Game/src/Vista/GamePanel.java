package Vista;

import java.awt.Graphics;

import javax.swing.JPanel;

import Modelo.KeyboardInputs;

import Modelo.MouseInputs;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private int xDelta = 100;
    private int yDelta = 100;

    // Constructor
    public GamePanel() {

        // Initialize the class
        mouseInputs = new MouseInputs(this);

        // Keyboard controls
        addKeyListener(new KeyboardInputs(this));

        // Mouse controls
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
        repaint();
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
        repaint();
    }

    // Change the rectangle position
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(xDelta, yDelta, 200, 50);
    }
}