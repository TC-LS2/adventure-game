package com.drpicox.game.monsters;

import com.drpicox.game.items.Item;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Monster {

    @Id private String name;

    private int attack;
    private int defense;
    @ManyToOne private Item treasure;

    public Monster(String name, int attack, int defense, Item treasure) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.treasure = treasure;
    }

    public Item dropTreasure() {
        var treasure = this.treasure;
        this.treasure = null;
        return treasure;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    @JsonValue
    public Map getJsonObject() {
        Map result = new LinkedHashMap();
        result.put("name", name);
        return result;
    }

    Monster() {}
}
