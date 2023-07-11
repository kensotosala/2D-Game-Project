package Modelo.Utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class LoadSave {
    public static BufferedImage[] idleAnimation;
    public static BufferedImage[] runningAnimation;
    public static BufferedImage[] attackJumpAnimation;
    public static BufferedImage[] attackAnimation;
    public static BufferedImage[] hitAnimation;
    public static BufferedImage[] jumpAnimation;

    public BufferedImage[] getIdleAnimation() {
        if (idleAnimation == null) {
            List<BufferedImage> frames = loadFramesFromFolder("2D Game/resources/Idle");
            idleAnimation = frames.toArray(new BufferedImage[0]);
        }
        return idleAnimation;
    }

    public BufferedImage[] getRunningAnimation() {
        if (runningAnimation == null) {
            List<BufferedImage> frames = loadFramesFromFolder("2D Game/resources/idle-running");
            runningAnimation = frames.toArray(new BufferedImage[0]);
        }
        return runningAnimation;
    }

    public BufferedImage[] getAttackJumpAnimation() {
        if (attackJumpAnimation == null) {
            attackJumpAnimation = GetPlayerAtlas();
        }
        return attackJumpAnimation;
    }

    public BufferedImage[] getAttackAnimation() {
        if (attackAnimation == null) {
            attackAnimation = GetPlayerAtlas();
        }
        return attackAnimation;
    }

    public BufferedImage[] getHitAnimation() {
        if (hitAnimation == null) {
            hitAnimation = GetPlayerAtlas();
        }
        return hitAnimation;
    }

    public BufferedImage[] getJumpAnimation() {
        if (jumpAnimation == null) {
            List<BufferedImage> frames = loadFramesFromFolder("2D Game/resources/idle-jumping");
            jumpAnimation = frames.toArray(new BufferedImage[0]);
        }
        return jumpAnimation;
    }

    private BufferedImage[] GetPlayerAtlas() {
        BufferedImage[] idle = getIdleAnimation();
        BufferedImage[] running = getRunningAnimation();

        List<BufferedImage> allFrames = new ArrayList<>();
        if (idle != null) {
            for (BufferedImage frame : idle) {
                allFrames.add(frame);
            }
        }
        if (running != null) {
            for (BufferedImage frame : running) {
                allFrames.add(frame);
            }
        }
        // Agregar otras animaciones...

        return allFrames.toArray(new BufferedImage[0]);
    }

    private List<BufferedImage> loadFramesFromFolder(String folderPath) {
        List<BufferedImage> frames = new ArrayList<>();
        File folder = new File(folderPath);
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            for (File file : imageFiles) {
                try {
                    BufferedImage frame = ImageIO.read(file);
                    frames.add(frame);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return frames;
    }
}
