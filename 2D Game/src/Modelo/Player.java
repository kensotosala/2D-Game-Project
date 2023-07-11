package Modelo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class Player extends Entity {

    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private boolean moving;
    private boolean attacking;
    private float playerSpeed;
    private static final float GRAVITY = 0.3f;
    private float velocityX;
    private float velocityY;
    private List<Platform> platforms;
    private CollisionManager collisionManager;
    private boolean up;
    private boolean left;
    private boolean down;
    private boolean right;
    private boolean jumping;
    private float jumpSpeed;

    public Player(int x, int y, List<Platform> platforms) {
        super(x, y);
        this.platforms = platforms;
        loadAnimations();
        moving = false;
        attacking = false;
        playerSpeed = 2.0f;
        collisionManager = new CollisionManager(platforms);
    }

    public void update() {
        // Aplicar la gravedad si el jugador no está en el suelo
        if (!isOnGround()) {
            velocityY += GRAVITY;
        }

        // Actualizar la posición del jugador
        x += velocityX;
        y += velocityY;

        // Comprobar si el jugador ha colisionado con una plataforma
        for (Platform platform : platforms) {
            if (collisionManager.checkCollision(this, platform)) {
                handleCollision(platform);
            }
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

    private void handleCollision(Platform platform) {
        Rectangle playerBounds = getBounds();
        Rectangle platformBounds = platform.getBounds();

        if (playerBounds.y < platformBounds.y) {
            y = platformBounds.y - playerBounds.height;
            velocityY = 0.0f;
        } else if (playerBounds.y > platformBounds.y) {
            y = platformBounds.y + platformBounds.height;
            velocityY = 0.0f;
        }
    }

    public void render(Graphics g) {
        int playerWidth = 100;
        int playerHeight = 100;

        if (moving) {
            // Reproducir animación de correr
            int frameIndex = (int) (System.currentTimeMillis() / 100) % runningAnimation.length;
            BufferedImage frame = runningAnimation[frameIndex];
            g.drawImage(frame, (int) x, (int) y, playerWidth, playerHeight, null);
        } else if (attacking) {
            // Reproducir animación de ataque
        } else {
            // Reproducir animación de reposo
            g.drawImage(idleAnimation[0], (int) x, (int) y, playerWidth, playerHeight, null);
        }
    }

    private void loadAnimations() {
        idleAnimation = loadAnimation("2D Game/resources/Idle");
        runningAnimation = loadAnimation("2D Game/resources/Running");
    }

    private BufferedImage[] loadAnimation(String folderPath) {
        try {
            File folder = new File(folderPath);
            File[] imageFiles = folder.listFiles();

            if (imageFiles != null && imageFiles.length > 0) {
                BufferedImage[] animation = new BufferedImage[imageFiles.length];

                for (int i = 0; i < imageFiles.length; i++) {
                    animation[i] = ImageIO.read(imageFiles[i]);
                }

                return animation;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, getWidth(), getHeight());
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public float getJumpSpeed() {
        return jumpSpeed;
    }

    public void setJumpSpeed(float jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int getWidth() {
        return 100;
    }

    public int getHeight() {
        return 100;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPlayerSpeed(float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
}
