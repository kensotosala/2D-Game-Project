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

/**
 * La clase GamePanel representa un panel de juego en el que se dibuja una
 * imagen y se controla mediante entradas de teclado y ratón.
 */
public class GamePanel extends JPanel {

    private MouseInputs mouseInputs; // Manejador de entradas de ratón
    private float xDelta = 100; // Delta de desplazamiento en el eje x
    private float yDelta = 100; // Delta de desplazamiento en el eje y
    private BufferedImage img; // Imagen principal
    private BufferedImage subImg; // Subimagen de la imagen principal

    /**
     * Constructor de la clase GamePanel.
     * Inicializa la clase, importa la imagen, establece el tamaño del panel y
     * configura los controles de teclado y ratón.
     */
    public GamePanel() {

        // Inicializar la clase MouseInputs
        mouseInputs = new MouseInputs(this);

        // Importar la imagen
        importImg();

        // Establecer el tamaño del panel
        setPanelSize();

        // Configurar controles de teclado
        addKeyListener(new KeyboardInputs(this));

        // Configurar controles de ratón
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Importa la imagen desde un archivo.
     */
    private void importImg() {
        try {
            img = ImageIO.read(new FileInputStream("2D Game\\resources\\Sonic.png"));
        } catch (IOException e) {
            e.printStackTrace();
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

    /**
     * Sobrescribe el método paintComponent para dibujar la subimagen en el panel.
     * 
     * @param g El contexto gráfico en el que se dibujará la subimagen.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Obtener la subimagen de la imagen principal
        subImg = img.getSubimage(1 * 64, 8 * 40, 64, 40);

        // Dibujar la subimagen en el contexto gráfico
        g.drawImage(subImg, (int) xDelta, (int) yDelta, 128, 80, null);
    }
}
