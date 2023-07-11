package Vista;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import Controlador.Game;
import Modelo.KeyboardInputs;
import Modelo.MouseInputs;
import Modelo.Levels.LevelManager;

import static Controlador.Game.GAME_WIDTH;
import static Controlador.Game.GAME_HEIGHT;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;
    private String spritesDirectory = "2D Game/resources/Platforms"; // Directorio de los sprites

    public GamePanel(Game game) {

        // Inicializar la clase MouseInputs
        mouseInputs = new MouseInputs(this);

        this.game = game;
        int gameWidth = GAME_WIDTH;
        int gameHeight = GAME_HEIGHT;

        // Establecer el tamaño del panel
        setPanelSize();

        // Crear una instancia de LevelManager
        LevelManager levelManager = new LevelManager(game, spritesDirectory, gameWidth, gameHeight);

        // Configurar controles de teclado
        setFocusable(true); // Agregar esta línea para recibir eventos de teclado
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
        game.update(); // Llamar al método update del juego para actualizar el estado del juego
    }

    // Sobrescribe el método paintComponent para dibujar la subimagen en el panel.
    // @param g El contexto gráfico en el que se dibujará la subimagen.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g); // Llamar al método render del juego para dibujar el juego en el panel
    }

    public Game getGame() {
        return game;
    }

    
}
