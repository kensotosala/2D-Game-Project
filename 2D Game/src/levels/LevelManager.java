package levels;

import com.b3dgs.tmx.TmxMap;
import com.b3dgs.tmx.TmxMapLoader;
import main.Game;

public class LevelManager {
    private Game game;
    private TmxMap level;

    public LevelManager(Game game) {
        this.game = game;
        loadLevel();
    }

    private void loadLevel() {
        TmxMapLoader loader = new TmxMapLoader();
        level = loader.load("src/resources/Levels/level_1.tmx");
    }

    public TmxMap getLevel() {
        return level;
    }
}
