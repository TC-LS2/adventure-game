package com.drpicox.game.items;

import javax.persistence.Entity;

@Entity
public class Weapon extends Item {

    private int attack;

    public Weapon(String name, int attack) {
        super(name);
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    Weapon() {}

}
