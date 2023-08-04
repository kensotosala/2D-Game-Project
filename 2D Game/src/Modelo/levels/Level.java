package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import entities.Crabmeat;
import utilz.HelpMethods;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;
	private Crabmeat[] crabmeats;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	private HelpMethods helpMethods = new HelpMethods();
	private final int TILES_DEFAULT_SIZE = 32;
	private final float SCALE = 2f;
	private final int TILES_IN_WIDTH = 26;
	private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);

	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		calcPlayerSpawn();
	}

	private void calcPlayerSpawn() {
		playerSpawn = helpMethods.GetPlayerSpawn(img);
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - TILES_IN_WIDTH;
		maxLvlOffsetX = TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		crabmeats = helpMethods.GetCrabs(img);
	}

	private void createLevelData() {
		lvlData = helpMethods.GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public Crabmeat[] getCrabmeats() {
		return crabmeats;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

}
