package levels;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.Game;
import utilz.LoadSave;

public class LevelManager {
    private Game game;

    public LevelManager(Game game) {
        this.game = game;
    }

    public void draw(Graphics2D g2d) {
        // Draw the Level 1 Sprite image at a specific position
        BufferedImage level1SpriteImage = LoadSave.getLevel1SpriteImage();
        int x = 0; // Adjust the 'x' position as needed
        int y = 0; // Adjust the 'y' position as needed
        g2d.drawImage(level1SpriteImage, x, y, null);

        // Draw the player on the screen
        game.getPlayer().render(g2d);
    }

    public void update() {
        // Update the level state
    }
}
