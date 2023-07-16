// Paquete Modelo
package Modelo;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {
    protected int x;
    protected int y;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    public abstract void update();

    public abstract void render(Graphics g);

    protected abstract int getWidth();

    protected abstract int getHeight();
}
