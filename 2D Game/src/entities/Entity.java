package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

	protected float x;
	protected float y;
	protected int width;
	protected int height;
	protected Rectangle2D.Float hitbox;

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	protected void drawHitbox(Graphics g) {
		g.setColor(Color.YELLOW);
		g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	protected void initHitbox(float x, float y, float f, float g) {
		hitbox = new Rectangle2D.Float(x, y, f, g);
	}

	// protected void updateHitbox() {
	// hitbox.x = (int) x;
	// hitbox.y = (int) y;
	// }

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

}
