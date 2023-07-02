package Controlador;

import java.io.IOException;

import Vista.GamePanel;
import Vista.GameWindow;

public class Game implements Runnable {
    // Atributos
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    // Constructor
    public Game() throws IOException {
        // Inicializa el panel de juego y la ventana del juego
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);

        // Establece el enfoque en el panel de juego
        gamePanel.requestFocus();

        // Inicia el bucle principal del juego
        startGameLoop();
    }

    // Inicia el hilo de ejecución del juego
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        gamePanel.updateGame();
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
}
