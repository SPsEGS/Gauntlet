package com.teamf5.gauntlet.Model.Game.Entity.Projectile;

import com.teamf5.gauntlet.Model.Game.Entity.Entity;

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
