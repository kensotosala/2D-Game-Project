package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    private BufferedImage[][] crabmeatArr;
    private Crabmeat[] crabmeats;
    private int crabmeatsCount = 0;

    public EnemyManager(Playing playing) {
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        crabmeats = LoadSave.GetCrabmeats();
        crabmeatsCount = crabmeats.length; // Add this line to update the crabmeatsCount correctly.
        System.out.println("Size of crabmeats: " + crabmeatsCount);
    }

    private void loadEnemyImgs() {
        crabmeatArr = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABMEAT_SPRITE);
        for (int j = 0; j < crabmeatArr.length; j++)
            for (int i = 0; i < crabmeatArr[j].length; i++)
                crabmeatArr[j][i] = temp.getSubimage(i * CRABMEAT_WIDTH_DEFAULT, j * CRABMEAT_HEIGHT_DEFAULT,
                        CRABMEAT_WIDTH_DEFAULT, CRABMEAT_HEIGHT_DEFAULT);

    }

    public void update(int[][] lvlData, Player player) {
        for (int i = 0; i < crabmeatsCount; i++) {
            crabmeats[i].update(lvlData, player);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabmeats(g, xLvlOffset);
    }

    private void drawCrabmeats(Graphics g, int xLvlOffset) {
        for (int i = 0; i < crabmeatsCount; i++) {
            Crabmeat c = crabmeats[i];
            int enemyState = c.getEnemyState();
            int aniIndex = c.getAniIndex();
            int x = (int) c.getHitbox().x - xLvlOffset;
            int y = (int) c.getHitbox().y;

            g.drawImage(crabmeatArr[enemyState][aniIndex], x, y, CRABMEAT_WIDTH, CRABMEAT_HEIGHT, null);
        }
    }

}
