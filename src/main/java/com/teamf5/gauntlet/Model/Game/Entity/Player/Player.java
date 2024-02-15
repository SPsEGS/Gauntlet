package com.teamf5.gauntlet.Model.Game.Entity.Player;
import com.teamf5.gauntlet.Model.Game.Entity.Character;
import com.teamf5.gauntlet.Model.Game.Entity.Buff.PlayerBuffs;

public abstract class Player extends Character {

    private static final int DEFAULT_HP = 0; /* TODO */
    private int magicDamage;
    private int physicDamage;
    private final String NAME;
    private int nbBombs;
    private int nbKeys;
    private PlayerBuffs buffs;

    public Player(String name, double spawnX, double spawnY) {
        super(spawnX, spawnY);
        this.NAME = name;
        this.buffs = new PlayerBuffs();
        this.nbBombs = 0;
        this.nbKeys = 0;
        this.setHp(DEFAULT_HP);
    }

    @Override
    public int getDefense () {
        return super.getDefense() + this.buffs.getDefenseBuff();
    }

    @Override
    public int getSpeed () {
        return super.getSpeed() + this.buffs.getSpeedBuff();
    }

    public int getMagicDamage () {
        return this.magicDamage + this.buffs.getMagicDamageBuff();
    }

    public int getPhysicDamage () {
        return this.physicDamage + this.buffs.getPhysicDamageBuff();
    }

    public void setDamage(int magic, int physic) {
        this.magicDamage = magic;
        this.physicDamage = physic;
    }

    public void addBuffDefense (int buff) {
        this.buffs.addDefenseBuff(buff);
    }

    public void addBuffMagic (int buff) {
        this.buffs.addMagicDamageBuff(buff);
    }

    public void addBuffPhysic (int buff) {
        this.buffs.addPhysicDamageBuff(buff);
    }

    public void addBuffSpeed (int buff) {
        this.buffs.addSpeedBuff(buff);
    }

    @Override
    public void takeDamage (int dmg) {
        int realDmg = dmg - this.getDefense();
        if (realDmg < 0)
            realDmg = 0;
        this.decreaseHp(realDmg);
    }
}
