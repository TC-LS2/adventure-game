package com.drpicox.game.tools;

public class ItemBuilder {
    private String name;
    private String type;
    private int value;

    public ItemBuilder(String name, String type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String build() {
        return name + ": " + type + " " + value + "\n";
    }
}
