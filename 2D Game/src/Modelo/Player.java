package Modelo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {

    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private BufferedImage[] attackJumpAnimation;
    private BufferedImage[] attackAnimation;
    private BufferedImage[] hitAnimation;
    private BufferedImage[] jumpAnimation;
    private int animationTick;
    private int animationIndex;
    private int animationSpeed;
    private boolean moving;
    private boolean attacking;
    private boolean jumping;
    private boolean spacePressed;
    private BufferedImage playerAction;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private float playerSpeed;
    private static final float GRAVITY = 0.3f; // Constante de gravedad
    private float yVelocity; // Velocidad vertical del jugador

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
        animationTick = 0;
        animationIndex = 0;
        animationSpeed = 15;
        moving = false;
        attacking = false;
        jumping = false;
        spacePressed = false;
        playerSpeed = 2.0f;
    }

    public void update(Platform[] platforms) {
        updatePos();
        updateAnimationTick();
        setAnimation();

        // Control de salto
        if (spacePressed && !jumping) {
            jumping = true;
            yVelocity = -10.0f; // Establecer una velocidad vertical negativa para el salto
            animationTick = 0;
            animationIndex = 0;
        }

        // Aplicar la gravedad al jugador
        if (!jumping) {
            y += yVelocity;
            yVelocity += GRAVITY;
        }

        spacePressed = false; // Reiniciar el estado de la tecla de salto

        // Verificar colisiones con las plataformas
        for (Platform platform : platforms) {
            if (CollisionManager.checkCollision(this, platform)) {
                handleCollision(platform);
            }
        }
    }

    private void handleCollision(Platform platform) {
        Rectangle playerBounds = getBounds();
        Rectangle platformBounds = platform.getBounds();

        // Ajustar la posición del jugador para evitar que se superponga con la
        // plataforma
        if (playerBounds.y < platformBounds.y) {
            y = platformBounds.y - playerBounds.height;
            jumping = false;
            yVelocity = 0.0f;
        } else if (playerBounds.y > platformBounds.y) {
            y = platformBounds.y + platformBounds.height;
            yVelocity = 0.0f;
        }
    }

    public void render(Graphics g) {
        int playerWidth = 100; // Nuevo ancho deseado del jugador
        int playerHeight = 100; // Nuevo alto deseado del jugador

        if (moving) {
            g.drawImage(playerAction, (int) x, (int) y, playerWidth, playerHeight, null);
        } else {
            g.drawImage(idleAnimation[0], (int) x, (int) y, playerWidth, playerHeight, null);
        }
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex = (animationIndex + 1) % jumpAnimation.length;
            attacking = false;
        }
    }

    public void setAnimation() {
        BufferedImage startAnimation = playerAction;

        if (moving) {
            playerAction = runningAnimation[animationIndex % runningAnimation.length];
        } else if (attacking) {
            playerAction = attackAnimation[animationIndex % attackAnimation.length];
        } else {
            playerAction = idleAnimation[animationIndex % idleAnimation.length];
        }

        if (!playerAction.equals(startAnimation)) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
    }

    private void updatePos() {
        moving = false;

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }

    private void loadAnimations() {
        // Cargar las animaciones desde los archivos
        idleAnimation = loadAnimation("2D Game\\resources\\Idle");
        runningAnimation = loadAnimation("2D Game\\resources\\idle-running");
        attackJumpAnimation = loadAnimation("2D Game\\resources\\idle-attack-jump");
        attackAnimation = loadAnimation("2D Game\\resources\\idle-attacking");
        hitAnimation = loadAnimation("2D Game\\resources\\idle-hit");
        jumpAnimation = loadAnimation("2D Game\\resources\\idle-jumping");
    }

    private BufferedImage[] loadAnimation(String folderPath) {
        File folder = new File(folderPath);
        File[] imageFiles = folder.listFiles();
        BufferedImage[] animation = new BufferedImage[imageFiles.length];

        for (int i = 0; i < imageFiles.length; i++) {
            try {
                animation[i] = ImageIO.read(imageFiles[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return animation;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public boolean isJumping() {
        return jumping;
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public void setAnimationTick(int animationTick) {
        this.animationTick = animationTick;
    }

    public void setAnimationIndex(int animationIndex) {
        this.animationIndex = animationIndex;
    }

    private Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, getWidth(), getHeight());
    }
}

// Quedamos en que hay un problema con el código y estabas en cuestiones de
// salto, gravedad y colisiones