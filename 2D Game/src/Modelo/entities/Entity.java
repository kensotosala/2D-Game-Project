package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

// Clase abstracta que define el comportamiento general de una entidad en el juego
public abstract class Entity {

	// Propiedades de la entidad
	public float x; // Coordenada X de la entidad
	public float y; // Coordenada Y de la entidad
	public int width; // Ancho de la entidad
	public int height; // Alto de la entidad
	public Rectangle2D.Float hitbox; // Área rectangular de colisión de la entidad

	// Constructor para inicializar las propiedades básicas de la entidad
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	// Método para dibujar el área de colisión (hitbox) de la entidad
	public void drawHitbox(Graphics g, int xLvlOffset) {
		g.setColor(Color.YELLOW);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	// Método para inicializar el hitbox con las coordenadas y dimensiones dadas
	public void initHitbox(float x, float y, float f, float g) {
		hitbox = new Rectangle2D.Float(x, y, f, g);
	}

	// Método para obtener el hitbox de la entidad
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

}
