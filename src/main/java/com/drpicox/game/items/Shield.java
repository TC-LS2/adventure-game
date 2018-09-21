package com.drpicox.game.items;

import javax.persistence.Entity;

@Entity
public class Shield extends Item {

    private int defense;

    public Shield(String name, int defense) {
        super(name);
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    Shield() {}
}
