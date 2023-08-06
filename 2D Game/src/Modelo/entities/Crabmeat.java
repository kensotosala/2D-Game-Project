package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import utilz.EnemyConstants;

// La clase Crabmeat extiende la clase Enemy, representa un enemigo específico del juego
public class Crabmeat extends Enemy {
    private final float SCALE = 2f; // Escala para ajustar el tamaño de los elementos

    private final int RIGHT = 0; // Constante que representa la dirección hacia la derecha
    private Rectangle2D.Float attackBox; // Área de ataque del enemigo
    private int attackBoxOffsetX; // Desplazamiento horizontal del área de ataque
    private EnemyConstants enemyConstants; // Constantes específicas de los enemigos

    // Constructor de Crabmeat
    public Crabmeat(float x, float y, EnemyConstants enemyConstants) {
        // Llama al constructor de la clase base (Enemy) y establece el tamaño y tipo de
        // enemigo
        super(x, y, enemyConstants.getCrabmeatWidth(), enemyConstants.getCrabmeatHeight(),
                enemyConstants.CRABMEAT);
        this.enemyConstants = enemyConstants; // Asigna las constantes de los enemigos
        initHitbox(x, y, (int) (22 * SCALE), (int) (19 * SCALE)); // Inicializa el área de colisión
        initAttackBox(); // Inicializa el área de ataque
    }

    // Inicializa el área de ataque
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (82 * SCALE), (int) (19 * SCALE)); // Crea el área de ataque
        attackBoxOffsetX = (int) (30 * SCALE); // Establece el desplazamiento horizontal del área de ataque
    }

    // Actualiza el comportamiento del enemigo
    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player); // Actualiza el comportamiento del enemigo
        updateAnimationTick(); // Actualiza la animación del enemigo
        updateAttackBox(); // Actualiza el área de ataque
    }

    // Actualiza el área de ataque
    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX; // Actualiza la posición horizontal del área de ataque
        attackBox.y = hitbox.y; // Mantiene la posición vertical del área de ataque
    }

    // Actualiza el comportamiento del enemigo
    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData); // Verifica si es la primera actualización

        if (inAir)
            updateInAir(lvlData); // Actualiza el comportamiento en el aire
        else {
            if (enemyState == enemyConstants.IDLE) {
                newState(enemyConstants.RUNNING); // Cambia al estado de "correr" si está en estado "idle"
            } else if (enemyState == enemyConstants.RUNNING) {
                if (canSeePlayer(lvlData, player)) {
                    turnTowardsPlayer(player); // Gira hacia el jugador si es visible
                    if (isPlayerCloseForAttack(player))
                        newState(enemyConstants.ATTACK); // Cambia al estado de "ataque" si el jugador está cerca
                }

                move(lvlData); // Mueve el enemigo
            } else if (enemyState == enemyConstants.ATTACK) {
                if (aniIndex == 0)
                    attackChecked = false; // Reinicia el chequeo de ataque
                if (aniIndex == 3 && !attackChecked)
                    checkPlayerHit(attackBox, player); // Verifica si el jugador fue golpeado durante el ataque
            } else if (enemyState == enemyConstants.HIT) {
                // Realiza alguna acción para el estado de "golpe"
            }
        }
    }

    // Dibuja el área de ataque en el gráfico
    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red); // Establece el color rojo para el área de ataque
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height); // Dibuja
                                                                                                                        // el
                                                                                                                        // área
                                                                                                                        // de
                                                                                                                        // ataque
                                                                                                                        // ajustado
                                                                                                                        // al
                                                                                                                        // desplazamiento
                                                                                                                        // horizontal
    }

    // Devuelve la coordenada X o la anchura reflejada según la dirección del
    // enemigo
    public int flipX() {
        if (walkDir == RIGHT)
            return width; // Si el enemigo mira hacia la derecha, devuelve la anchura
        else
            return 0; // Si el enemigo mira hacia la izquierda, devuelve 0
    }

    // Devuelve -1 o 1 según la dirección del enemigo
    public int flipW() {
        if (walkDir == RIGHT)
            return -1; // Si el enemigo mira hacia la derecha, devuelve -1
        else
            return 1; // Si el enemigo mira hacia la izquierda, devuelve 1
    }

    // Devuelve el tipo de enemigo
    public int getEnemyType() {
        return enemyType; // Devuelve el tipo de enemigo
    }
}
