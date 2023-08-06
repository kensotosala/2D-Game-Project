package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import entities.Crabmeat;
import utilz.HelpMethods;

// Clase que representa un nivel en el juego
public class Level {

	private BufferedImage img; // Imagen del nivel
	private int[][] lvlData; // Datos del nivel
	private Crabmeat[] crabmeats; // Arreglo de enemigos Crabmeat en el nivel
	private int lvlTilesWide; // Ancho del nivel en cantidad de tiles
	private int maxTilesOffset; // Máximo desplazamiento en tiles
	private int maxLvlOffsetX; // Máximo desplazamiento horizontal en píxeles
	private Point playerSpawn; // Punto de aparición del jugador
	private HelpMethods helpMethods = new HelpMethods(); // Instancia de HelpMethods para funciones auxiliares
	private final int TILES_DEFAULT_SIZE = 32; // Tamaño de un tile por defecto
	private final float SCALE = 2f; // Escala para ajustar el tamaño
	private final int TILES_IN_WIDTH = 26; // Cantidad de tiles visibles en el ancho
	private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE); // Tamaño de un tile ajustado

	// Constructor de la clase Level
	public Level(BufferedImage img) {
		this.img = img; // Asigna la imagen del nivel
		createLevelData(); // Crea los datos del nivel
		createEnemies(); // Crea los enemigos
		calcLvlOffsets(); // Calcula los desplazamientos del nivel
		calcPlayerSpawn(); // Calcula el punto de aparición del jugador
	}

	// Calcula el punto de aparición del jugador
	private void calcPlayerSpawn() {
		playerSpawn = helpMethods.GetPlayerSpawn(img);
	}

	// Calcula los desplazamientos del nivel
	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth(); // Ancho del nivel en cantidad de tiles
		maxTilesOffset = lvlTilesWide - TILES_IN_WIDTH; // Máximo desplazamiento en tiles
		maxLvlOffsetX = TILES_SIZE * maxTilesOffset; // Máximo desplazamiento horizontal en píxeles
	}

	// Crea los enemigos
	private void createEnemies() {
		crabmeats = helpMethods.GetCrabs(img);
	}

	// Crea los datos del nivel
	private void createLevelData() {
		lvlData = helpMethods.GetLevelData(img);
	}

	// Obtiene el índice de sprite en una posición dada
	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	// Obtiene los datos del nivel
	public int[][] getLevelData() {
		return lvlData;
	}

	// Obtiene el desplazamiento máximo del nivel
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	// Obtiene el arreglo de enemigos Crabmeat
	public Crabmeat[] getCrabmeats() {
		return crabmeats;
	}

	// Obtiene el punto de aparición del jugador
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
}
