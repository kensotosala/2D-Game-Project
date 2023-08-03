package utilz;

import main.Game;
import java.awt.image.BufferedImage;

public class EnemyConstants {
    public final int CRABMEAT = 0;

    public final int IDLE = 0;
    public final int RUNNING = 1;
    public final int ATTACK = 2;
    public final int HIT = 3;
    public final int DEAD = 4;

    public final int CRABMEAT_WIDTH_DEFAULT = 72;
    public final int CRABMEAT_HEIGHT_DEFAULT = 32;

    public final int CRABMEAT_WIDTH = (int) (CRABMEAT_WIDTH_DEFAULT * Game.SCALE);
    public final int CRABMEAT_HEIGHT = (int) (CRABMEAT_HEIGHT_DEFAULT * Game.SCALE);

    public final int CRABMEAT_DRAWOFFSET_X = (int) (26 * Game.SCALE);
    public final int CRABMEAT_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

    private BufferedImage[][] crabmeatArr;
    private LoadSave loadSave = new LoadSave();

    public EnemyConstants() {
        loadEnemyImgs();
    }

    public int GetSpriteAmount(int enemy_type, int enemy_state) {
        switch (enemy_type) {
            case CRABMEAT:
                switch (enemy_state) {
                    case IDLE:
                        return 9;
                    case RUNNING:
                        return 6;
                    case ATTACK:
                        return 7;
                    case HIT:
                        return 4;
                    case DEAD:
                        return 5;
                }
        }
        return 0;
    }

    public int GetMaxHealth(int enemy_type) {
        switch (enemy_type) {
            case CRABMEAT:
                return 10;
            default:
                return 1;
        }
    }

    public int GetEnemyDmg(int enemy_type) {
        switch (enemy_type) {
            case CRABMEAT:
                return 15;
            default:
                return 0;
        }
    }

    public int getCrabmeatWidth() {
        return (int) (CRABMEAT_WIDTH_DEFAULT * Game.SCALE);
    }

    public int getCrabmeatHeight() {
        return (int) (CRABMEAT_HEIGHT_DEFAULT * Game.SCALE);
    }

    public BufferedImage[][] GetEnemySprites(int enemy_type) {
        switch (enemy_type) {
            case CRABMEAT:
                return crabmeatArr;
            default:
                return null;
        }
    }

    private void loadEnemyImgs() {
        crabmeatArr = new BufferedImage[5][9];
        BufferedImage temp = loadSave.GetSpriteAtlas(loadSave.CRABMEAT_SPRITE);
        for (int j = 0; j < crabmeatArr.length; j++) {
            for (int i = 0; i < crabmeatArr[j].length; i++) {
                crabmeatArr[j][i] = temp.getSubimage(i * CRABMEAT_WIDTH_DEFAULT, j * CRABMEAT_HEIGHT_DEFAULT,
                        CRABMEAT_WIDTH_DEFAULT, CRABMEAT_HEIGHT_DEFAULT);
            }
        }
    }
}
