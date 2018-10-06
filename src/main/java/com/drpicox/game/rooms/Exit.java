package com.drpicox.game.rooms;


import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Embeddable
public class Exit implements Serializable {

    private Direction direction;
    private int code;
    private boolean open;
    private boolean initialOpen;

    public Exit(Direction direction, int code, boolean open) {
        this.direction = direction;
        this.code = code;
        this.open = open;
        this.initialOpen = open;
    }

    @JsonValue
    public Map getJsonObject() {
        Map result = new LinkedHashMap();
        result.put("name", direction.name().toLowerCase());
        result.put("open", isOpen());
        return result;
    }

    Exit() {}

    public boolean isDirection(Direction direction) {
        return this.direction.equals(direction);
    }

    public int getCode() {
        return code;
    }

    public void open() {
        open = true;
    }

    public boolean isOpen() {
        return open || code == 0;
    }

    public Direction getDirection() {
        return direction;
    }

    public void refresh() {
        this.open = this.initialOpen;
    }
}
