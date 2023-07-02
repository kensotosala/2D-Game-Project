package Vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Modelo.KeyboardInputs;
import Modelo.MouseInputs;

import static Modelo.Utilities.Constants.Directions.*;

/**
 * La clase GamePanel representa un panel de juego en el que se dibuja una
 * imagen y se controla mediante entradas de teclado y ratón.
 */
public class GamePanel extends JPanel {

    private MouseInputs mouseInputs; // Manejador de entradas de ratón
    private float xDelta = 100; // Delta de desplazamiento en el eje x
    private float yDelta = 100; // Delta de desplazamiento en el eje y
    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private BufferedImage[] attackJumpAnimation;
    private BufferedImage[] attackAnimation;
    private BufferedImage[] hitAnimation;
    private BufferedImage[] jumpAnimation;
    private int animationTick = 30;
    private int animationIndex = 30;
    private int animationSpeed = 15;
    private int playerDirection = -1;
    private boolean moving = false;
    private BufferedImage playerAction; // Cambiado el tipo de variable

    /**
     * Constructor de la clase GamePanel.
     * Inicializa la clase, importa la imagen, establece el tamaño del panel y
     * configura los controles de teclado y ratón.
     */
    public GamePanel() {

        // Inicializar la clase MouseInputs
        mouseInputs = new MouseInputs(this);

        // Importar la imagen
        loadAnimation();
        loadRunninAnimation();
        loadAttackJumpAnimation();
        loadAttackAnimation();
        loadHitAnimation();
        loadJumpAnimation();

        // Establecer el tamaño del panel
        setPanelSize();

        // Configurar controles de teclado
        addKeyListener(new KeyboardInputs(this));

        // Configurar controles de ratón
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimation() {
        File folder = new File("2D Game\\resources\\Idle"); // Ruta del directorio donde se encuentran las imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            idleAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    idleAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadRunninAnimation() {
        File folder = new File("2D Game\\resources\\idle-running"); // Ruta del directorio donde se encuentran las
                                                                    // imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            runningAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    runningAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadAttackJumpAnimation() {
        File folder = new File("2D Game\\resources\\idle-attack-jump"); // Ruta del directorio donde se encuentran las
        // imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            attackJumpAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    attackJumpAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadAttackAnimation() {
        File folder = new File("2D Game\\resources\\idle-attacking"); // Ruta del directorio donde se encuentran las
        // imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            attackAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    attackAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadHitAnimation() {
        File folder = new File("2D Game\\resources\\idle-hit"); // Ruta del directorio donde se encuentran las
        // imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            hitAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    hitAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadJumpAnimation() {
        File folder = new File("2D Game\\resources\\idle-jumping"); // Ruta del directorio donde se encuentran las
        // imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            jumpAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    jumpAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Establece el tamaño preferido del panel.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex = (animationIndex + 1) % jumpAnimation.length;
        }
    }

    public void setDirection(int direction) {
        this.playerDirection = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setAnimation() {

        if (moving) {
            playerAction = runningAnimation[animationIndex];
        } else {
            playerAction = idleAnimation[animationIndex];
        }
    }

    private void updatePos() {
        if (moving) {
            switch (playerDirection) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePos();
    }

    // Sobrescribe el método paintComponent para dibujar la subimagen en el panel.
    // @param g El contexto gráfico en el que se dibujará la subimagen.
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Running Animation
        g.drawImage(playerAction, (int) xDelta, (int) yDelta, 128, 180, null);

    }
}
