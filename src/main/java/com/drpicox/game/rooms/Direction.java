package com.drpicox.game.rooms;

public enum Direction {

    NORTH(+1, 0),
    SOUTH(-1, 0),
    EAST(0, +1),
    WEST(0, -1);

    private int di;
    private int dj;

    Direction(int di, int dj) {
        this.di = di;
        this.dj = dj;
    }

    public int getDi() {
        return di;
    }

    public int getDj() {
        return dj;
    }

    public Direction reverse() {
        for (var candidate: Direction.values()) {
            if (candidate.di == -di && candidate.dj == -dj) {
                return candidate;
            }
        }
        return null;
    }
}
