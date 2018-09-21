package com.drpicox.game.world;

import java.util.HashMap;

public class TextWorldFactory {

    public World create(String...lines) {
        var world = new HashMap<String, String>() {{
            put("rooms", "");
            put("items", "");
            put("monsters", "");
        }};

        var kind = "rooms";
        for (var line: lines) {
            if (line.matches("[= #~-]*room.*:.*")) kind = "rooms";
            else if (line.matches("[= #~-]*item.*:.*")) kind = "items";
            else if (line.matches("[= #~-]*monster.*:.*")) kind = "monsters";
            else world.put(kind, world.get(kind) + "\n" + line);
        }

        return new World(world.get("rooms"), world.get("items"), world.get("monsters"));
    }

}
