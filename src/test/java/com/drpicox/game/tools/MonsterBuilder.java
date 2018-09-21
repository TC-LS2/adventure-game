package com.drpicox.game.tools;

public class MonsterBuilder {
    private String name;
    private int attack;
    private int defense;
    private String item;

    public MonsterBuilder(String name, int attack, int defense, String item) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.item = item;
    }

    public String build() {
        return name + ":" + attack + " " + defense + ":" + item + "\n";
    }
}
