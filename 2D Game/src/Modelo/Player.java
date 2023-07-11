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
    private BufferedImage[] attackAnimation; // Nueva variable para la animación de ataque
    private BufferedImage[] jumpAnimation; // Nueva variable para la animación de salto

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
    private float jumpSpeed;
    private int currentFrame;
    private boolean jumping;
    private float jumpForce;
    private float currentJumpVelocity;
    private float jumpHeight;
    private float jumpTime;
    private float jumpTimer;
    private float initialJumpVelocity;
    private boolean reachedPeak;
    private float originalY;

    public Player(int x, int y, List<Platform> platforms) {
        super(x, y);
        this.platforms = platforms;
        loadAnimations();
        moving = false;
        attacking = false;
        jumping = false;
        playerSpeed = 2.0f;
        collisionManager = new CollisionManager(platforms);
        jumping = false;
        jumpForce = 7.0f; // Ajusta la fuerza del salto según tus necesidades
        jumpHeight = 100.0f; // Ajusta la altura del salto según tus necesidades
        jumpTime = 0.4f; // Ajusta el tiempo total del salto según tus necesidades
        jumpTimer = 0.0f;
        initialJumpVelocity = 0.0f;
        reachedPeak = false;
        originalY = y;
    }

    public void update() {
        // Aplicar la gravedad si el jugador no está en el suelo o saltando
        if (!isOnGround() && !jumping) {
            velocityY += GRAVITY;
        }

        // Actualizar la posición del jugador
        x += velocityX;
        y += velocityY;

        if (moving) {
            // Reiniciar la animación de reposo
            currentFrame = 0;
        }

        // Comprobar si el jugador ha colisionado con una plataforma
        for (Platform platform : platforms) {
            if (collisionManager.checkCollision(this, platform)) {
                handleCollision(platform);
            }
        }

        // Actualizar el salto
        if (jumping) {
            if (jumpTimer < jumpTime) {
                jumpTimer += 0.01f; // Ajusta el valor para controlar la velocidad del salto

                // Calcular la velocidad actual del salto
                currentJumpVelocity = initialJumpVelocity - (jumpForce * jumpTimer);

                // Si el personaje alcanza su punto máximo de salto, invertir la dirección de la
                // velocidad
                if (currentJumpVelocity <= 0 && !reachedPeak) {
                    reachedPeak = true;
                    currentJumpVelocity *= -1;
                }

                // Actualizar la posición vertical según la velocidad del salto
                y -= currentJumpVelocity;

                // Si el personaje aterriza en el suelo, finalizar el salto
                if (y >= originalY) {
                    y = originalY;
                    jumping = false;
                    reachedPeak = false;
                    jumpTimer = 0.0f;
                    velocityY = 0.0f;
                }
            } else {
                // Si el tiempo del salto ha terminado, finalizar el salto
                jumping = false;
                reachedPeak = false;
                jumpTimer = 0.0f;
                velocityY = 0.0f;
            }
        }
    }

    public boolean isOnGround() {
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
            jumping = false; // El jugador aterriza en la plataforma y deja de saltar
        } else if (playerBounds.y > platformBounds.y) {
            y = platformBounds.y + platformBounds.height;
            velocityY = 0.0f;
        }

        // Asegurar que el jugador no salga de los límites de la pantalla
        if (x < 0) {
            x = 0;
        } else if (x > 1248 - getWidth()) { // 1248
            x = 1248 - getWidth(); // 672
        }
    }

    public void render(Graphics g) {
        int playerWidth = 100; // Nuevo ancho deseado del jugador
        int playerHeight = 100; // Nuevo alto deseado del jugador

        if (attacking) {
            // Renderizar animación de ataque
            g.drawImage(attackAnimation[currentFrame], (int) x, (int) y, playerWidth, playerHeight, null);

            // Avanzar al siguiente fotograma de la animación
            if (currentFrame < attackAnimation.length - 1) {
                // Incrementar el índice del fotograma actual
                currentFrame++;
            } else {
                // Reiniciar la animación al llegar al último fotograma
                attacking = false;
                currentFrame = 0;
            }
        } else if (jumping) {
            // Renderizar animación de salto
            g.drawImage(jumpAnimation[0], (int) x, (int) y, playerWidth, playerHeight, null);
        } else if (moving) {
            // Renderizar animación de correr
            int frameIndex = (int) (System.currentTimeMillis() / 100) % runningAnimation.length;

            // Asegurarse de que el índice del fotograma sea positivo
            if (frameIndex < 0) {
                frameIndex += runningAnimation.length;
            }

            g.drawImage(runningAnimation[frameIndex], (int) x, (int) y, playerWidth, playerHeight, null);
        } else {
            // Renderizar animación de reposo
            g.drawImage(idleAnimation[0], (int) x, (int) y, playerWidth, playerHeight, null);
        }
    }

    private void loadAnimations() {
        idleAnimation = loadAnimation("2D Game\\resources\\Idle");
        runningAnimation = new BufferedImage[12]; // Actualiza el tamaño del array a 12
        attackAnimation = loadAnimation("2D Game\\resources\\idle-attacking");
        jumpAnimation = loadAnimation("2D Game\\resources\\idle-jumping");

        for (int i = 0; i < runningAnimation.length; i++) {
            String imagePath = "2D Game\\resources\\idle-running\\sonic-running-" + (i + 4) + ".png"; // Actualiza el
                                                                                                      // índice de las
                                                                                                      // imágenes
            runningAnimation[i] = loadImage(imagePath);
        }
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

    private BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, getWidth(), getHeight());
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void jump() {
        if (!jumping && isOnGround()) {
            velocityY = -7.0f; // Establecer la velocidad vertical para el salto
            jumping = true; // El jugador está en el aire y saltando
        }
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public float getJumpSpeed() {
        return jumpSpeed;
    }

    public void setInitialJumpVelocity(float initialJumpVelocity) {
        this.initialJumpVelocity = initialJumpVelocity;
    }

    public float getInitialJumpSpeed() {
        return initialJumpVelocity;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isJumping() {
        return jumping;
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

    public int getWidth() {
        return 100; // Reemplaza el valor con el ancho deseado del jugador
    }

    public int getHeight() {
        return 100; // Reemplaza el valor con el alto deseado del jugador
    }

}
