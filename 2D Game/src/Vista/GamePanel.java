package Vista;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import Controlador.Game;
import Modelo.KeyboardInputs;
import Modelo.MouseInputs;
import static Controlador.Game.GAME_WIDTH;
import static Controlador.Game.GAME_HEIGHT;

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
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("GAME_WIDTH: " + GAME_WIDTH);
        System.out.println("GAME_HEIGHT: " + GAME_HEIGHT);

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
