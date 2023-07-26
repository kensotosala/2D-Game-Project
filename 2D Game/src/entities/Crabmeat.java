package entities;

import static utilz.Constants.EnemyConstants.*;

import main.Game;

public class Crabmeat extends Enemy {

    public Crabmeat(float x, float y) {
        super(x, y, CRABMEAT_WIDTH, CRABMEAT_HEIGHT, CRABMEAT);
        initHitbox(x, y, (int) 22 * Game.SCALE, (int) 19 * Game.SCALE);
    }

}
