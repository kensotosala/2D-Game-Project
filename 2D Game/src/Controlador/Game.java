package Controlador;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import Modelo.CollisionManager;
import Modelo.Platform;
import Modelo.Player;
import Modelo.Levels.LevelManager;
import Vista.GamePanel;
import Vista.GameWindow;

public class Game implements ActionListener {
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    private Player player;
    private LevelManager levelManager;
    private CollisionManager collisionManager;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private Timer gameTimer;

    public Game() throws IOException {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        gameTimer = new Timer(16, this); // Actualizar el juego aproximadamente cada 16 ms (60 FPS)
        gameTimer.start();
    }

    private void initClasses() {
        List<Platform> platforms = new ArrayList<>();
        player = new Player(0, 0, platforms);
        levelManager = new LevelManager(this, "2D Game/resources/Platforms", GAME_WIDTH, GAME_HEIGHT);
        collisionManager = new CollisionManager(levelManager.getPlatforms()); // Pasa las plataformas al constructor de
                                                                              // CollisionManager

    }

    public void update() {
        player.update(); // Actualizar posición y velocidad del jugador
        collisionManager.checkCollisions(player); // Verificar colisiones del jugador
        // Resto de la lógica de actualización del juego
    }

    public void render(Graphics g) {
        levelManager.draw(g);
        player.render(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        gamePanel.repaint();
    }

    public void windowFocusLost() {
    }

    public Player getPlayer() {
        return player;
    }
}
