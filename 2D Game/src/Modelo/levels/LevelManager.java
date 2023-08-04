package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

public class LevelManager {
	private final int TILES_DEFAULT_SIZE = 32;
	private final float SCALE = 2f;
	private final int TILES_IN_HEIGHT = 14;
	private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);

	private Game game;
	private BufferedImage[] levelSprite;
	private Node head; // Nodo principal de la LinkedList
	private int lvlIndex = 0;
	LoadSave loadSave = new LoadSave(); // Crear una instancia de LoadSave

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		buildAllLevels();
	}

	// Clase Node para implementar la LinkedList
	private class Node {
		Level data;
		Node next;

		public Node(Level data) {
			this.data = data;
			next = null;
		}
	}

	public void loadNextLevel() {
		lvlIndex++;
		if (lvlIndex >= getAmountOfLevels()) {
			lvlIndex = 0;
			System.out.println("No more levels! Game Completed!");
			Gamestate.state = Gamestate.MENU;
		}

		Level newLevel = getLevelAtIndex(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
	}

	private void buildAllLevels() {
		BufferedImage[] allLevels = loadSave.GetAllLevels();
		head = null; // Inicializar la LinkedList con el nodo principal como null
		Node current = null;

		for (int i = 0; i < allLevels.length; i++) {
			BufferedImage img = allLevels[i];
			Level newLevel = new Level(img);
			Node newNode = new Node(newLevel);
			if (head == null) {
				head = newNode;
				current = head;
			} else {
				current.next = newNode;
				current = current.next;
			}
		}
	}

	private void importOutsideSprites() {
		BufferedImage img = loadSave.GetSpriteAtlas(loadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	public void draw(Graphics g, int lvlOffset) {
		for (int j = 0; j < TILES_IN_HEIGHT; j++)
			for (int i = 0; i < getCurrentLevel().getLevelData()[0].length; i++) {
				int index = getCurrentLevel().getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], TILES_SIZE * i - lvlOffset, TILES_SIZE * j, TILES_SIZE,
						TILES_SIZE, null);
			}
	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return getLevelAtIndex(lvlIndex);
	}

	public int getAmountOfLevels() {
		int count = 0;
		Node current = head;
		while (current != null) {
			count++;
			current = current.next;
		}
		return count;
	}

	private Level getLevelAtIndex(int index) {
		int count = 0;
		Node current = head;
		while (current != null) {
			if (count == index) {
				return current.data;
			}
			count++;
			current = current.next;
		}
		throw new IndexOutOfBoundsException("Index out of range: " + index);
	}
}
