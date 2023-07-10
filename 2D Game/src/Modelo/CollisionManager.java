package Modelo;

import java.awt.Rectangle;

public class CollisionManager {

    public static boolean checkCollision(Player player, Platform platform) {
        Rectangle playerBounds = new Rectangle((int) player.getX(), (int) player.getY(), player.getWidth(),
                player.getHeight());
        Rectangle platformBounds = new Rectangle((int) platform.getX(), (int) platform.getY(), platform.getWidth(),
                platform.getHeight());

        return playerBounds.intersects(platformBounds);
    }
}
