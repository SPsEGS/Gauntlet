package com.teamf5.gauntlet.Model.Game.Entity.Buff;

public class Buff {
    private int buff = 0;
    /* TODO : private Timer timerBuff; */

    public Buff(int buff) {
        this.buff = buff;
        /* TODO : init timer */
    }

    public int getBuff() {
        /* TODO : timer ? */
        return this.buff;
    }

    public void setBuff(int buff) {
        this.buff = buff;
        /* TODO : init timer */
    }

    public void addBuff(int buff) {
        this.buff += buff;
        /* TODO : reset timer */
    }
}