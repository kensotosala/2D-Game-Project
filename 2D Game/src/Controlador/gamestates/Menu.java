package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

// Clase que representa el estado del menú del juego
public class Menu extends State implements Statemethods {
	// Constantes para dimensiones y escalas
	private final int TILES_DEFAULT_SIZE = 32;
	private final float SCALE = 2f;
	private final int TILES_IN_WIDTH = 26;
	private final int TILES_IN_HEIGHT = 14;
	private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	private final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	private final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	// Arreglo de botones del menú
	private MenuButton[] buttons = new MenuButton[3];

	// Imágenes de fondo
	private BufferedImage backgroundImg;
	private BufferedImage backgroundImageMain;

	// Posiciones y dimensiones del menú
	private int menuX;
	private int menuY;
	private int menuWidth;
	private int menuHeight;

	// Instancia de la clase LoadSave para cargar recursos
	private LoadSave loadSave = new LoadSave();

	// Constructor de la clase Menu
	public Menu(Game game) {
		super(game);
		loadButtons(); // Carga los botones del menú
		loadBackground(); // Carga la imagen de fondo
		backgroundImageMain = loadSave.GetSpriteAtlas(loadSave.MENU_BACKGROUND_IMG);
	}

	// Carga la imagen de fondo del menú
	private void loadBackground() {
		backgroundImg = loadSave.GetSpriteAtlas(loadSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * SCALE);
		menuX = GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int) (45 * SCALE);

	}

	// Carga los botones del menú
	private void loadButtons() {
		buttons[0] = new MenuButton(GAME_WIDTH / 2, (int) (150 * SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton(GAME_WIDTH / 2, (int) (220 * SCALE), 1, Gamestate.OPTIONS);
		buttons[2] = new MenuButton(GAME_WIDTH / 2, (int) (290 * SCALE), 2, Gamestate.QUIT);
	}

	// Actualiza la lógica del menú
	@Override
	public void update() {
		for (MenuButton mb : buttons)
			mb.update();
	}

	// Dibuja el menú en pantalla
	@Override
	public void draw(Graphics g) {

		g.drawImage(backgroundImageMain, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

		for (MenuButton mb : buttons)
			mb.draw(g);
	}

	// Implementación de métodos de interacción con el mouse y teclado (omitiendo
	// detalles de implementación
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				break;
			}
		}

		resetButtons();

	}

	private void resetButtons() {
		for (MenuButton mb : buttons)
			mb.resetBools();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb : buttons)
			mb.setMouseOver(false);

		for (MenuButton mb : buttons)
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			Gamestate.state = Gamestate.PLAYING;

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}