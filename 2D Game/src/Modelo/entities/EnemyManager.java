package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] crabmeatArr;
    private Crabmeat[] crabmeats;
    LoadSave loadSave = new LoadSave();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(levels.Level level) {
        crabmeats = level.getCrabmeats();
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for (int i = 0; i < crabmeats.length; i++) {
            Crabmeat c = crabmeats[i];
            if (c.isActive()) {
                c.update(lvlData, player);
                isAnyActive = true;
            }
        }

        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }

    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabmeats(g, xLvlOffset);
    }

    private void drawCrabmeats(Graphics g, int xLvlOffset) {
        for (int i = 0; i < crabmeats.length; i++) {
            Crabmeat c = crabmeats[i];
            if (c.isActive()) {
                g.drawImage(crabmeatArr[c.getEnemyState()][c.getAniIndex()],
                        (int) c.getHitbox().x - xLvlOffset - CRABMEAT_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitbox().y - CRABMEAT_DRAWOFFSET_Y,
                        CRABMEAT_WIDTH * c.flipW(), CRABMEAT_HEIGHT, null);
                // c.drawHitbox(g, xLvlOffset);
                // c.drawAttackBox(g, xLvlOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (int i = 0; i < crabmeats.length; i++) {
            Crabmeat c = crabmeats[i];
            if (c.isActive() && attackBox.intersects(c.getHitbox())) {
                c.hurt(10);
                return;
            }
        }
    }

    private void loadEnemyImgs() {
        crabmeatArr = new BufferedImage[5][9];
        BufferedImage temp = loadSave.GetSpriteAtlas(loadSave.CRABMEAT_SPRITE);
        for (int j = 0; j < crabmeatArr.length; j++)
            for (int i = 0; i < crabmeatArr[j].length; i++)
                crabmeatArr[j][i] = temp.getSubimage(i * CRABMEAT_WIDTH_DEFAULT, j * CRABMEAT_HEIGHT_DEFAULT,
                        CRABMEAT_WIDTH_DEFAULT, CRABMEAT_HEIGHT_DEFAULT);

    }

    public void resetAllEnemies() {
        for (int i = 0; i < crabmeats.length; i++) {
            crabmeats[i].resetEnemy();
        }
    }
}
