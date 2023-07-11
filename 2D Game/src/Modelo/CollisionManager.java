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

        // Ajustar la posición del jugador para evitar que se superponga con la
        // plataforma
        if (playerBounds.y < platformBounds.y) {
            player.setY(platformBounds.y - playerBounds.height);
            player.setVelocityY(0.0f);
        } else if (playerBounds.y > platformBounds.y) {
            player.setY(platformBounds.y + platformBounds.height);
            player.setVelocityY(0.0f);
        }

        // Actualizar el estado del salto del jugador
        if (player.isJumping()) {
            player.setJumping(false);
            player.setVelocityY(0.0f);
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
