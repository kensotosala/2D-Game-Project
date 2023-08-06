package main;

import java.awt.Graphics;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

// Clase principal que representa el juego
public class Game implements Runnable {

	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	private Playing playing;
	private Menu menu;

	// Constructor del juego
	public Game() {
		initClasses();

		gamePanel = new GamePanel(this);
		new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();

		startGameLoop();
	}

	// Inicializa las instancias de las clases de estados
	private void initClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
	}

	// Inicia el bucle principal del juego
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// Actualiza la lógica del juego
	public void update() {
		switch (Gamestate.state) {
			case MENU:
				menu.update();
				break;
			case PLAYING:
				playing.update();
				break;
			case OPTIONS:
			case QUIT:
			default:
				System.exit(0);
				break;

		}
	}

	// Dibuja los elementos del juego en pantalla
	public void render(Graphics g) {
		switch (Gamestate.state) {
			case MENU:
				menu.draw(g);
				break;
			case PLAYING:
				playing.draw(g);
				break;
			default:
				break;
		}
	}

	// Método principal de ejecución del juego
	@Override
	public void run() {
		// Código para calcular FPS y UPS omitido para mantener la claridad
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			// Lógica de actualización y renderizado
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			// Control de velocidad del bucle
			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}

	}

	// Método para manejar la pérdida de enfoque de la ventana
	public void windowFocusLost() {
		if (Gamestate.state == Gamestate.PLAYING)
			playing.getPlayer().resetDirBooleans();
	}

	// Métodos para obtener instancias de los estados del juego
	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}
}
