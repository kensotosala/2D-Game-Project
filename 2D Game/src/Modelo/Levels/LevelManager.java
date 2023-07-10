package Modelo.Levels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Controlador.Game;
import Modelo.Player;

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
            floorPlatformSprite = ImageIO.read(new File(spritesDirectory + "/floor/floor-platform.png"));
            airPlatformSprites = loadAirPlatformSprites(spritesDirectory + "/air");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage scaleImage(BufferedImage originalImage, int newWidth, int newHeight) {
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics g = resizedImage.getGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();

        return resizedImage;
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
            try {
                sprites[i] = ImageIO.read(spriteFiles[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sprites;
    }

    public void draw(Graphics g) {
        g.drawImage(levelSprite, 0, 0, null);

        // Dibujar el suelo con las plataformas
        if (floorPlatformSprite != null) {
            int originalWidth = floorPlatformSprite.getWidth();
            int originalHeight = floorPlatformSprite.getHeight();
            int scaledWidth = originalWidth / 4; // Ajusta el factor de escala según tus necesidades
            int scaledHeight = originalHeight / 6; // Ajusta el factor de escala según tus necesidades

            int numPlatforms = Math.min(5, GAME_WIDTH / scaledWidth);

            int horizontalSpacing = (GAME_WIDTH - (numPlatforms * scaledWidth)) / (numPlatforms + 1);

            for (int j = 0; j < numPlatforms; j++) {
                int x = (j + 1) * horizontalSpacing + j * scaledWidth;
                int y = GAME_HEIGHT - scaledHeight;

                g.drawImage(floorPlatformSprite, x, y, scaledWidth, scaledHeight, null);

                // Verificar la colisión con el jugador
                Player player = game.getPlayer();
                if (player != null) {
                    int playerWidth = player.getWidth();
                    int playerHeight = player.getHeight();
                    Rectangle platformBounds = new Rectangle(x, y, scaledWidth, scaledHeight);
                    Rectangle playerBounds = new Rectangle((int) player.getX(), (int) player.getY(), playerWidth,
                            playerHeight);

                    if (playerBounds.intersects(platformBounds)) {
                        // Colocar al jugador encima de la plataforma
                        player.setY(y - playerHeight);
                    }
                }
            }
        }

        // Dibujar las plataformas en el aire
        if (airPlatformSprites != null && airPlatformSprites.length > 0) {
            int airPlatformWidth = airPlatformSprites[0].getWidth();
            int airPlatformHeight = airPlatformSprites[0].getHeight();
            int scaledAirPlatformWidth = airPlatformWidth * 2; // Ajusta el factor de escala según tus necesidades
            int scaledAirPlatformHeight = airPlatformHeight * 2; // Ajusta el factor de escala según tus necesidades

            int numAirPlatforms = Math.min(3, GAME_WIDTH / scaledAirPlatformWidth);

            int horizontalSpacing = (GAME_WIDTH - (numAirPlatforms * scaledAirPlatformWidth)) / (numAirPlatforms + 1);
            int verticalPosition = GAME_HEIGHT / 2 - scaledAirPlatformHeight / 2;

            for (int j = 0; j < numAirPlatforms; j++) {
                int x = (j + 1) * horizontalSpacing + j * scaledAirPlatformWidth;
                int y = verticalPosition + (j % 2 == 0 ? -50 : 50); // Ajusta la posición vertical según tus necesidades

                g.drawImage(airPlatformSprites[j], x, y, scaledAirPlatformWidth, scaledAirPlatformHeight, null);
            }
        }
    }

    public void update() {
        // Código de actualización del nivel
    }
}
