package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100;
    private float yDelta = 100;

    private BufferedImage img;

    // Constructor
    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        imporImg();
        setPanelSize();
        // Adds the key listener
        addKeyListener(new KeyboardInputs(this));
        // Adds the mouse listener
        addMouseListener(mouseInputs);
        // Adds the mouse motion listener
        addMouseMotionListener(mouseInputs);
    }

    private void imporImg() {
        InputStream is = getClass().getResourceAsStream("/resources/Sonic/Sonic (7).png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, (int) xDelta, (int) yDelta, null);

    }

}
