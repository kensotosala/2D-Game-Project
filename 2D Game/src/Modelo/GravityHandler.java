package Modelo;

public class GravityHandler {
    private static final float GRAVITY = 0.5f;
    private float yVelocity;

    public void update(Player player) {
        if (!player.isJumping()) {
            player.setY(player.getY() + yVelocity);
            yVelocity += GRAVITY;
        }
    }

    public void jump(Player player) {
        if (!player.isJumping()) {
            player.setJumping(true);
            yVelocity = -10.0f; // Establecer una velocidad vertical negativa para el salto
            player.setAnimationTick(0);
            player.setAnimationIndex(0);
        }
    }
}
