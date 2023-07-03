package Vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Controlador.Game;
import Modelo.KeyboardInputs;
import Modelo.MouseInputs;

import static Modelo.Utilities.Constants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {

        // Inicializar la clase MouseInputs
        mouseInputs = new MouseInputs(this);

        this.game = game;

        // Establecer el tamaño del panel
        setPanelSize();

        // Configurar controles de teclado
        addKeyListener(new KeyboardInputs(this));

        // Configurar controles de ratón
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void updateGame() {

    }

    // Sobrescribe el método paintComponent para dibujar la subimagen en el panel.
    // @param g El contexto gráfico en el que se dibujará la subimagen.
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.render(g);

    }

    public Game getGame() {
        return game;
    }
}
