
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import utilz.EnemyConstants;

public class Crabmeat extends Enemy {
    private final float SCALE = 2f;

    private static final int RIGHT = 0;
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    private EnemyConstants enemyConstants;

    public Crabmeat(float x, float y, EnemyConstants enemyConstants) {
        super(x, y, enemyConstants.getCrabmeatWidth(), enemyConstants.getCrabmeatHeight(),
                enemyConstants.CRABMEAT);
        this.enemyConstants = enemyConstants;
        initHitbox(x, y, (int) (22 * SCALE), (int) (19 * SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (82 * SCALE), (int) (19 * SCALE));
        attackBoxOffsetX = (int) (SCALE * 30);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            updateInAir(lvlData);
        else {
            if (enemyState == enemyConstants.IDLE) {
                newState(enemyConstants.RUNNING);
            } else if (enemyState == enemyConstants.RUNNING) {
                if (canSeePlayer(lvlData, player)) {
                    turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player))
                        newState(enemyConstants.ATTACK);
                }

                move(lvlData);
            } else if (enemyState == enemyConstants.ATTACK) {
                if (aniIndex == 0)
                    attackChecked = false;
                if (aniIndex == 3 && !attackChecked)
                    checkPlayerHit(attackBox, player);
            } else if (enemyState == enemyConstants.HIT) {
                // Do something for the HIT state
            }
        }
    }

    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;
    }

    public int getEnemyType() {
        return enemyType;
    }
}
