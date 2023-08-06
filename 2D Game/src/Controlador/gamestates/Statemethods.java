package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

// Interfaz que define los métodos comunes para los diferentes estados del juego
public interface Statemethods {
	// Método para actualizar la lógica del estado
	public void update();

	// Método para dibujar el estado en pantalla
	public void draw(Graphics g);

	// Métodos de interacción con el mouse
	public void mouseClicked(MouseEvent e);

	public void mousePressed(MouseEvent e);

	public void mouseReleased(MouseEvent e);

	public void mouseMoved(MouseEvent e);

	// Métodos de interacción con el teclado
	public void keyPressed(KeyEvent e);

	public void keyReleased(KeyEvent e);
}
