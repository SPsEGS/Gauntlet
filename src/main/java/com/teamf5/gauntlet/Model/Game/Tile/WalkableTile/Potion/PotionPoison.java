package com.teamf5.gauntlet.Model.Game.Tile.WalkableTile.Potion;

import com.teamf5.gauntlet.Model.Game.Entity.Player.Player;

public class PotionPoison extends Potion {
    private static final int DEFAULT_BUFF = 0; /* TODO */

    public PotionPoison () {
        super(DEFAULT_BUFF);
    }

    public void walkEffect (Player p) {
        p.decreaseHp(this.getBUFF());
    }
}
