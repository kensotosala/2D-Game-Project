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
    private int animationTick = 30;
    private int animationIndex = 30;
    private int animationSpeed = 15;

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

    /**
     * Establece el tamaño preferido del panel.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    /**
     * Cambia el valor de desplazamiento en el eje x.
     * 
     * @param value El valor a sumar al desplazamiento actual en el eje x.
     */
    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    /**
     * Cambia el valor de desplazamiento en el eje y.
     * 
     * @param value El valor a sumar al desplazamiento actual en el eje y.
     */
    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    /**
     * Establece la posición del rectángulo.
     * 
     * @param x La coordenada x de la posición del rectángulo.
     * @param y La coordenada y de la posición del rectángulo.
     */
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= runningAnimation.length) {
                animationIndex = 0;
            }
        }
    }

    /**
     * Sobrescribe el método paintComponent para dibujar la subimagen en el panel.
     * 
     * @param g El contexto gráfico en el que se dibujará la subimagen.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateAnimationTick();

        // Dibujar la subimagen en el contexto gráfico
        // g.drawImage(idleAnimation[animationIndex], (int) xDelta, (int) yDelta, 128,
        // 80, null);

        // Running Animation
        g.drawImage(runningAnimation[animationIndex], (int) xDelta, (int) yDelta, 128, 80, null);

    }
}
