package com.drpicox.game.players;

import com.drpicox.game.items.*;
import com.drpicox.game.rooms.Room;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Player {

    static final int MAX_LIFE_POINTS = 16;

    @Id private String username;

    @ManyToOne private Room room;
    private int lifePoints;

    @ManyToOne private Key key;
    @ManyToOne private Shield shield;
    @ManyToOne private Weapon weapon;

    @JsonCreator
    public Player(
            @JsonProperty("username") String username,
            @JsonProperty("room") Room room,
            @JsonProperty("lifePoints") int lifePoints,
            @JsonProperty("key") Key key
    ) {

        this.username = username;
        this.room = room;
        this.lifePoints = lifePoints;
        this.key = key;
    }

    public void die(Room initialRoom) {
        this.lifePoints = MAX_LIFE_POINTS;
        this.room = initialRoom;
        this.key = null;
        this.weapon = null;
        this.shield = null;
    }

    public int getAttack() {
        if (weapon == null) return 0;
        return weapon.getAttack();
    }

    public int getDefense() {
        if (shield == null) return 0;
        return shield.getDefense();
    }

    public Key getKey() {
        return key;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public Room getRoom() {
        return room;
    }

    public void leaveItem(Item item) {
        if (key == item) key = null;
    }

    public void moveTo(Room nextRoom) {
        room = nextRoom;
    }

    public void takeDamage(int damagePoints) {
        this.lifePoints -= damagePoints;
    }

    public void takeLifePoints(int lifePoints) {
        this.lifePoints = Math.min(this.lifePoints + lifePoints, MAX_LIFE_POINTS);
    }

    public Item takeItem(Item item) {
        Item dropped = null;
        if (item instanceof Key) {
            dropped = key;
            key = (Key) item;
        } else if (item instanceof Shield) {
            dropped = shield;
            shield = (Shield) item;
        } else if (item instanceof Weapon) {
            dropped = weapon;
            weapon = (Weapon) item;
        } else if (item instanceof Food) {
            takeLifePoints(((Food) item).getLifePoints());
        }
        return dropped;
    }

    @JsonValue
    public Map getJsonObject() {
        Map result = new LinkedHashMap();
        result.put("username", username);
        result.put("lifePoints", lifePoints);
        result.put("key", key);
        result.put("shield", shield);
        result.put("weapon", weapon);
        return result;
    }

    Player() {}
}
