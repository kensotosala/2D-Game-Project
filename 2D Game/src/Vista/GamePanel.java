package Vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import Modelo.KeyboardInputs;
import Modelo.MouseInputs;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100;
    private float yDelta = 100;
    private BufferedImage img;

    // Constructor
    public GamePanel() {

        // Initialize the class
        mouseInputs = new MouseInputs(this);

        importImg();
        setPanelSize();

        // Keyboard controls
        addKeyListener(new KeyboardInputs(this));

        // Mouse controls
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImg() {
        try {
            img = ImageIO.read(new FileInputStream("2D Game\\resources\\Sonic.png"));
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

    public void changeXDelta(int value) {
        this.xDelta += value;

    }

    public void changeYDelta(int value) {
        this.yDelta += value;

    }

    // Change the rectangle position
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // g.drawImage(null, x, y, null);
    }

}