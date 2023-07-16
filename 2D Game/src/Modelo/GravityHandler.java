package Modelo;

public class GravityHandler {
    private static final float GRAVITY = 0.5f;

    public void applyGravity(Player player) {
        if (!player.isJumping()) {
            player.setY(player.getY() + player.getVelocityY());
            player.setVelocityY(player.getVelocityY() + GRAVITY);
        }
    }

    public void jump(Player player) {
        if (!player.isJumping()) {
            player.setJumping(true);
            player.setVelocityY(-10.0f); // Establecer una velocidad vertical negativa para el salto
            player.setAnimationTick(0);
            player.setAnimationIndex(0);
        }
    }
}
