package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;
import static utilz.Constants.Directions.*;

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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.setMoving(true);
                gamePanel.changeYDelta(-5);
                break;
            case KeyEvent.VK_S:
                gamePanel.setMoving(true);
                gamePanel.changeYDelta(5);
                break;
            case KeyEvent.VK_A:
                gamePanel.setMoving(true);
                gamePanel.changeXDelta(-5);
                break;
            case KeyEvent.VK_D:
                gamePanel.setMoving(true);
                gamePanel.changeXDelta(5);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                gamePanel.setMoving(false);
                break;
        }
    }
}
