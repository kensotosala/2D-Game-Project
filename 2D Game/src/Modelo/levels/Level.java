package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import entities.Crabmeat;
import main.Game;
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
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
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
