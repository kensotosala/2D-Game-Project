package Modelo.Levels;

import java.awt.Graphics;
<<<<<<< HEAD
<<<<<<< HEAD
import java.awt.Rectangle;
=======
=======
import java.awt.Graphics2D;
>>>>>>> parent of b118cba (Arreglo de plataformas)
import java.awt.Image;
import java.awt.image.BufferedImage;
>>>>>>> parent of 986a7c4 (Gravedad, colisiones y salto)
import java.io.File;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
import java.io.IOException;

>>>>>>> parent of b118cba (Arreglo de plataformas)
import javax.imageio.ImageIO;

<<<<<<< HEAD
=======
import Controlador.Game;

>>>>>>> parent of 986a7c4 (Gravedad, colisiones y salto)
public class LevelManager {
<<<<<<< HEAD
    private List<Rectangle> platforms;
=======
    private Game game;
    private BufferedImage levelSprite;
    private BufferedImage[] outsideSprites;
    private int GAME_WIDTH;
    private int GAME_HEIGHT;
>>>>>>> parent of b118cba (Arreglo de plataformas)

<<<<<<< HEAD
    public LevelManager(String path) {
        platforms = new ArrayList<>();
        loadPlatforms(path);
    }

    private void loadPlatforms(String path) {
        // Load platform coordinates from a file or any other source
        // Example:
        platforms.add(new Rectangle(100, 500, 200, 20));
        platforms.add(new Rectangle(400, 400, 150, 20));
=======
    public LevelManager(Game game, String spritesDirectory, int gameWidth, int gameHeight) {
        this.game = game;
        this.GAME_WIDTH = gameWidth;
        this.GAME_HEIGHT = gameHeight;
        try {
            levelSprite = ImageIO.read(new File("2D Game/resources/Background/background-zone-1.png"));
            int newWidth = 1248 /* nuevo ancho deseado */;
            int newHeight = 672 /* nueva altura deseada */;
            levelSprite = scaleImage(levelSprite, newWidth, newHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
        outsideSprites = loadOutsideSprites(spritesDirectory);
    }

    private BufferedImage[] loadOutsideSprites(String spritesDirectory) {
        File folder = new File(spritesDirectory);
        File[] spriteFiles = folder.listFiles();

        BufferedImage[] sprites = new BufferedImage[spriteFiles.length];

        try {
            for (int i = 0; i < spriteFiles.length; i++) {
                BufferedImage originalImage = ImageIO.read(spriteFiles[i]);
                int newWidth = 100 /* nuevo ancho deseado */;
                int newHeight = 100 /* nueva altura deseada */;
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
                sprites[i] = toBufferedImage(scaledImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprites;
>>>>>>> parent of 986a7c4 (Gravedad, colisiones y salto)
    }

    private BufferedImage scaleImage(BufferedImage originalImage, int newWidth, int newHeight) {
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }

    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Crea una nueva imagen BufferedImage con el mismo ancho y alto
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Dibuja la imagen en el BufferedImage
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }

    public void draw(Graphics g) {
<<<<<<< HEAD
        g.setColor(Color.GREEN);
        for (Rectangle platform : platforms) {
            g.fillRect(platform.x, platform.y, platform.width, platform.height);
        }
    }

    public List<Rectangle> getPlatforms() {
        return platforms;
=======
        g.drawImage(levelSprite, 0, 0, null);

        // Dibujar las imágenes cargadas en outsideSprites
        for (int i = 0; i < outsideSprites.length; i++) {
            int x;
            int y;

            // Posicionar las plataformas en la parte inferior de la ventana
            if (i == 3) {
                int platformWidth = outsideSprites[i].getWidth();
                int platformHeight = outsideSprites[i].getHeight();
                int numPlatforms = 5; // Número de plataformas en la fila

                // Calcular el espacio horizontal entre las plataformas
                int horizontalSpacing = (GAME_WIDTH - (numPlatforms * platformWidth)) / (numPlatforms + 1);

                // Posicionar las plataformas en la parte inferior de la ventana
                for (int j = 0; j < numPlatforms; j++) {
                    x = (j + 1) * horizontalSpacing + j * platformWidth;
                    y = GAME_HEIGHT - platformHeight - 1; // Espacio entre el suelo y las plataformas

                    g.drawImage(outsideSprites[i], x, y, null);
                }
            } else {
                x = 100;
                y = 100;

                g.drawImage(outsideSprites[i], x, y, null);
            }
        }
    }

    public void update() {

>>>>>>> parent of 986a7c4 (Gravedad, colisiones y salto)
    }
}
