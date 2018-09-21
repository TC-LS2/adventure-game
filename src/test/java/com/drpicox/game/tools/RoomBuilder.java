package com.drpicox.game.tools;

import com.drpicox.game.rooms.Direction;
import com.drpicox.game.rooms.RoomCoordinates;

import java.util.HashMap;
import java.util.Map;

import static com.drpicox.game.rooms.Direction.*;
import static com.drpicox.game.tools.WorldBuilder.EXIT_HEIGHT;
import static com.drpicox.game.tools.WorldBuilder.EXIT_WIDTH;

public class RoomBuilder {
    private RoomCoordinates coordinates;
    private String name = "no name";
    private String description = "no description";
    private String itemOrMonster = "nothing";
    private Map<Direction,Integer> exits = new HashMap<>();

    public RoomBuilder(RoomCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void name(String name) {
        this.name = name;
    }

    public void description(String description) {
        this.description = description;
    }

    public void openIfAbsent(Direction direction) {
        exits.putIfAbsent(direction, 0);
    }

    public void exit(Direction direction, int keyValue) {
        exits.put(direction, keyValue);
    }

    public void itemOrMonster(String name) {
        this.itemOrMonster = name;
    }

    public String build() {
        var result = new StringBuilder();

        result.append(coordinates.getI());
        result.append(' ');
        result.append(coordinates.getJ());
        result.append(':');
        result.append(name);
        result.append(':');
        result.append(buildExits());
        result.append(':');
        result.append(itemOrMonster);
        result.append('\n');
        result.append(description);
        result.append('\n');
        result.append("::::\n");

        return result.toString();
    }

    private String buildExits() {
        var result = new StringBuilder();

        result.append(getExit(NORTH));
        result.append(' ');
        result.append(getExit(SOUTH));
        result.append(' ');
        result.append(getExit(EAST));
        result.append(' ');
        result.append(getExit(WEST));

        return result.toString();
    }

    public void draw(DrawableMap drawableMap, int width) {
        drawableMap.drawBox(0, 0, width + 2, 5)
                .centerText(1, 1, name.toUpperCase(), width)
                .leftText(1, 2, description.toLowerCase(), width);

        if (!itemOrMonster.equals("nothing")) {
            drawableMap.rightText(1, 3, "(" + itemOrMonster + ")", width);
        }

        for (var direction: exits.keySet()) {
            drawExit(direction, drawableMap, width);
        }
    }

    public void drawExit(Direction direction, DrawableMap drawableMap, int width) {
        int x = 0, y = 0, l = 0;
        switch (direction) {
            case NORTH: x = width / 2; y = -1; l = EXIT_HEIGHT; break;
            case SOUTH: x = width / 2; y = 5; l = EXIT_HEIGHT; break;
            case EAST: x = width + 2; y = 2; l = EXIT_WIDTH; break;
            case WEST: x = -1; y = 2; l = EXIT_WIDTH; break;
        }
        drawableMap.drawLine(x, y, l, direction);

        var exit = exits.get(direction);
        char ch = 'o';
        if (exit < 0) {
            ch = 'x';
        } if (exit > 0) {
            ch = '▒';
        }
        drawableMap.draw(x - direction.getDj(), y + direction.getDi(), ch);
    }

    public RoomCoordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    private int getExit(Direction direction) {
        return exits.getOrDefault(direction, -1);
    }


}
