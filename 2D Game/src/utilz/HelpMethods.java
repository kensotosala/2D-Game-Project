package utilz;

import main.Game;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, int width, int height, int[][] lvlData) {
        if (lvlData == null) {
            return false; // or handle the null case appropriately
        }

        int tileX = (int) (x / Game.TILES_SIZE);
        int tileY = (int) (y / Game.TILES_SIZE);

        return !IsSolid(tileX, tileY, lvlData);
    }

    private static boolean IsSolid(int tileX, int tileY, int[][] lvlData) {
        if (tileX < 0 || tileY < 0 || tileX >= lvlData[0].length || tileY >= lvlData.length) {
            return true;
        }

        int tileType = lvlData[tileY][tileX];
        // Compare tileType with solid tile types in your game and return true if it's
        // solid

        return false;
    }
}
