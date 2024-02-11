package com.teamf5.gauntlet.Model.Game.Entity.Monster;

public class Lobber extends Monster {

    private static final int DEFAULT_DEF = 0; /* TODO */
    private static final int DEFAULT_DMG = 0; /* TODO */
    private static final int DEFAULT_SPD = 0; /* TODO */
    private static final int DEFAULT_RANGE = 0; /* TODO */

    public Lobber (double spawnX, double spawnY) {
        super(spawnX, spawnY);
        this.setStats(DEFAULT_DEF, DEFAULT_SPD, DEFAULT_RANGE);
        this.setDamage(DEFAULT_DMG);
    }
}
