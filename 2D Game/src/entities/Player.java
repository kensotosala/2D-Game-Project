package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.Constants;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
	private float playerSpeed = 2.0f;
	private int[][] lvlData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);
	}

	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g) {
		if (playerAction >= 0 && playerAction < animations.length) {
			BufferedImage[] animation = animations[playerAction];
			int numSubimages = Constants.PlayerConstants.GetSpriteAmount(playerAction);

			// Calculate the index to ensure it's within the valid range
			aniIndex %= numSubimages;

			if (aniIndex >= 0 && aniIndex < animation.length) {
				g.drawImage(animation[aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width,
						height, null);
			}
			drawHitbox(g);
		}
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}

		}

	}

	private void setAnimation() {
		int startAni = playerAction;

		if (attacking) {
			playerAction = ATTACK_1;
		} else if (moving) {
			playerAction = RUNNING;
		} else {
			playerAction = IDLE;
		}

		if (startAni != playerAction) {
			resetAniTick();
		}
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;
		if (!left && !right && !up && !down) {
			return;
		}

		float xSpeed = 0;
		float ySpeed = 0;

		if (left && !right) {
			xSpeed = -playerSpeed;
		} else if (right && !left) {
			xSpeed = playerSpeed;
		}
		if (up && !down) {
			ySpeed = -playerSpeed;
		} else if (down && !up) {
			ySpeed = playerSpeed;
		}

		// if (CanMoveHere(x + xSpeed, y + ySpeed, width, height, lvlData)) {
		// this.x += xSpeed;
		// this.y += ySpeed;
		// moving = true;
		// }

		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
			hitbox.y += ySpeed;
			moving = true;
		}

	}

	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

		animations = new BufferedImage[5][6];

		int subimageWidth = 64;
		int subimageHeight = 64;

		// Idle animation
		for (int i = 0; i < animations[0].length; i++) {
			animations[0][i] = img.getSubimage(i * subimageWidth, 0, subimageWidth, subimageHeight);
		}

		// Attack animation
		for (int i = 0; i < animations[1].length; i++) {
			animations[1][i] = img.getSubimage(i * subimageWidth, subimageHeight, subimageWidth, subimageHeight);
		}

		// Running animation
		for (int i = 0; i < animations[2].length; i++) {
			animations[2][i] = img.getSubimage(i * subimageWidth, subimageHeight * 2, subimageWidth, subimageHeight);
		}

		// Falling animation
		for (int i = 0; i < animations[3].length; i++) {
			animations[3][i] = img.getSubimage(i * subimageWidth, subimageHeight * 3, subimageWidth, subimageHeight);
		}

		// Jump animation
		for (int i = 0; i < animations[4].length; i++) {
			animations[4][i] = img.getSubimage(i * subimageWidth, subimageHeight * 4, subimageWidth, subimageHeight);
		}
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

}
