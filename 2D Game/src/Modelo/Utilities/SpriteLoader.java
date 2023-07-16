// Paquete Modelo.Utilities
package Modelo.Utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class SpriteLoader {
    private Map<String, BufferedImage> spriteMap;

    public SpriteLoader() {
        spriteMap = new HashMap<>();
    }

    public BufferedImage getSprite(String spritePath) {
        if (spriteMap.containsKey(spritePath)) {
            return spriteMap.get(spritePath);
        } else {
            BufferedImage sprite = loadSprite(spritePath);
            spriteMap.put(spritePath, sprite);
            return sprite;
        }
    }

    private BufferedImage loadSprite(String spritePath) {
        try {
            return ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
