package Modelo;

import java.awt.image.BufferedImage;

import Modelo.Utilities.LoadSave;

public class AnimationLoader {
    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private BufferedImage[] attackJumpAnimation;
    private BufferedImage[] attackAnimation;
    private BufferedImage[] hitAnimation;
    private BufferedImage[] jumpAnimation;

    public AnimationLoader() {
        LoadSave loader = new LoadSave();
        idleAnimation = loader.getIdleAnimation();
        runningAnimation = loader.getRunningAnimation();
        attackJumpAnimation = loader.getAttackJumpAnimation();
        attackAnimation = loader.getAttackAnimation();
        hitAnimation = loader.getHitAnimation();
        jumpAnimation = loader.getJumpAnimation();
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
}
