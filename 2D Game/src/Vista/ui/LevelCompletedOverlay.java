package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import utilz.LoadSave;
import utilz.URMButtons;

public class LevelCompletedOverlay {

    private URMButtons urmButtons = new URMButtons();
    private Playing playing;
    private UrmButton menu, next;
    private BufferedImage img;
    private int bgX;
    private int bgY;
    private int bgW;
    private int bgH;
    LoadSave loadSave = new LoadSave(); // Crear una instancia de LoadSave
    private final int TILES_DEFAULT_SIZE = 32;
    private final float SCALE = 2f;
    private final int TILES_IN_WIDTH = 26;
    private final int TILES_IN_HEIGHT = 14;
    private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    private final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    private final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (330 * SCALE);
        int nextX = (int) (445 * SCALE);
        int y = (int) (195 * SCALE);
        next = new UrmButton(nextX, y, urmButtons.URM_SIZE, urmButtons.URM_SIZE, 0);
        menu = new UrmButton(menuX, y, urmButtons.URM_SIZE, urmButtons.URM_SIZE, 2);
    }

    private void initImg() {
        img = loadSave.GetSpriteAtlas(loadSave.COMPLETED_IMG);
        bgW = (int) (img.getWidth() * SCALE);
        bgH = (int) (img.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * SCALE);
    }

    public void draw(Graphics g) {
        // Added after youtube upload
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // Mostrar los contadores de estadísticas
        int partidasJugadas = playing.getPartidasJugadas();
        int partidasGanadas = playing.getPartidasGanadas();
        int partidasPerdidas = playing.getPartidasPerdidas();

        g.setColor(Color.WHITE);
        g.drawString("Partidas jugadas: " + partidasJugadas, 10, 20);
        g.drawString("Partidas ganadas: " + partidasGanadas, 10, 40);
        g.drawString("Partidas perdidas: " + partidasPerdidas, 10, 60);

        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }

    public void update() {
        next.update();
        menu.update();
    }

    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(next, e))
            next.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
            }
        } else if (isIn(next, e)) {
            if (next.isMousePressed()) {
                playing.loadNextLevel();
                playing.incrementPartidasJugadas();
                playing.incrementPartidasGanadas();
            }
        }

        menu.resetBools();
        next.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(next, e))
            next.setMousePressed(true);
    }
}
