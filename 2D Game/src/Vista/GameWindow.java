// Paquete Vista
package Vista;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame frame;

    public GameWindow(GamePanel gamePanel) {
        frame = new JFrame("Game Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(gamePanel); // Agregar gamePanel al contenido del JFrame
        frame.pack(); // Ajustar el tamaño del JFrame al contenido
        frame.setLocationRelativeTo(null); // Centrar el JFrame en la pantalla
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
