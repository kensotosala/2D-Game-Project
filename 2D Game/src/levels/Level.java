package levels;

import java.awt.Graphics2D;

public class Level {
    private int[][] levelData;

    public Level(int[][] levelData) {
        this.levelData = levelData;
    }

    public int[][] getLevelData() {
        return levelData;
    }

    public void draw(Graphics2D g2d) {

    }
}
