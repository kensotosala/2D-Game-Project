package Vista;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame jframe;

    // Constructor
    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();

        // Window size
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true);
    }
}
