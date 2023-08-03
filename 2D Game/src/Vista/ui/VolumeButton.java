package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import utilz.VolumeButtons;

public class VolumeButton extends PauseButton {

    private BufferedImage[] imgs;
    private BufferedImage slider;
    private int index = 0;
    private boolean mouseOver;
    private boolean mousePressed;
    private int buttonX;
    private int minX;
    private int maxX;
    private LoadSave loadSave = new LoadSave(); // Crear una instancia de LoadSave
    private VolumeButtons volumeButtons; // No lo inicializamos aquí

    public VolumeButton(int x, int y, int width, int height, VolumeButtons volumeButtons) {
        super(x, y, volumeButtons.VOLUME_WIDTH, height);
        this.volumeButtons = volumeButtons;
        bounds.x -= volumeButtons.VOLUME_WIDTH / 2;
        buttonX = x + width / 2;
        this.x = x;
        this.width = width;
        minX = x + volumeButtons.VOLUME_WIDTH / 2;
        maxX = x + width - volumeButtons.VOLUME_WIDTH / 2;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp = loadSave.GetSpriteAtlas(loadSave.VOLUME_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * volumeButtons.VOLUME_DEFAULT_WIDTH, 0, volumeButtons.VOLUME_DEFAULT_WIDTH,
                    volumeButtons.VOLUME_DEFAULT_HEIGHT);

        slider = temp.getSubimage(3 * volumeButtons.VOLUME_DEFAULT_WIDTH, 0, volumeButtons.SLIDER_DEFAULT_WIDTH,
                volumeButtons.VOLUME_DEFAULT_HEIGHT);

    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;

    }

    public void draw(Graphics g) {

        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(imgs[index], buttonX - volumeButtons.VOLUME_WIDTH / 2, y, volumeButtons.VOLUME_WIDTH, height, null);

    }

    public void changeX(int x) {
        if (x < minX)
            buttonX = minX;
        else if (x > maxX)
            buttonX = maxX;
        else
            buttonX = x;

        bounds.x = buttonX - volumeButtons.VOLUME_WIDTH / 2;

    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}