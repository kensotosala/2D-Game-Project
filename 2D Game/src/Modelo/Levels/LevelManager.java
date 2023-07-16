package Modelo.Levels;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class LevelManager {
    private List<Rectangle> platforms;

    public LevelManager(String path) {
        platforms = new ArrayList<>();
        loadPlatforms(path);
    }

    private void loadPlatforms(String path) {
        // Load platform coordinates from a file or any other source
        // Example:
        platforms.add(new Rectangle(100, 500, 200, 20));
        platforms.add(new Rectangle(400, 400, 150, 20));
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for (Rectangle platform : platforms) {
            g.fillRect(platform.x, platform.y, platform.width, platform.height);
        }
    }

    public List<Rectangle> getPlatforms() {
        return platforms;
    }
}
