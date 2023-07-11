package Modelo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Vista.GamePanel;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Player player = gamePanel.getGame().getPlayer();
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            player.setUp(true);
        } else if (keyCode == KeyEvent.VK_A) {
            player.setLeft(true);
        } else if (keyCode == KeyEvent.VK_S) {
            player.setDown(true);
        } else if (keyCode == KeyEvent.VK_D) {
            player.setRight(true);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            player.setJumping(true);
            player.setJumpSpeed(5.0f); // Asigna la velocidad de salto adecuada
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Player player = gamePanel.getGame().getPlayer();
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            player.setUp(false);
        } else if (keyCode == KeyEvent.VK_A) {
            player.setLeft(false);
        } else if (keyCode == KeyEvent.VK_S) {
            player.setDown(false);
        } else if (keyCode == KeyEvent.VK_D) {
            player.setRight(false);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            player.setJumping(false);
        }
    }
}
