package com.drpicox.game.items;

import javax.persistence.Entity;

@Entity
public class Key extends Item {

    private int code;

    public Key(String name, int code) {
        super(name);
        this.code = code;
    }

    public boolean opens(int code) {
        return this.code == code;
    }

    Key() {}
}
