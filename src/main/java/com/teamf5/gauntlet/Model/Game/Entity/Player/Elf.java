package com.teamf5.gauntlet.Model.Game.Entity.Player;

import com.teamf5.gauntlet.Model.Game.Entity.Character;

public class Elf extends Player {

    private static final int DEFAULT_DEF = 0; /* TODO */
    private static final int DEFAULT_PHYS = 0;
    private static final int DEFAULT_MAG = 0; /* TODO */
    private static final int DEFAULT_SPD = 0; /* TODO */
    private static final int DEFAULT_RANGE = 0; /* TODO */

    public Elf (String name, double spawnX, double spawnY) {
        super(name, spawnX, spawnY);
        this.setStats(DEFAULT_DEF, DEFAULT_SPD, DEFAULT_RANGE);
        this.setDamage(DEFAULT_MAG, DEFAULT_PHYS);
    }

    @Override
    public void attack(Character enemy) {
        enemy.takeDamage(this.getMagicDamage());
        /* TODO : range */
    }
}