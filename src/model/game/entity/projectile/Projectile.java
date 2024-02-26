package model.game.entity.projectile;

import model.game.entity.Entity;

public class Projectile extends Entity {

    private int damage;
    private int range;
    private double distanceTravelled;

    public Projectile (double spawnX, double spawnY, int damage, int range) {
        super(spawnX, spawnY);
        this.damage = damage;
        this.range = range;
    }
}
