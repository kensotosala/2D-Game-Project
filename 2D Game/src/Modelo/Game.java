package Modelo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import Modelo.Levels.LevelManager;

public class Game {
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;

    private static Game instance;

    private Player player;
    private LevelManager levelManager;
    private KeyboardInputs keyboardInputs;
    private MouseInputs mouseInputs;

    public Game() {
        levelManager = new LevelManager("2D Game/resources/Platforms");
        player = new Player(100, 100, levelManager.getPlatforms());
        keyboardInputs = new KeyboardInputs(player);
        mouseInputs = new MouseInputs(player);
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Player getPlayer() {
        return player;
    }

    public void update() {
        player.update();
    }

    public void render(Graphics g) {
        levelManager.draw(g);
        player.render(g);
    }

    public void start() {
    }
}
