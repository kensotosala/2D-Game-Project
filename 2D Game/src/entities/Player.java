package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

import javax.imageio.ImageIO;

public class Player extends Entity {
    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private BufferedImage[] jumpingAnimation;
    private BufferedImage[] attackingAnimation;
    private BufferedImage[] hitAnimation;
    private BufferedImage[] jumpAttackAnimation;
    private int animationTick;
    private int animationIndex;
    private int animationSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean attacking = false;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private float playerSpeed = 2.0f;
    public Object resetDirBooleans;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    private void updatePosition() {
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

    public void render(Graphics g) {
        BufferedImage[] animationFrames;
        switch (playerAction) {
            case IDLE:
                animationFrames = idleAnimation;
                break;
            case RUNNING:
                animationFrames = runningAnimation;
                break;
            case JUMP:
                animationFrames = jumpingAnimation;
                break;
            case ATTACK:
                animationFrames = attackingAnimation;
                break;
            case ATTACK_JUMP:
                animationFrames = jumpAttackAnimation;
                break;
            case HIT:
                animationFrames = hitAnimation;
                break;
            default:
                animationFrames = idleAnimation;
                break;
        }

        int animationLength = animationFrames.length;

        g.drawImage(animationFrames[animationIndex % animationLength], (int) x, (int) y, 100, 100, null);
    }

    private void updateAnimationTick() {
        animationTick++;
        BufferedImage[] currentAnimation;

        switch (playerAction) {
            case IDLE:
                currentAnimation = idleAnimation;
                break;
            case RUNNING:
                currentAnimation = runningAnimation;
                break;
            case JUMP:
                currentAnimation = jumpingAnimation;
                break;
            case ATTACK:
                currentAnimation = attackingAnimation;
                break;
            case ATTACK_JUMP:
                currentAnimation = jumpAttackAnimation;
                break;
            case HIT:
                currentAnimation = hitAnimation;
                break;
            default:
                currentAnimation = idleAnimation;
                break;
        }

        int animationLength = currentAnimation.length;

        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= animationLength) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (attacking) {
            playerAction = ATTACK;
        }

        if (startAnimation != playerAction) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void loadAnimations() {
        // Idle Animation
        idleAnimation = new BufferedImage[13];
        String idleSpritesPath = "src/resources/Sonic";
        for (int i = 0; i < idleAnimation.length; i++) {
            String imageName = "/Sonic (" + (i + 1) + ").png";
            String imagePath = idleSpritesPath + imageName;
            try {
                idleAnimation[i] = ImageIO.read(new File(imagePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Running Animation
        runningAnimation = new BufferedImage[15];
        idleSpritesPath = "src/resources/Sonic-Corriendo";
        for (int i = 0; i < runningAnimation.length; i++) {
            String imageName = "/sonic-running-" + (i + 1) + ".png";
            String imagePath = idleSpritesPath + imageName;
            try {
                runningAnimation[i] = ImageIO.read(new File(imagePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Jumping Animation
        jumpingAnimation = new BufferedImage[4];
        idleSpritesPath = "src/resources/Sonic-Saltando";
        for (int i = 0; i < jumpingAnimation.length; i++) {
            String imageName = "/sonic-jumping-" + (i + 1) + ".png";
            String imagePath = idleSpritesPath + imageName;
            try {
                jumpingAnimation[i] = ImageIO.read(new File(imagePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Attacking Animation
        attackingAnimation = new BufferedImage[8];
        idleSpritesPath = "src/resources/Sonic-Ataque";
        for (int i = 0; i < attackingAnimation.length; i++) {
            String imageName = "/sonic-attack-" + (i + 1) + ".png";
            String imagePath = idleSpritesPath + imageName;
            try {
                attackingAnimation[i] = ImageIO.read(new File(imagePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Hit Animation
        hitAnimation = new BufferedImage[2];
        idleSpritesPath = "src/resources/Sonic-Golpeando";
        for (int i = 0; i < hitAnimation.length; i++) {
            String imageName = "/sonic-hit-" + (i + 1) + ".png";
            String imagePath = idleSpritesPath + imageName;
            try {
                hitAnimation[i] = ImageIO.read(new File(imagePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Jump Attack Animation
        jumpAttackAnimation = new BufferedImage[8];
        idleSpritesPath = "src/resources/Sonic-Salto-Ataque";
        for (int i = 0; i < jumpAttackAnimation.length; i++) {
            String imageName = "/sonic-attack-jump (" + (i + 1) + ").png";
            String imagePath = idleSpritesPath + imageName;
            try {
                jumpAttackAnimation[i] = ImageIO.read(new File(imagePath));
            } catch (Exception e) {
                e.printStackTrace();
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

}