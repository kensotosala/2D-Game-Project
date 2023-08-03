package entities;

import java.awt.geom.Rectangle2D;

import main.Game;
import utilz.Directions;
import utilz.EnemyConstants;
import utilz.HelpMethods;

public abstract class Enemy extends Entity {
    protected int aniIndex;
    protected int enemyState;
    protected int enemyType;
    protected int aniTick = 25;
    protected int aniSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.35f * Game.SCALE;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;
    private HelpMethods helpMethods = new HelpMethods();
    protected EnemyConstants enemyConstants;
    private Directions directions = new Directions();
    protected int walkDir = directions.LEFT;
    protected int IDLE;
    protected int RUNNING;
    protected int ATTACK;
    protected int HIT;
    protected int DEAD;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        enemyConstants = new EnemyConstants();
        maxHealth = enemyConstants.GetMaxHealth(enemyType);
        currentHealth = maxHealth;

        // Asignar los valores constantes según el tipo de enemigo
        IDLE = enemyConstants.IDLE;
        RUNNING = enemyConstants.RUNNING;
        ATTACK = enemyConstants.ATTACK;
        HIT = enemyConstants.HIT;
        DEAD = enemyConstants.DEAD;
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!helpMethods.IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if (helpMethods.CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = helpMethods.GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = 0;

        if (walkDir == directions.LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (helpMethods.CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
            if (helpMethods.IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }

        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x)
            walkDir = directions.RIGHT;
        else
            walkDir = directions.LEFT;
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == tileY)
            if (isPlayerInRange(player)) {
                if (helpMethods.IsSightClear(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }

        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0)
            newState(enemyConstants.DEAD);
        else
            newState(enemyConstants.HIT);
    }

    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox))
            player.changeHealth(-enemyConstants.GetEnemyDmg(enemyType));
        attackChecked = true;

    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= enemyConstants.GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;

                int nextState = enemyState;
                switch (enemyState) {
                    case 2: // ATTACK
                    case 3: // HIT
                        nextState = 0; // IDLE
                        break;
                    case 4: // DEAD
                        active = false;
                        nextState = 4; // DEAD
                        break;
                }

                enemyState = nextState;
            }
        }
    }

    protected void changeWalkDir() {
        if (walkDir == directions.LEFT)
            walkDir = directions.RIGHT;
        else
            walkDir = directions.LEFT;
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(enemyConstants.IDLE);
        active = true;
        fallSpeed = 0;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean isActive() {
        return active;
    }

}