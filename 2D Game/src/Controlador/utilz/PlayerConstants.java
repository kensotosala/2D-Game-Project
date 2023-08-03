package utilz;

public class PlayerConstants {
    public final int IDLE = 0;
    public final int RUNNING = 1;
    public final int JUMP = 2;
    public final int FALLING = 3;
    public final int ATTACK = 4;
    public final int HIT = 5;
    public final int DEAD = 6;

    public int GetSpriteAmount(int player_action) {
        switch (player_action) {
            case DEAD:
                return 8;
            case RUNNING:
                return 6;
            case IDLE:
                return 5;
            case HIT:
                return 4;
            case JUMP:
            case ATTACK:
                return 3;
            case FALLING:
            default:
                return 1;
        }
    }
}