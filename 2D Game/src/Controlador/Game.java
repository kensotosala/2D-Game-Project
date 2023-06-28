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

    @Override
    public void run() {
        // Calcula el tiempo máximo permitido para cada cuadro
        double timePerFrame = 1000000000.0 / FPS_SET;

        // Obtiene el tiempo del último cuadro
        long lastFrame = System.nanoTime();

        // Variables para el cálculo de los FPS
        long now = System.nanoTime();
        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        // Bucle principal del juego
        while (true) {
            now = System.nanoTime();

            // Comprueba si ha pasado el tiempo suficiente para renderizar un nuevo cuadro
            if (System.nanoTime() - lastFrame >= timePerFrame) {
                // Vuelve a pintar el panel de juego
                gamePanel.repaint();

                // Actualiza el tiempo del último cuadro
                lastFrame = now;

                // Incrementa el contador de cuadros
                frames++;
            }

            // Comprueba si ha pasado un segundo para calcular los FPS
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();

                // Imprime los FPS en la consola
                System.out.println("FPS: " + frames);

                // Reinicia el contador de cuadros
                frames = 0;
            }
        }
    }
}
