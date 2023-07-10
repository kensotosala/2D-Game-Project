package Modelo;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Modelo.Utilities.LoadSave;

public class AnimationLoader {

    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private BufferedImage[] attackJumpAnimation;
    private BufferedImage[] attackAnimation;
    private BufferedImage[] hitAnimation;
    private BufferedImage[] jumpAnimation;

    public AnimationLoader() {
        loadAnimation();
        loadRunningAnimation();
        loadAttackJumpAnimation();
        loadAttackAnimation();
        loadHitAnimation();
        loadJumpAnimation();
    }

    public BufferedImage[] getIdleAnimation() {
        return idleAnimation;
    }

    public BufferedImage[] getRunningAnimation() {
        return runningAnimation;
    }

    public BufferedImage[] getAttackJumpAnimation() {
        return attackJumpAnimation;
    }

    public BufferedImage[] getAttackAnimation() {
        return attackAnimation;
    }

    public BufferedImage[] getHitAnimation() {
        return hitAnimation;
    }

    public BufferedImage[] getJumpAnimation() {
        return jumpAnimation;
    }

    private void loadAnimation() {
        LoadSave loader = new LoadSave();
        idleAnimation = loader.GetIdleAnimation();
    }

    private void loadRunningAnimation() {
        LoadSave loader = new LoadSave();
        runningAnimation = loader.GetRunningAnimation();
    }

    private void loadAttackJumpAnimation() {
        LoadSave loader = new LoadSave();
        attackJumpAnimation = loader.GetPlayerAtlas();
    }

    private void loadAttackAnimation() {
        LoadSave loader = new LoadSave();
        attackAnimation = loader.GetPlayerAtlas();
    }

    private void loadHitAnimation() {
        LoadSave loader = new LoadSave();
        hitAnimation = loader.GetPlayerAtlas();
    }

    private void loadJumpAnimation() {
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
}