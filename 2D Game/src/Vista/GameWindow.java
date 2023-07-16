// Paquete Vista
package Vista;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame frame;

    public GameWindow(GamePanel gamePanel) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
