package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import utilz.EnemyConstants;
import utilz.LoadSave;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] crabmeatArr;
    private Crabmeat[] crabmeats;
    LoadSave loadSave = new LoadSave();
    EnemyConstants enemyConstants = new EnemyConstants();

    // Constructor que inicializa el administrador de enemigos
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    // Método para cargar los enemigos de un nivel
    public void loadEnemies(levels.Level level) {
        crabmeats = level.getCrabmeats();
    }

    // Método para actualizar el estado de los enemigos
    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false; // Indicador si hay enemigos activos
        for (int i = 0; i < crabmeats.length; i++) {
            Crabmeat c = crabmeats[i];
            if (c.isActive()) {
                c.update(lvlData, player); // Actualizar enemigo si está activo
                isAnyActive = true; // Indicar que al menos un enemigo está activo
            }
        }

        if (!isAnyActive) {
            playing.setLevelCompleted(true); // Establecer nivel como completado si no hay enemigos activos
        }

    }

    // Método para dibujar enemigos en la pantalla
    public void draw(Graphics g, int xLvlOffset) {
        drawCrabmeats(g, xLvlOffset); // Dibujar enemigos en la posición correcta
    }

    // Método auxiliar para dibujar los enemigos
    private void drawCrabmeats(Graphics g, int xLvlOffset) {
        for (int i = 0; i < crabmeats.length; i++) {
            Crabmeat c = crabmeats[i];
            if (c.isActive()) {
                BufferedImage[][] enemySprites = enemyConstants.GetEnemySprites(c.getEnemyType());
                if (enemySprites != null) { // Asegurarse de que los sprites no sean nulos
                    // Dibujar el sprite del enemigo en la posición correcta
                    g.drawImage(enemySprites[c.getEnemyState()][c.getAniIndex()],
                            (int) c.getHitbox().x - xLvlOffset - enemyConstants.CRABMEAT_DRAWOFFSET_X + c.flipX(),
                            (int) c.getHitbox().y - enemyConstants.CRABMEAT_DRAWOFFSET_Y,
                            enemySprites[c.getEnemyState()][c.getAniIndex()].getWidth(),
                            enemySprites[c.getEnemyState()][c.getAniIndex()].getHeight(), null);
                }
            }
        }
    }

    // Método para verificar si el área de ataque golpea a un enemigo
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (int i = 0; i < crabmeats.length; i++) {
            Crabmeat c = crabmeats[i];
            if (c.isActive() && attackBox.intersects(c.getHitbox())) {
                c.hurt(10); // Aplicar daño al enemigo si el área de ataque lo alcanza
                return;
            }
        }
    }

    // Método para cargar las imágenes de los enemigos - Bubblesort 
    private void loadEnemyImgs() {
        crabmeatArr = new BufferedImage[5][9];
        BufferedImage temp = loadSave.GetSpriteAtlas(loadSave.CRABMEAT_SPRITE);

        if (temp != null) {
            for (int j = 0; j < crabmeatArr.length; j++) {
                for (int i = 0; i < crabmeatArr[j].length; i++) {
                    BufferedImage subImage = temp.getSubimage(i * enemyConstants.CRABMEAT_WIDTH_DEFAULT,
                            j * enemyConstants.CRABMEAT_HEIGHT_DEFAULT,
                            enemyConstants.CRABMEAT_WIDTH_DEFAULT, enemyConstants.CRABMEAT_HEIGHT_DEFAULT);

                    if (subImage != null) {
                        crabmeatArr[j][i] = subImage;
                    } else {
                        // Manejar el caso en el que subImage es nulo
                        // Puede ser imprimir un mensaje de error o tomar otra acción
                    }
                }
            }
        }
    }

    // Método para restablecer todos los enemigos
    public void resetAllEnemies() {
        for (int i = 0; i < crabmeats.length; i++) {
            crabmeats[i].resetEnemy(); // Restablecer el estado de cada enemigo
        }
    }
}
