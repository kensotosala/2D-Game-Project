package levels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import utilz.LoadSave;

public class LevelManager extends JPanel {
    private BufferedImage level1SpriteImage;

    public LevelManager() {
        loadLevel1SpriteImage(); // Load the level 1 sprite image
    }

    private void loadLevel1SpriteImage() {
        // Load the level 1 image using the LoadSave class
        level1SpriteImage = LoadSave.getLevel1SpriteImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Paint the level 1 sprite image
        g.drawImage(level1SpriteImage, 0, 0, null);
    }

    public Object getCurrentLevel() {
        return null;
    }

    public void draw(Graphics2D g2d) {
    }
}
