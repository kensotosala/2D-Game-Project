package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

// Panel que se utiliza para renderizar el juego
public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private Game game;
	private final int TILES_DEFAULT_SIZE = 32;
	private final float SCALE = 2f;
	private final int TILES_IN_WIDTH = 26;
	private final int TILES_IN_HEIGHT = 14;
	private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	private final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	private final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	// Constructor del panel
	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(this);
		this.game = game;
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	// Establece el tamaño preferido del panel
	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
	}

	// Actualiza el estado del juego
	public void updateGame() {
		game.update();
	}

	// Pinta los elementos del juego en el panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}

	// Método para obtener la instancia del juego
	public Game getGame() {
		return game;
	}

}