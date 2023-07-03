package Modelo;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Player extends Entity {

    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private BufferedImage[] attackJumpAnimation;
    private BufferedImage[] attackAnimation;
    private BufferedImage[] hitAnimation;
    private BufferedImage[] jumpAnimation;
    private int animationTick = 30;
    private int animationIndex = 30;
    private int animationSpeed = 15;
    private boolean moving = false;
    private boolean attacking = false;
    private BufferedImage playerAction; // Cambiado el tipo de variable
    private static final int LEFT = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimation();
        loadRunninAnimation();
        loadAttackJumpAnimation();
        loadAttackAnimation();
        loadHitAnimation();
        loadJumpAnimation();
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        // Running Animation
        g.drawImage(playerAction, (int) x, (int) y, 128, 180, null);

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
            playerAction = runningAnimation[animationIndex];
        } else if (attacking) {
            playerAction = attackAnimation[animationIndex];
        } else {
            playerAction = idleAnimation[animationIndex];
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

    private void loadAnimation() {
        File folder = new File("2D Game\\resources\\Idle"); // Ruta del directorio donde se encuentran las imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            idleAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    idleAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadRunninAnimation() {
        File folder = new File("2D Game\\resources\\idle-running"); // Ruta del directorio donde se encuentran las
                                                                    // imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            runningAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    runningAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadAttackJumpAnimation() {
        File folder = new File("2D Game\\resources\\idle-attack-jump"); // Ruta del directorio donde se encuentran las
        // imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            attackJumpAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    attackJumpAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadAttackAnimation() {
        File folder = new File("2D Game\\resources\\idle-attacking"); // Ruta del directorio donde se encuentran las
        // imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            attackAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    attackAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadHitAnimation() {
        File folder = new File("2D Game\\resources\\idle-hit"); // Ruta del directorio donde se encuentran las
        // imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            hitAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    hitAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadJumpAnimation() {
        File folder = new File("2D Game\\resources\\idle-jumping"); // Ruta del directorio donde se encuentran las
        // imágenes
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            jumpAnimation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                try {
                    jumpAnimation[i] = ImageIO.read(imageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
