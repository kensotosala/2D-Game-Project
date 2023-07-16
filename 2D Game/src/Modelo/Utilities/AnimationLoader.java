// Paquete Modelo.Utilities
package Modelo.Utilities;

import java.awt.image.BufferedImage;
import java.io.File;

public class AnimationLoader {
    private SpriteLoader spriteLoader;

    public AnimationLoader() {
        spriteLoader = new SpriteLoader();
    }

    public BufferedImage[] loadAnimation(String folderPath) {
        File folder = new File(folderPath);
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null && imageFiles.length > 0) {
            BufferedImage[] animation = new BufferedImage[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                animation[i] = spriteLoader.getSprite(imageFiles[i].getPath());
            }

            return animation;
        }

        return null;
    }
}
