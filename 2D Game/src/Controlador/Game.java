package Controlador;

import java.awt.Graphics;
import java.io.IOException;

import Modelo.Player;
import Modelo.Levels.LevelManager;
import Vista.GamePanel;
import Vista.GameWindow;

public class Game implements Runnable {
    // Atributos
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Player player;
    private LevelManager levelManager;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    // Constructor
    public Game() throws IOException {
        initClasses();
        // Inicializa el panel de juego y la ventana del juego
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);

        // Establece el enfoque en el panel de juego
        gamePanel.requestFocus();

        // Inicia el bucle principal del juego
        startGameLoop();

    }

    private void initClasses() {
        player = new Player(100, 100);
        levelManager = new LevelManager(this, "2D Game/resources/Platforms", GAME_WIDTH, GAME_HEIGHT);
    }

    // Inicia el hilo de ejecución del juego
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
        levelManager.update();
    }

    public void render(Graphics g) {
        levelManager.draw(g);
        player.render(g);
    }

    @Override
    public void run() {
        // Calcula el tiempo máximo permitido para cada cuadro
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        // Bucle principal del juego
        while (true) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // Comprueba si ha pasado un segundo para calcular los FPS
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();

                // Imprime los FPS en la consola
                System.out.println("FPS: " + frames + " | UPS: " + updates);

                // Reinicia el contador de cuadros
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }
}