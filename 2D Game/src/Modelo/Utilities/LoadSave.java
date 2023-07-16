// Paquete Modelo.Utilities
package Modelo.Utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import javax.imageio.ImageIO;

=======
>>>>>>> parent of b118cba (Arreglo de plataformas)
public class LoadSave {
    public static BufferedImage[] idleAnimation;
    public static BufferedImage[] runningAnimation;
    public static BufferedImage[] attackJumpAnimation;
    public static BufferedImage[] attackAnimation;
    public static BufferedImage[] hitAnimation;
    public static BufferedImage[] jumpAnimation;

    public static BufferedImage[] getIdleAnimation() {
        if (idleAnimation == null) {
            idleAnimation = loadFramesFromFolder("2D Game/resources/Idle");
        }
        return idleAnimation;
    }

    public static BufferedImage[] getRunningAnimation() {
        if (runningAnimation == null) {
            runningAnimation = loadFramesFromFolder("2D Game/resources/idle-running");
        }
        return runningAnimation;
    }

    public static BufferedImage[] getAttackJumpAnimation() {
        if (attackJumpAnimation == null) {
            attackJumpAnimation = getPlayerAtlas();
        }
        return attackJumpAnimation;
    }

    public static BufferedImage[] getAttackAnimation() {
        if (attackAnimation == null) {
            attackAnimation = getPlayerAtlas();
        }
        return attackAnimation;
    }

    public static BufferedImage[] getHitAnimation() {
        if (hitAnimation == null) {
            hitAnimation = getPlayerAtlas();
        }
        return hitAnimation;
    }

    public static BufferedImage[] getJumpAnimation() {
        if (jumpAnimation == null) {
            jumpAnimation = loadFramesFromFolder("2D Game/resources/idle-jumping");
        }
        return jumpAnimation;
    }

    private static BufferedImage[] getPlayerAtlas() {
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

    private static BufferedImage[] loadFramesFromFolder(String folderPath) {
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

        return frames.toArray(new BufferedImage[0]);
    }
<<<<<<< HEAD
=======

    // Otros métodos para cargar las demás animaciones...

    public BufferedImage[] GetPlayerAtlas() {
        BufferedImage[] idle = GetIdleAnimation();
        BufferedImage[] running = GetRunningAnimation();
        // Carga de otras animaciones...

        // Combina todas las animaciones en una sola matriz
        BufferedImage[][] allAnimations = {
                idle,
                running,
                // Otras animaciones...
        };

        int totalFrames = 0;

        for (BufferedImage[] animation : allAnimations) {
            if (animation != null) {
                totalFrames += animation.length;
            }
        }

        BufferedImage[] playerAtlas = new BufferedImage[totalFrames];
        int frameIndex = 0;

        for (BufferedImage[] animation : allAnimations) {
            if (animation != null) {
                System.arraycopy(animation, 0, playerAtlas, frameIndex, animation.length);
                frameIndex += animation.length;
            }
        }

        return playerAtlas;
    }
>>>>>>> parent of b118cba (Arreglo de plataformas)
}
