package model.game.entity;

public abstract class Character extends Entity {

    private int hp;
    private int maxHp;
    private int defense;
    private int speed;
    private int rangeAttack;

    public Character (double spawnX, double spawnY) {
        super(spawnX, spawnY);
    }

    public void setHp (int value) {
        this.hp = value;
    }

    public void setStats(int def, int spd, int range) {
        this.defense = def;
        this.speed = spd;
        this.rangeAttack = range;
    }

    public void increaseHp (int hp) {
        this.hp += hp;
        if (this.hp > this.maxHp)
            this.hp = this.maxHp;
    }

    public void decreaseHp (int hp) {
        this.hp -= hp;
    }

    public void increaseMaxHp (int hp) {
        this.maxHp += hp;
    }

    public int getHp () {
        return this.hp;
    }

    public int getMaxHp () {
        return this.maxHp;
    }

    public int getDefense () {
        return this.defense;
    }

    public int getSpeed () {
        return this.speed;
    }

    public int getRangeAttack () {
        return this.rangeAttack;
    }

    public abstract void attack (Character enemy);

    public void takeDamage (int damage) {
        int realDamage = damage - this.defense;
        if (realDamage < 0)
            realDamage = 0;
        this.hp -= realDamage;
    }

    public boolean isDead () {
        return this.hp < 0;
    }
}
