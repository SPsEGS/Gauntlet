package com.teamf5.gauntlet.Model.Game.Tile.WalkableTile.Potion;

import com.teamf5.gauntlet.Model.Game.Entity.Player.Player;
import com.teamf5.gauntlet.Model.Game.Tile.WalkableTile.WalkableTile;

public abstract class Potion extends WalkableTile {
    private final int BUFF;

    public Potion (int buff) {
        this.BUFF = buff;
    }

    public int getBUFF () {
        return this.BUFF;
    }

    public abstract void walkEffect (Player p);
}
