package utilz;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import entities.Crabmeat;

public class HelpMethods {

    private EnemyConstants enemyConstants = new EnemyConstants(); // Instancia de la clase EnemyConstants para acceder a
                                                                  // constantes de enemigos
    private final int TILES_DEFAULT_SIZE = 32; // Tamaño predeterminado de los bloques en píxeles
    private final float SCALE = 2f; // Escala para el tamaño de los bloques
    private final int TILES_IN_HEIGHT = 14; // Número de bloques en la altura del nivel
    private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE); // Tamaño final de los bloques después de aplicar
                                                                       // la escala
    private final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT; // Altura total del juego en píxeles

    // Verifica si un objeto puede moverse a la posición indicada sin colisionar con
    // bloques sólidos
    public boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    // Verifica si una posición es sólida (bloque sólido o fuera de los límites del
    // nivel)
    private boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * TILES_SIZE; // Ancho total del nivel en píxeles
        if (x < 0 || x >= maxWidth)
            return true; // Si está fuera de los límites horizontales del nivel, es sólido
        if (y < 0 || y >= GAME_HEIGHT)
            return true; // Si está fuera de los límites verticales del nivel, es sólido
        float xIndex = x / TILES_SIZE; // Índice de columna del bloque
        float yIndex = y / TILES_SIZE; // Índice de fila del bloque

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData); // Verificar si el bloque es sólido
    }

    // Verifica si un bloque en coordenadas dadas es sólido
    public boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile]; // Obtener el valor del bloque en el nivel

        if (value >= 48 || value < 0 || value != 11)
            return true; // Si el valor está fuera del rango válido o no es el valor de un bloque sólido,
                         // es sólido
        return false;
    }

    // Obtiene la posición x de un objeto cerca de una pared para evitar colisiones
    public float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / TILES_SIZE); // Obtener el índice de columna del bloque actual
        if (xSpeed > 0) {
            // Movimiento hacia la derecha
            int tileXPos = currentTile * TILES_SIZE; // Posición x del bloque
            int xOffset = (int) (TILES_SIZE - hitbox.width); // Cantidad para ajustar la posición
            return tileXPos + xOffset - 1; // Alinear el objeto junto a la pared
        } else
            // Movimiento hacia la izquierda
            return currentTile * TILES_SIZE; // Mantener el objeto en la misma columna del bloque
    }

    // Obtiene la posición y de un objeto al tocar el techo o el suelo
    public float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / TILES_SIZE); // Obtener el índice de fila del bloque actual
        if (airSpeed > 0) {
            // Caida - toca el suelo
            int tileYPos = currentTile * TILES_SIZE; // Posición y del bloque
            int yOffset = (int) (TILES_SIZE - hitbox.height); // Cantidad para ajustar la posición
            return tileYPos + yOffset - 1; // Alinear el objeto junto al suelo
        } else
            // Saltando
            return currentTile * TILES_SIZE; // Mantener el objeto en la misma fila del bloque
    }

    // Verifica si un objeto está en el suelo
    public boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }

    // Verifica si un objeto está en una plataforma
    public boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0)
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        else
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    // Verifica si todos los bloques entre dos columnas son transitables
    public boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
            if (!IsTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }
        return true;
    }

    // Verifica si la línea de visión entre dos hitboxes está despejada
    public boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox,
            int yTile) {
        int firstXTile = (int) (firstHitbox.x / TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
    }

    // Obtiene los datos de los bloques del nivel desde una imagen
    public int[][] GetLevelData(BufferedImage img) {
        int[][] lvlData = new int[img.getHeight()][img.getWidth()]; // Crear arreglo para almacenar datos del nivel
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j)); // Obtener color del píxel
                int value = color.getRed(); // Obtener valor rojo (0-255) del color
                if (value >= 48)
                    value = 0; // Convertir valores mayores o iguales a 48 a 0 (no sólido)
                lvlData[j][i] = value; // Almacenar el valor en el arreglo de datos del nivel
            }
        return lvlData;
    }

    // Obtiene los datos de los enemigos Crabmeat desde una imagen
    public Crabmeat[] GetCrabs(BufferedImage img) {
        int crabmeatCount = 0; // Contador de enemigos Crabmeat
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j)); // Obtener color del píxel
                int value = color.getGreen(); // Obtener valor verde (0-255) del color
                if (value == enemyConstants.CRABMEAT) {
                    crabmeatCount++; // Incrementar el contador si se encuentra un enemigo Crabmeat
                }
            }
        }

        Crabmeat[] crabs = new Crabmeat[crabmeatCount]; // Crear arreglo para almacenar instancias de Crabmeat
        int crabIndex = 0; // Índice para el arreglo de Crabmeat

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j)); // Obtener color del píxel
                int value = color.getGreen(); // Obtener valor verde (0-255) del color
                if (value == enemyConstants.CRABMEAT) {
                    crabs[crabIndex] = new Crabmeat(i * TILES_SIZE, j * TILES_SIZE, enemyConstants); // Crear instancia
                                                                                                     // de Crabmeat y
                                                                                                     // agregar al
                                                                                                     // arreglo
                    crabIndex++; // Incrementar el índice para el siguiente Crabmeat
                }
            }
        }

        return crabs; // Devolver el arreglo de Crabmeat
    }

    // Obtiene la posición de spawn del jugador desde una imagen
    public Point GetPlayerSpawn(BufferedImage img) {
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j)); // Obtener color del píxel
                int value = color.getGreen(); // Obtener valor verde (0-255) del color
                if (value == 100)
                    return new Point(i * TILES_SIZE, j * TILES_SIZE); // Devolver posición si se encuentra el valor
                                                                      // adecuado
            }
        return new Point(1 * TILES_SIZE, 1 * TILES_SIZE); // Devolver posición predeterminada si no se encuentra el
                                                          // valor
    }

}