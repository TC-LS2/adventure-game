package com.drpicox.game.tools;

import com.drpicox.game.rooms.RoomCoordinates;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;

import static com.drpicox.game.tools.WorldBuilder.*;

public class WorldDimensions {

    private int minI;
    private int maxI;
    private int minJ;
    private int maxJ;
    private int width;
    private NavigableMap<Integer, Integer> columnWidths = new TreeMap<>();
    private NavigableMap<Integer, Integer> columnOffsets = new TreeMap<>();

    public WorldDimensions(Collection<RoomBuilder> rooms) {
        computeRanges(rooms);
        computeColumnWidths(rooms);
        computeColumnOffsetsAndWidth(rooms);
    }

    private void computeRanges(Collection<RoomBuilder> rooms) {
        minI = Integer.MAX_VALUE;
        maxI = Integer.MIN_VALUE;
        minJ = Integer.MAX_VALUE;
        maxJ = Integer.MIN_VALUE;

        for (var room: rooms) {
            var coordinates = room.getCoordinates();
            var i = coordinates.getI();
            var j = coordinates.getJ();

            minI = Math.min(minI, i);
            maxI = Math.max(maxI, i);
            minJ = Math.min(minJ, j);
            maxJ = Math.max(maxJ, j);
        }
    }

    private void computeColumnWidths(Collection<RoomBuilder> rooms) {
        for (var room: rooms) {
            var j = room.getCoordinates().getJ();
            var width = columnWidths.getOrDefault(j, 0);
            var length = Math.min(MAX_COL_WIDTH, room.getName().length());
            width = Math.max(width, length);
            columnWidths.put(j, width);
        }
    }

    private void computeColumnOffsetsAndWidth(Collection<RoomBuilder> rooms) {
        var offset = 0;
        for (var j: columnWidths.keySet()) {
            columnOffsets.put(j, offset);

            var width = columnWidths.get(j);
            offset += EXIT_WIDTH + width + 2;
        }
        width = offset;
    }

    public int getHeight() {
        return getY(minI) + 5;
    }

    public int getWidth() {
        return width;
    }

    public int getX(RoomCoordinates coordinates) {
        var j = coordinates.getJ();
        var offset = columnOffsets.get(j);
        return offset;
    }

    public int getY(RoomCoordinates coordinates) {
        var i = coordinates.getI();
        return getY(i);
    }

    public int getWidth(RoomCoordinates coordinates) {
        var j = coordinates.getJ();
        return columnWidths.get(j);
    }

    private int getY(int i) {
        var di = maxI - i;

        return di * (5 + EXIT_HEIGHT);
    }
}
