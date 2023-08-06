package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import utilz.HelpMethods;
import utilz.LoadSave;
import utilz.PlayerConstants;

// Clase que representa al personaje del jugador en el juego
public class Player extends Entity {
	private PlayerConstants playerConstants = new PlayerConstants(); // Constantes relacionadas con el jugador
	private final float SCALE = 2f; // Escala para ajustar el tamaño de la entidad
	private BufferedImage[][] animations; // Matriz de animaciones para el jugador
	private int aniTick; // Contador para controlar la velocidad de la animación
	private int aniIndex; // Índice de la animación actual
	private int aniSpeed = 25; // Velocidad de la animación
	private int playerAction = playerConstants.IDLE; // Acción actual del jugador
	private boolean moving; // Indica si el jugador se está moviendo
	private boolean attacking; // Indica si el jugador está atacando
	private boolean left; // Indica si el jugador se está moviendo hacia la izquierda
	private boolean up; // Indica si el jugador se está moviendo hacia arriba
	private boolean right; // Indica si el jugador se está moviendo hacia la derecha
	private boolean down; // Indica si el jugador se está moviendo hacia abajo
	private boolean jump; // Indica si el jugador está saltando
	private float playerSpeed = 1.0f * SCALE; // Velocidad de movimiento del jugador
	private int[][] lvlData; // Datos del nivel del juego
	private float xDrawOffset = 21 * SCALE; // Desplazamiento horizontal para dibujar el jugador
	private float yDrawOffset = 4 * SCALE; // Desplazamiento vertical para dibujar el jugador

	// Salto / Gravedad
	private float airSpeed = 0f; // Velocidad en el aire
	private float gravity = 0.04f * SCALE; // Valor de gravedad
	private float jumpSpeed = -2.25f * SCALE; // Velocidad de salto
	private float fallSpeedAfterCollision = 0.5f * SCALE; // Velocidad de caída después de la colisión
	private boolean inAir = false; // Indica si el jugador está en el aire

	// StatusBarUI
	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * SCALE);
	private int statusBarHeight = (int) (58 * SCALE);
	private int statusBarX = (int) (10 * SCALE);
	private int statusBarY = (int) (10 * SCALE);

	private int healthBarWidth = (int) (150 * SCALE);
	private int healthBarHeight = (int) (4 * SCALE);
	private int healthBarXStart = (int) (34 * SCALE);
	private int healthBarYStart = (int) (14 * SCALE);

	private int maxHealth = 10;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	private HelpMethods helpMethods = new HelpMethods();

	// AttackBox
	private Rectangle2D.Float attackBox;

	private int flipX = 0;
	private int flipW = 1;

	private boolean attackChecked;
	private Playing playing;
	LoadSave loadSave = new LoadSave(); // Crear una instancia de LoadSave

	// Constructor para crear una instancia del jugador
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		loadAnimations();
		initHitbox(x, y, (int) (20 * SCALE), (int) (27 * SCALE));
		initAttackBox();
	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (20 * SCALE), (int) (20 * SCALE));
	}

	public void update() {
		updateHealthBar();

		if (currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}

		updateAttackBox();

		updatePos();
		if (attacking)
			checkAttack();
		updateAnimationTick();
		setAnimation();
	}

	private void checkAttack() {
		if (attackChecked || aniIndex != 1)
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
	}

	private void updateAttackBox() {
		if (right)
			attackBox.x = hitbox.x + hitbox.width + (int) (SCALE * 10);
		else if (left)
			attackBox.x = hitbox.x - hitbox.width - (int) (SCALE * 10);

		attackBox.y = hitbox.y + (SCALE * 10);
	}

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX,
				(int) (hitbox.y - yDrawOffset), width * flipW, height, null);
		drawUI(g);
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= playerConstants.GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;
			}
		}
	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving)
			playerAction = playerConstants.RUNNING;
		else
			playerAction = playerConstants.IDLE;

		if (inAir) {
			if (airSpeed < 0)
				playerAction = playerConstants.JUMP;
			else
				playerAction = playerConstants.FALLING;
		}

		if (attacking) {
			playerAction = playerConstants.ATTACK;
			if (startAni != playerConstants.ATTACK) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}
		}
		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();

		if (!inAir) {
			if ((!left && !right) || (right && left))
				return;
		}

		float xSpeed = 0;

		if (left) {
			xSpeed -= playerSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right) {
			xSpeed += playerSpeed;
			flipX = 0;
			flipW = 1;
		}

		if (!inAir) {
			if (!helpMethods.IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
		}

		if (inAir) {
			if (helpMethods.CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = helpMethods.GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		} else {
			updateXPos(xSpeed);
		}

		moving = true;
	}

	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if (helpMethods.CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed;
		else
			hitbox.x = helpMethods.GetEntityXPosNextToWall(hitbox, xSpeed);
	}

	public void changeHealth(int value) {
		currentHealth += value;

		if (currentHealth <= 0)
			currentHealth = 0;
		else if (currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}

	private void loadAnimations() {
		BufferedImage img = loadSave.GetSpriteAtlas(loadSave.PLAYER_ATLAS);
		animations = new BufferedImage[7][8];
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
			}
		}

		statusBarImg = loadSave.GetSpriteAtlas(loadSave.STATUS_BAR);
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!helpMethods.IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
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

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = playerConstants.IDLE;
		currentHealth = maxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if (!helpMethods.IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

	public int getHealth() {
		return 0;
	}
}
