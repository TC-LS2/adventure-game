package com.drpicox.game.tools;

import com.drpicox.game.rooms.Direction;
import com.drpicox.game.rooms.RoomCoordinates;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.drpicox.game.rooms.Direction.*;

public class WorldBuilder {

    public static final int EXIT_HEIGHT = 2;
    public static final int EXIT_WIDTH = 6;
    public static final int MAX_COL_WIDTH = 18;

    private RoomCoordinates currentCoordinates;
    private Map<RoomCoordinates, RoomBuilder> rooms = new LinkedHashMap<>();
    private Map<String, ItemBuilder> items = new LinkedHashMap<>();
    private Map<String, MonsterBuilder> monsters = new LinkedHashMap<>();

    public WorldBuilder() {
        currentCoordinates = new RoomCoordinates(0, 0);
        rooms.put(currentCoordinates, new RoomBuilder(currentCoordinates));
    }

    public WorldBuilder name(String roomName) {
        getCurrentRoom().name(roomName);
        return this;
    }

    public WorldBuilder description(String roomDescription) {
        getCurrentRoom().description(roomDescription);
        return this;
    }

    public WorldBuilder goTo(int i, int j) {
        return goTo(new RoomCoordinates(i, j));
    }

    protected WorldBuilder goTo(RoomCoordinates coordinates) {
        currentCoordinates = coordinates;
        var room = getCurrentRoom();
        if (room == null) {
            room = new RoomBuilder(coordinates);
            rooms.put(coordinates, room);

            connectRoom(coordinates, NORTH);
            connectRoom(coordinates, SOUTH);
            connectRoom(coordinates, EAST);
            connectRoom(coordinates, WEST);
        }

        return this;
    }

    private void connectRoom(RoomCoordinates coordinates, Direction direction) {
        var a = rooms.get(coordinates);
        var b = rooms.get(coordinates.move(direction));

        if (a == null || b == null) return;

        a.openIfAbsent(direction);
        b.openIfAbsent(direction.reverse());
    }

    public WorldBuilder north() {
        return this.goTo(currentCoordinates.move(NORTH));
    }

    public WorldBuilder south() {
        return this.goTo(currentCoordinates.move(SOUTH));
    }

    public WorldBuilder east() {
        return this.goTo(currentCoordinates.move(EAST));
    }

    public WorldBuilder west() {
        return this.goTo(currentCoordinates.move(WEST));
    }

    public WorldBuilder exit(Direction direction, int keyValue) {
        getCurrentRoom().exit(direction, keyValue);
        return this;
    }

    public WorldBuilder item(String name, String type, int value) {
        var item = new ItemBuilder(name, type, value);
        items.put(name, item);
        return item(name);
    }

    public WorldBuilder item(String name) {
        getCurrentRoom().itemOrMonster(name);
        return this;
    }

    public WorldBuilder monster(String name, int attack, int defense, String item) {
        var monster = new MonsterBuilder(name, attack, defense, item);
        monsters.put(name, monster);
        return monster(name);
    }

    public WorldBuilder monster(String name, int attack, int defense) {
        return monster(name, attack, defense, "nothing");
    }

    public WorldBuilder monster(String name) {
        getCurrentRoom().itemOrMonster(name);
        return this;
    }


    public String build() {
        var result = new StringBuilder();

        result.append("== rooms:\n");
        for (var room: rooms.values()) {
            result.append(room.build());
        }

        if (items.size() > 0) {
            result.append("== items:\n");
            for (var item: items.values()) {
                result.append(item.build());
            }
        }

        if (monsters.size() > 0) {
            result.append("== monsters:\n");
            for (var monster: monsters.values()) {
                result.append(monster.build());
            }
        }

        return result.toString();
    }

    private RoomBuilder getCurrentRoom() {
        return rooms.get(currentCoordinates);
    }

    public String map() {
        var dimensions = new WorldDimensions(rooms.values());
        var drawableMap = new DrawableMap(dimensions.getWidth(), dimensions.getHeight());

        drawRooms(dimensions, drawableMap);

        return drawableMap.toString();
    }

    private void drawRooms(WorldDimensions dimensions, DrawableMap drawableMap) {
        for (var coordinates: rooms.keySet()) {
            var x = dimensions.getX(coordinates);
            var y = dimensions.getY(coordinates);
            drawableMap.draw(x, y, 'X');
            rooms.get(coordinates).draw(drawableMap.offset(x, y), dimensions.getWidth(coordinates));
        }
    }
}
