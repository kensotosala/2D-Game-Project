// Paquete Modelo
package Modelo;

import java.awt.Rectangle;
import java.util.List;

public class CollisionManager {
    private List<Platform> platforms;

    public CollisionManager(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public boolean checkCollision(Player player, Platform platform) {
        Rectangle playerBounds = player.getBounds();
        Rectangle platformBounds = platform.getBounds();

        return playerBounds.intersects(platformBounds);
    }

    public void handleCollision(Player player, Platform platform) {
        Rectangle playerBounds = player.getBounds();
        Rectangle platformBounds = platform.getBounds();

        if (playerBounds.y < platformBounds.y) {
            player.setY(platformBounds.y - playerBounds.height);
            player.setVelocityY(0.0f);
            player.setJumping(false);
        } else if (playerBounds.y > platformBounds.y) {
            player.setY(platformBounds.y + platformBounds.height);
            player.setVelocityY(0.0f);
        }

        if (player.getX() < 0) {
            player.setX(0);
        } else if (player.getX() > Game.GAME_WIDTH - player.getWidth()) {
            player.setX(Game.GAME_WIDTH - player.getWidth());
        }
    }

    public void checkCollisions(Player player) {
        for (Platform platform : platforms) {
            if (checkCollision(player, platform)) {
                handleCollision(player, platform);
            }
        }
    }
}
