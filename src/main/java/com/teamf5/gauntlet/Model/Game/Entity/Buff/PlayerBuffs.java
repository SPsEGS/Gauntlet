package com.teamf5.gauntlet.Model.Game.Entity.Buff;

public class PlayerBuffs {
    private Buff defense;
    private Buff magic;
    private Buff physic;
    private Buff speed;

    public PlayerBuffs() {
        this.defense = new Buff(0);
        this.magic = new Buff(0);
        this.physic = new Buff(0);
        this.speed = new Buff(0);
    }

    public int getDefenseBuff() {
        return this.defense.getBuff();
    }

    public int getPhysicDamageBuff() {
        return this.physic.getBuff();
    }

    public int getMagicDamageBuff() {
        return this.magic.getBuff();
    }

    public int getSpeedBuff() {
        return this.speed.getBuff();
    }

    public void addDefenseBuff(int buff) {
        this.defense.addBuff(buff);
    }

    public void addPhysicDamageBuff(int buff) {
        this.physic.addBuff(buff);
    }

    public void addMagicDamageBuff(int buff) {
        this.magic.addBuff(buff);
    }

    public void addSpeedBuff(int buff) {
        this.speed.addBuff(buff);
    }
}