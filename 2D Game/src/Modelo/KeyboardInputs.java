package Modelo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
    private Player player;

    public KeyboardInputs(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
<<<<<<< HEAD
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            player.jump();
        } else if (key == KeyEvent.VK_LEFT) {
            player.moveLeft();
        } else if (key == KeyEvent.VK_RIGHT) {
            player.moveRight();
=======
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(true);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(true);
                break;
>>>>>>> parent of 986a7c4 (Gravedad, colisiones y salto)
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
<<<<<<< HEAD
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player.stopMoving();
        }
    }
=======
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(false);
                break;
        }
    }

>>>>>>> parent of 986a7c4 (Gravedad, colisiones y salto)
}
