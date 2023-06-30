package Modelo.Utilities;

public class Constants {
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK = 6;
        public static final int ATTACK_JUMP = 7;

        public static int GetSpriteAmount(int playerAction) {
            switch (playerAction) {
                case RUNNING:
                    return 15;
                case IDLE:
                    return 13;
                case JUMP:
                    return 4;
                case HIT:
                    return 2;
                case ATTACK:
                    return 4;
                case ATTACK_JUMP:
                    return 8;
                default:
                    return 1;
            }
        }
    }
}
