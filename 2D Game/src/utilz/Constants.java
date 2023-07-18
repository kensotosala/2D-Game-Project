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
		public static final int ATTACK_1 = 1;
		public static final int RUNNING = 2;
		public static final int FALLING = 3;
		public static final int JUMP = 4;
		public static final int GROUND = 5;
		public static final int HIT = 6;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
				case RUNNING:
				case IDLE:
				case ATTACK_1:
					return 6;
				case JUMP:
					return 6;
				case FALLING:
					return 2;
				default:
					return 1;
			}
		}

	}

}
