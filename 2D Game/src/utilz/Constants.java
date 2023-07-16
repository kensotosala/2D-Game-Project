package utilz;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int ATTACK = 3;
        public static final int ATTACK_JUMP = 4;
        public static final int HIT = 5;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case IDLE:
                    return 13;
                case RUNNING:
                    return 15;
                case JUMP:
                    return 4;
                case ATTACK:
                    return 8;
                case ATTACK_JUMP:
                    return 8;
                case HIT:
                    return 2;
                default:
                    return 13;
            }

        }
    }

}
