package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gamestates.Gamestate;
import gamestates.Playing;

public class GameOverOverlay {
    private final int TILES_DEFAULT_SIZE = 32;
    private final float SCALE = 2f;
    private final int TILES_IN_WIDTH = 26;
    private final int TILES_IN_HEIGHT = 14;
    private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    private final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    private final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private Playing playing;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over", GAME_WIDTH / 2, 150);
        g.drawString("Press esc to enter Main Menu!", GAME_WIDTH / 2, 300);

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}