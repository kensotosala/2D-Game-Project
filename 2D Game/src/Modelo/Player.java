package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

public class Player {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int GRAVITY = 1;
    private static final int JUMP_HEIGHT = 15;

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
        }
    }

    private void applyGravity() {
        velocityY += GRAVITY;
        y += velocityY;
    }

    private void checkCollision() {
        for (Rectangle platform : platforms) {
            if (boundingBox.intersects(platform)) {
                // Handle collision
                velocityY = 0;
                y = platform.y - HEIGHT;
                isJumping = false;
            }
        }
    }

    private void jump() {
        velocityY = -JUMP_HEIGHT;
        isJumping = true;
    }

    private void updateBoundingBox() {
        boundingBox.setBounds(x, y, WIDTH, HEIGHT);
    }
}
