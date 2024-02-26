package model.game.entity.monster;

import model.game.entity.Character;

public abstract class Monster extends Character {

    private static final int DEFAULT_HP = 0; /* TODO */
    private int damage;

    public Monster (double spawnX, double spawnY) {
        super(spawnX, spawnY);
        this.setHp(DEFAULT_HP);
    }

    public void setDamage (int dmg) {
        this.damage = dmg;
    }

    public int getDamage () {
        return this.damage;
    }

    @Override
    public void attack(Character enemy) {
        enemy.takeDamage(this.getDamage());
        /* TODO : range */
    }
}
