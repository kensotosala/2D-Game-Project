package gamestates;

import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

// Clase base para los diferentes estados del juego
public class State {
    // Referencia al objeto de juego
    protected Game game;

    // Constructor de la clase State
    public State(Game game) {
        this.game = game;
    }

    // Método para verificar si el puntero del mouse está dentro de los límites de
    // un botón del menú
    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    // Método para obtener el objeto de juego
    public Game getGame() {
        return game;
    }
}
