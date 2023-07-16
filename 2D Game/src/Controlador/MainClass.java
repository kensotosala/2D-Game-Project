// Paquete Controlador
package Controlador;

import Modelo.Game;
import Vista.GamePanel;
import Vista.GameWindow;
import Modelo.Player;
import Modelo.KeyboardInputs;
import Modelo.MouseInputs;

public class MainClass {
    public static void main(String[] args) {
        Game game = new Game();
        GamePanel gamePanel = new GamePanel(game);
        GameWindow gameWindow = new GameWindow(gamePanel);

        Player player = game.getPlayer(); // Obtener la instancia de Player desde el juego

        // Crear instancias de KeyboardInputs y MouseInputs con el jugador y el
        // gamePanel como argumentos
        KeyboardInputs keyboardInputs = new KeyboardInputs(player);
        MouseInputs mouseInputs = new MouseInputs(gamePanel, player);

        // Agregar los listeners de teclado y ratón al gamePanel
        gamePanel.addKeyListener(keyboardInputs);
        gamePanel.addMouseListener(mouseInputs);
        gamePanel.addMouseMotionListener(mouseInputs);

        game.start();
    }
}
