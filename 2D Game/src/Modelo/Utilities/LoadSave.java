package Modelo.Utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoadSave {
    public static BufferedImage[] idleAnimation;
    public static BufferedImage[] runningAnimation;
    public static BufferedImage[] attackJumpAnimation;
    public static BufferedImage[] attackAnimation;
    public static BufferedImage[] hitAnimation;
    public static BufferedImage[] jumpAnimation;

    public BufferedImage[] GetIdleAnimation() {
        File idleFolder = new File("2D Game\\resources\\Idle");
        File[] idleImageFiles = idleFolder.listFiles();

        if (idleImageFiles != null) {
            idleAnimation = new BufferedImage[idleImageFiles.length];

            for (int i = 0; i < idleImageFiles.length; i++) {
                try {
                    idleAnimation[i] = ImageIO.read(idleImageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return idleAnimation;
    }

    public BufferedImage[] GetRunningAnimation() {
        File runningFolder = new File("2D Game\\resources\\idle-running");
        File[] runningImageFiles = runningFolder.listFiles();

        if (runningImageFiles != null) {
            runningAnimation = new BufferedImage[runningImageFiles.length];

            for (int i = 0; i < runningImageFiles.length; i++) {
                try {
                    runningAnimation[i] = ImageIO.read(runningImageFiles[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return runningAnimation;
    }

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
}
