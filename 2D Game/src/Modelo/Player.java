// Paquete Modelo
package Modelo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private static final float GRAVITY = 0.3f;

    private boolean moving;
    private boolean attacking;
    private boolean jumping;
    private float velocityX;
    private float velocityY;
    private CollisionManager collisionManager;
    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private BufferedImage[] attackAnimation;
    private BufferedImage[] jumpAnimation;
    private int currentFrame;

    public Player(int x, int y, List<Platform> platforms) {
        super(x, y);
        this.platforms = platforms;
        this.collisionManager = new CollisionManager(platforms);
        this.moving = false;
        this.attacking = false;
        this.jumping = false;
        this.velocityX = 0.0f;
        this.velocityY = 0.0f;
        this.currentFrame = 0;
    }

    public void update() {
        applyGravity();
        updatePosition();
        updateAnimation();
        collisionManager.checkCollisions(this);
        handleJump();
    }

    private void applyGravity() {
        if (!jumping && !isOnGround()) {
            velocityY += GRAVITY;
        }
    }

    private void updatePosition() {
        x += velocityX;
        y += velocityY;
    }

    private void updateAnimation() {
        if (moving) {
            currentFrame = (currentFrame + 1) % runningAnimation.length;
        }
    }

    private void handleJump() {
        if (jumping) {
            if (velocityY < 0 && isHeadColliding()) {
                velocityY = 0;
            }

            if (velocityY >= 0 && isFeetColliding()) {
                jumping = false;
                velocityY = 0;
            }
        }
    }

    public void jump() {
        if (!jumping && isOnGround()) {
            jumping = true;
            velocityY = -7.0f;
        }
    }

    private boolean isOnGround() {
        Rectangle playerBounds = getBounds();

        for (Platform platform : platforms) {
            Rectangle platformBounds = platform.getBounds();

            if (playerBounds.intersects(platformBounds) && velocityY >= 0) {
                return true;
            }
        }

        return false;
    }

    private boolean isHeadColliding() {
        Rectangle playerBounds = getBounds();
        playerBounds.y -= 1;

        for (Platform platform : platforms) {
            Rectangle platformBounds = platform.getBounds();

            if (playerBounds.intersects(platformBounds)) {
                return true;
            }
        }

        return false;
    }

    private boolean isFeetColliding() {
        Rectangle playerBounds = getBounds();
        playerBounds.y += 1;

        for (Platform platform : platforms) {
            Rectangle platformBounds = platform.getBounds();

            if (playerBounds.intersects(platformBounds)) {
                return true;
            }
        }

        return false;
    }

    // Métodos getters y setters

    public void setMoving(boolean moving) {
        this.moving = moving;
        if (!moving) {
            velocityX = 0.0f;
        }
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    public BufferedImage[] getIdleAnimation() {
        return idleAnimation;
    }

    public BufferedImage[] getRunningAnimation() {
        return runningAnimation;
    }

    public BufferedImage[] getAttackAnimation() {
        return attackAnimation;
    }

    public BufferedImage[] getJumpAnimation() {
        return jumpAnimation;
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    @Override
    protected int getWidth() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWidth'");
    }

    @Override
    protected int getHeight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHeight'");
    }
}
