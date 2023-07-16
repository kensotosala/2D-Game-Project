// Paquete Modelo
package Modelo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Controlador.Game;

public class KeyboardInputs implements KeyListener {
    private Player player;

    public KeyboardInputs() {
        player = Game.getInstance().getPlayer();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setMovingLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setMovingRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.jump();
                break;
            case KeyEvent.VK_SHIFT:
                player.setSprinting(true);
                break;
            case KeyEvent.VK_F:
                player.attack();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setMovingLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setMovingRight(false);
                break;
            case KeyEvent.VK_SHIFT:
                player.setSprinting(false);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
