// Paquete Vista
package Vista;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import Modelo.Game;
import Modelo.KeyboardInputs;
import Modelo.MouseInputs;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT));
        setFocusable(true);
        addKeyListener(new KeyboardInputs());
        addMouseListener(new MouseInputs());
        addMouseMotionListener(new MouseInputs());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public void updateGame() {
        game.update();
        repaint();
    }
}
