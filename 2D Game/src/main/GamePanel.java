package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100;
    private float yDelta = 100;

    private BufferedImage img;
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
    private int playerDirection = -1;
    private boolean moving = false;

    // Constructor
    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        imporImg();
        loadAnimations();
        setPanelSize();
        setFocusable(true); // Request focus for keyboard events
        requestFocusInWindow();
        // Adds the key listener
        addKeyListener(new KeyboardInputs(this));
        // Adds the mouse listener
        addMouseListener(mouseInputs);
        // Adds the mouse motion listener
        addMouseMotionListener(mouseInputs);
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

        // Runnning Animation
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

    // Imports the image
    private void imporImg() {
        InputStream is = getClass().getResourceAsStream("/resources/Sonic/Sonic (7).png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Sets the size of the panel
    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

    }

    // Gets the mouse inputs
    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    // Gets the mouse inputs
    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    // Sets the position of the rectangle
    public void setRecPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    public void setDirection(int direction) {
        this.playerAction = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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
            }
        }
    }

    private void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    private void updatePosition() {
        float moveSpeed = 5.0f; // Adjust the movement speed as needed

        if (moving) {
            switch (playerDirection) {
                case LEFT:
                    xDelta -= moveSpeed;
                    break;
                case UP:
                    yDelta -= moveSpeed;
                    break;
                case RIGHT:
                    xDelta += moveSpeed;
                    break;
                case DOWN:
                    yDelta += moveSpeed;
                    break;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updatePosition();
        updateAnimationTick();
        setAnimation();

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

        int animationLength = animationFrames.length; // Update this line

        g.drawImage(animationFrames[animationIndex % animationLength], (int) xDelta, (int) yDelta, 100, 100, null); // Update
                                                                                                                    // this
                                                                                                                    // line
    }

}
