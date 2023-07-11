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

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                player.setRight(false);
                player.setMoving(true);
                player.setVelocityX(-player.getPlayerSpeed()); // Actualizar la velocidad hacia la izquierda
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                player.setLeft(false);
                player.setMoving(true);
                player.setVelocityX(player.getPlayerSpeed()); // Actualizar la velocidad hacia la derecha
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Player player = gamePanel.getGame().getPlayer();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                if (!player.isRight()) {
                    player.setMoving(false);
                    player.setVelocityX(0.0f);
                }
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                if (!player.isLeft()) {
                    player.setMoving(false);
                    player.setVelocityX(0.0f);
                }
                break;
        }
    }
}
