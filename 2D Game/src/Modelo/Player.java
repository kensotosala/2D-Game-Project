package Modelo;

<<<<<<< HEAD
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
=======
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
>>>>>>> parent of 986a7c4 (Gravedad, colisiones y salto)

public class Player {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int GRAVITY = 1;
    private static final int JUMP_HEIGHT = 15;

<<<<<<< HEAD
    private int x;
    private int y;
    private int velocityY;
    private boolean isJumping;
    private Rectangle boundingBox;
    private List<Rectangle> platforms;

    public Player(int x, int y, List<Rectangle> platforms) {
        this.x = x;
        this.y = y;
        this.platforms = platforms;
        boundingBox = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void update() {
        handleInput();
        applyGravity();
        checkCollision();
        updateBoundingBox();
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    private void handleInput() {
        if (KeyboardInputs.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= 5;
        }
        if (KeyboardInputs.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += 5;
        }
        if (KeyboardInputs.isKeyPressed(KeyEvent.VK_SPACE) && !isJumping) {
            jump();
=======
import Modelo.Utilities.LoadSave;

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
        loadRunningAnimation();
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
        if (moving) {
            g.drawImage(playerAction, (int) x, (int) y, 128, 180, null);
        } else {
            g.drawImage(idleAnimation[0], (int) x, (int) y, 128, 180, null);
>>>>>>> parent of 986a7c4 (Gravedad, colisiones y salto)
        }
    }

    private void applyGravity() {
        velocityY += GRAVITY;
        y += velocityY;
    }

<<<<<<< HEAD
    private void checkCollision() {
        for (Rectangle platform : platforms) {
            if (boundingBox.intersects(platform)) {
                // Handle collision
                velocityY = 0;
                y = platform.y - HEIGHT;
                isJumping = false;
=======
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

    private void loadAnimation() {
        LoadSave loader = new LoadSave();
        idleAnimation = loader.GetIdleAnimation();
    }

    private void loadRunningAnimation() {
        LoadSave loader = new LoadSave();
        runningAnimation = loader.GetRunningAnimation();
    }

    public void loadAttackJumpAnimation() {
        LoadSave loader = new LoadSave();
        attackJumpAnimation = loader.GetPlayerAtlas();
    }

    public void loadAttackAnimation() {
        LoadSave loader = new LoadSave();
        attackAnimation = loader.GetPlayerAtlas();
    }

    public void loadHitAnimation() {
        LoadSave loader = new LoadSave();
        hitAnimation = loader.GetPlayerAtlas();
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
>>>>>>> parent of 986a7c4 (Gravedad, colisiones y salto)
            }
        }
    }

<<<<<<< HEAD
    private void jump() {
        velocityY = -JUMP_HEIGHT;
        isJumping = true;
    }

    private void updateBoundingBox() {
        boundingBox.setBounds(x, y, WIDTH, HEIGHT);
=======
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
>>>>>>> parent of 986a7c4 (Gravedad, colisiones y salto)
    }
}
