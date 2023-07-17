package main;

import java.awt.Graphics;
<<<<<<< HEAD
import java.awt.Graphics2D;
=======
>>>>>>> parent of 98b21e4 (Level Creation)

import entities.Player;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 120;

    private Player player;

    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
<<<<<<< HEAD
        levelManager = new LevelManager(this);
        player = new Player(200, 200, 50, 50);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
=======
        player = new Player(200, 200);
>>>>>>> parent of 98b21e4 (Level Creation)
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    public void render(Graphics g) {
<<<<<<< HEAD
        Graphics2D g2d = (Graphics2D) g;

        // Clear the screen
        g2d.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // Draw the level map
        levelManager.draw(g2d);

        // Draw the player on top of the level
        player.render(g2d);
    }
=======
        player.render(g);
>>>>>>> parent of 98b21e4 (Level Creation)

    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        // Game loop
        while (true) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                // Update()
                updates++;
                deltaU--;
                update();
            }

            if (deltaF >= 1) {
                // Draw()
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // Increments the frame count
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }

            try {
                // Limit the game loop to the target FPS
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
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