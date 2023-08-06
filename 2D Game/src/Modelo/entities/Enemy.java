package entities;

import java.awt.geom.Rectangle2D;
import utilz.Directions;
import utilz.EnemyConstants;
import utilz.HelpMethods;

// Clase abstracta que define el comportamiento genérico de los enemigos
public abstract class Enemy extends Entity {
    // Constantes relacionadas con el tamaño de los tiles
    private final int TILES_DEFAULT_SIZE = 32;
    private final float SCALE = 2f;
    private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);

    // Variables de estado y comportamiento
    public int aniIndex;
    public int enemyState;
    public int enemyType;
    public int aniTick = 0;
    public int aniSpeed = 25; // Ajustar según sea necesario
    public boolean firstUpdate = true;
    public boolean inAir;
    public float fallSpeed;
    public float gravity = 0.04f * SCALE;
    public float walkSpeed = 0.35f * SCALE;
    public int tileY;
    public float attackDistance = TILES_SIZE;
    public int maxHealth;
    public int currentHealth;
    public boolean active = true;
    public boolean attackChecked;

    // Instancias de utilidades y constantes
    public HelpMethods helpMethods = new HelpMethods();
    public EnemyConstants enemyConstants;
    public Directions directions = new Directions();
    public int walkDir = directions.LEFT;
    public int IDLE;
    public int RUNNING;
    public int ATTACK;
    public int HIT;
    public int DEAD;

    // Constructor que inicializa propiedades compartidas
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        enemyConstants = new EnemyConstants();
        maxHealth = enemyConstants.GetMaxHealth(enemyType);
        currentHealth = maxHealth;

        // Inicializar los estados de enemigo según las constantes
        IDLE = enemyConstants.IDLE;
        RUNNING = enemyConstants.RUNNING;
        ATTACK = enemyConstants.ATTACK;
        HIT = enemyConstants.HIT;
        DEAD = enemyConstants.DEAD;
    }

    // Método para verificar si es el primer ciclo de actualización
    public void firstUpdateCheck(int[][] lvlData) {
        inAir = !helpMethods.IsEntityOnFloor(hitbox, lvlData);
        firstUpdate = false;
    }

    // Método para actualizar el comportamiento en el aire
    public void updateInAir(int[][] lvlData) {
        if (helpMethods.CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = helpMethods.GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / TILES_SIZE);
        }
    }

    // Método para mover al enemigo
    public void move(int[][] lvlData) {
        float xSpeed = (walkDir == directions.LEFT) ? -walkSpeed : walkSpeed;

        if (helpMethods.CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            if (helpMethods.IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        }
        changeWalkDir();
    }

    // Método para girar hacia el jugador
    public void turnTowardsPlayer(Player player) {
        walkDir = (player.hitbox.x > hitbox.x) ? directions.RIGHT : directions.LEFT;
    }

    // Método para verificar si el jugador está visible
    public boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / TILES_SIZE);
        return (playerTileY == tileY) && isPlayerInRange(player)
                && helpMethods.IsSightClear(lvlData, hitbox, player.hitbox, tileY);
    }

    // Método para verificar si el jugador está en rango
    public boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    // Método para verificar si el jugador está cerca para atacar
    public boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    // Método para cambiar el estado del enemigo
    public void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    // Método para infligir daño al enemigo
    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0)
            newState(enemyConstants.DEAD);
        else
            newState(enemyConstants.HIT);
    }

    // Método para verificar el impacto en el jugador
    public void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox))
            player.changeHealth(-enemyConstants.GetEnemyDmg(enemyType));
        attackChecked = true;
    }

    // Método para actualizar el índice de animación
    public void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= enemyConstants.GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;

                int nextState = enemyState;
                if (enemyState == ATTACK || enemyState == HIT)
                    nextState = IDLE;
                else if (enemyState == DEAD) {
                    active = false;
                    nextState = DEAD;
                }
                enemyState = nextState;
            }
        }
    }

    // Método para cambiar la dirección del movimiento
    public void changeWalkDir() {
        walkDir = (walkDir == directions.LEFT) ? directions.RIGHT : directions.LEFT;
    }

    // Método para restablecer el estado del enemigo
    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        fallSpeed = 0;
    }

    // Métodos de obtención de información
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
