package model.game.entity;

public abstract class Entity {
    private double positionX;
    private double positionY;

    public Entity (double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }

    public void setPosition (double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }

    public double getPositionX () {
        return this.positionX;
    }

    public double getPositionY () {
        return this.positionY;
    }

    public void move (double x, double y) {
        this.positionX += x;
        this.positionY += y;
    }
}
