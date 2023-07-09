package Modelo.Levels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Controlador.Game;

public class LevelManager {
    private Game game;
    private BufferedImage levelSprite;
    private BufferedImage floorPlatformSprite;
    private BufferedImage[] airPlatformSprites;
    private int GAME_WIDTH;
    private int GAME_HEIGHT;

    public LevelManager(Game game, String spritesDirectory, int gameWidth, int gameHeight) {
        this.game = game;
        this.GAME_WIDTH = gameWidth;
        this.GAME_HEIGHT = gameHeight;
        try {
            levelSprite = ImageIO.read(new File("2D Game/resources/Background/background-zone-1.png"));
            int newWidth = 1248; /* nuevo ancho deseado */
            int newHeight = 672; /* nueva altura deseada */
            levelSprite = scaleImage(levelSprite, newWidth, newHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
        floorPlatformSprite = loadSprite(spritesDirectory + "/Platforms/floor-platform.png");
        airPlatformSprites = loadAirPlatformSprites(spritesDirectory + "/Platforms/air-platform");
    }

    private BufferedImage loadSprite(String spritePath) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
    }

    private BufferedImage[] loadAirPlatformSprites(String spritesDirectory) {
        File folder = new File(spritesDirectory);
        File[] spriteFiles = folder.listFiles();

        if (spriteFiles == null) {
            System.out.println("Sprite files not found.");
            return new BufferedImage[0];
        }

        BufferedImage[] sprites = new BufferedImage[spriteFiles.length];

        for (int i = 0; i < spriteFiles.length; i++) {
            sprites[i] = loadSprite(spriteFiles[i].getPath());
        }

        return sprites;
    }

    private BufferedImage scaleImage(BufferedImage originalImage, int newWidth, int newHeight) {
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics g = resizedImage.getGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();

        return resizedImage;
    }

    public void draw(Graphics g) {
        g.drawImage(levelSprite, 0, 0, null);

        // Dibujar el suelo con la plataforma
        if (floorPlatformSprite != null) {
            int platformWidth = floorPlatformSprite.getWidth();
            int platformHeight = floorPlatformSprite.getHeight();
            int numPlatforms = Math.min(5, GAME_WIDTH / platformWidth);

            int horizontalSpacing = (GAME_WIDTH - (numPlatforms * platformWidth)) / (numPlatforms + 1);

            for (int j = 0; j < numPlatforms; j++) {
                int x = (j + 1) * horizontalSpacing + j * platformWidth;
                int y = GAME_HEIGHT - platformHeight;

                g.drawImage(floorPlatformSprite, x, y, null);
            }
        }

        // Dibujar las plataformas en el aire
        if (airPlatformSprites != null && airPlatformSprites.length > 0) {
            int airPlatformWidth = airPlatformSprites[0].getWidth();
            int airPlatformHeight = airPlatformSprites[0].getHeight();
            int numAirPlatforms = Math.min(3, GAME_WIDTH / airPlatformWidth);

            int horizontalSpacing = (GAME_WIDTH - (numAirPlatforms * airPlatformWidth)) / (numAirPlatforms + 1);
            int verticalPosition = GAME_HEIGHT / 2 - airPlatformHeight / 2;

            for (int j = 0; j < numAirPlatforms; j++) {
                int x = (j + 1) * horizontalSpacing + j * airPlatformWidth;
                int y = verticalPosition;

                g.drawImage(airPlatformSprites[j], x, y, null);
            }
        }
    }

    public void update() {

    }
}

// Quedamos en que las plataformas no se dibujan y tenemos que hacer un suelo y
// poner varias plataformas en el aire.
