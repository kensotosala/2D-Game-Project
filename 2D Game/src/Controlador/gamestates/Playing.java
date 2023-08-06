package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;

// Clase que representa el estado de juego mientras se está jugando
public class Playing extends State implements Statemethods {
    // Constantes para dimensiones y escalas
    private final int TILES_DEFAULT_SIZE = 32;
    private final float SCALE = 2f;
    private final int TILES_IN_WIDTH = 26;
    private final int TILES_IN_HEIGHT = 14;
    private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    private final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    private final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    // Instancias de clases de gestión y elementos del juego
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    // Variables para controlar el estado del juego
    private boolean paused = false;
    private boolean gameOver;
    private boolean lvlCompleted;
    private boolean lostGame = false;

    // Variables para controlar el desplazamiento horizontal del nivel
    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * GAME_WIDTH);
    private int rightBorder = (int) (0.8 * GAME_WIDTH);
    private int maxLvlOffsetX;

    // Estadísticas del juego
    private int partidasJugadas = 0;
    private int partidasGanadas = 0;
    private int partidasPerdidas = 0;

    // Constructor de la clase Playing
    public Playing(Game game) {
        super(game);
        initClasses();
        calcLvlOffset();
        loadStartLevel();
    }

    // Inicializa las instancias de clases y elementos del juego
    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);

        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }

    // Carga el siguiente nivel del juego
    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        calcLvlOffset();
    }

    // Carga el nivel inicial del juego
    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
    }

    // Calcula el desplazamiento horizontal del nivel
    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    // Actualiza la lógica del juego
    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
        } else if (lvlCompleted) {
            levelCompletedOverlay.update();
        } else if (!gameOver) {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
        } else {
            if (!lostGame) {
                lostGame = true;
            }
        }
    }

    // Verifica si el jugador está cerca de los bordes y ajusta el desplazamiento
    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    // Dibuja los elementos del juego en pantalla
    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver)
            gameOverOverlay.draw(g);
        else if (lvlCompleted)
            levelCompletedOverlay.draw(g);
    }

    // Métodos para gestionar interacciones del jugador y el estado del juego
    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        player.resetAll();
        enemyManager.resetAllEnemies();

        if (lostGame) {
            incrementPartidasPerdidas(); // Incrementar el contador de partidas perdidas en 1
            incrementPartidasJugadas(); // Incrementar el contador de partidas jugadas en 1
            lostGame = false;
        }
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }

    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mousePressed(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseReleased(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseMoved(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mouseMoved(e);
        }
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unpauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    // Métodos para actualizar las estadísticas y variables del juego
    public void incrementPartidasJugadas() {
        partidasJugadas++;
    }

    public void incrementPartidasGanadas() {
        partidasGanadas++;
    }

    public void incrementPartidasPerdidas() {
        partidasPerdidas++;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

}
