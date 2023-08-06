package gamestates;

// Definición de un enum para representar los diferentes estados del juego
public enum Gamestate {

    // Estado de juego en el que se está actualmente jugando
    PLAYING,

    // Estado de juego que representa el menú principal
    MENU,

    // Estado de juego que representa la pantalla de opciones
    OPTIONS,

    // Estado de juego que representa la acción de salir del juego
    QUIT;

    // Variable estática para mantener el estado actual del juego
    public static Gamestate state = MENU;

}
