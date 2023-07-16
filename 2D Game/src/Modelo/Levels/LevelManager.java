// Paquete Modelo.Levels
package Modelo.Levels;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Controlador.Game;
import Modelo.Platform;
import Modelo.Player;

public class LevelManager {
    private BufferedImage levelSprite;
    private BufferedImage floorPlatformSprite;
    private BufferedImage[] airPlatformSprites;
    private List<Platform> platforms;

    public LevelManager(String spritesDirectory) {
        try {
            levelSprite = loadImage("2D Game/resources/Background/background-zone-1.png");
            floorPlatformSprite = loadImage(spritesDirectory + "/floor/floor-platform.png");
            airPlatformSprites = loadAirPlatformSprites(spritesDirectory + "/air");
        } catch (IOException e) {
            e.printStackTrace();
        }

        platforms = new ArrayList<>();
        platforms.add(new Platform(100, 200, 100, 20));
        platforms.add(new Platform(300, 400, 150, 20));
    }

    private BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    private BufferedImage[] loadAirPlatformSprites(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        File[] spriteFiles = directory.listFiles();

        if (spriteFiles == null) {
            System.out.println("Sprite files not found.");
            return new BufferedImage[0];
        }

        BufferedImage[] sprites = new BufferedImage[spriteFiles.length];

        for (int i = 0; i < spriteFiles.length; i++) {
            sprites[i] = loadImage(spriteFiles[i].getPath());
        }

        return sprites;
    }

    public void draw(Graphics g) {
        g.drawImage(levelSprite, 0, 0, null);

        if (floorPlatformSprite != null) {
            int scaledWidth = floorPlatformSprite.getWidth() / 4;
            int scaledHeight = floorPlatformSprite.getHeight() / 6;

            int numPlatforms = Math.min(5, Game.GAME_WIDTH / scaledWidth);
            int horizontalSpacing = (Game.GAME_WIDTH - (numPlatforms * scaledWidth)) / (numPlatforms + 1);

            for (int j = 0; j < numPlatforms; j++) {
                int x = (j + 1) * horizontalSpacing + j * scaledWidth;
                int y = Game.GAME_HEIGHT - scaledHeight;

                g.drawImage(floorPlatformSprite, x, y, scaledWidth, scaledHeight, null);

                Player player = Game.getInstance().getPlayer();
                if (player != null) {
                    Rectangle platformBounds = new Rectangle(x, y, scaledWidth, scaledHeight);
                    Rectangle playerBounds = player.getBounds();

                    if (playerBounds.intersects(platformBounds)) {
                        player.setY(y - playerBounds.height);
                    }
                }
            }
        }

        if (airPlatformSprites != null && airPlatformSprites.length > 0) {
            int scaledAirPlatformWidth = airPlatformSprites[0].getWidth() * 2;
            int scaledAirPlatformHeight = airPlatformSprites[0].getHeight() * 2;

            int numAirPlatforms = Math.min(3, Game.GAME_WIDTH / scaledAirPlatformWidth);
            int horizontalSpacing = (Game.GAME_WIDTH - (numAirPlatforms * scaledAirPlatformWidth))
                    / (numAirPlatforms + 1);
            int verticalPosition = Game.GAME_HEIGHT / 2 - scaledAirPlatformHeight / 2;

            for (int j = 0; j < numAirPlatforms; j++) {
                int x = (j + 1) * horizontalSpacing + j * scaledAirPlatformWidth;
                int y = verticalPosition + (j % 2 == 0 ? -50 : 50);

                g.drawImage(airPlatformSprites[j], x, y, scaledAirPlatformWidth, scaledAirPlatformHeight, null);
            }
        }
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }
}
