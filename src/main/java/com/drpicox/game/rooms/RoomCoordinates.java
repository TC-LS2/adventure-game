package com.drpicox.game.rooms;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoomCoordinates implements Serializable {

    private int i;
    private int j;

    public RoomCoordinates(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public RoomCoordinates move(Direction direction) {
        return new RoomCoordinates(i + direction.getDi(), j + direction.getDj());
    }

    RoomCoordinates(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCoordinates that = (RoomCoordinates) o;
        return i == that.i &&
                j == that.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
