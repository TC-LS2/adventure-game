package com.drpicox.game.world;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class World {

    private String rooms;
    private String items;
    private String monsters;

    @JsonCreator
    public World(
            @JsonProperty("rooms") String rooms,
            @JsonProperty("items") String items,
            @JsonProperty("monsters") String monsters) {

        this.rooms = rooms;
        this.items = items;
        this.monsters = monsters;
    }

    public String getRooms() {
        return rooms;
    }
    public String getItems() {
        return items;
    }

    public String getMonsters() {
        return monsters;
    }

}
