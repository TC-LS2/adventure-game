package com.drpicox.game.tools;

import java.util.List;
import java.util.Map;

public class GameResultStringifier {

    public String toString(Map game) {
        var result = new StringBuilder();
        var player = (Map) game.get("player");
        var room = (Map) game.get("room");

        result.append(room(room));
        result.append(player(player));

        return result.toString();
    }

    protected String room(Map room) {
        var result = new StringBuilder();

        result.append(room.get("name")).append("\n");
        result.append(wrap((String) room.get("description")));
        result.append(roomExits(room));
        result.append(item("There is", (Map) room.get("item")));
        result.append(monster("There is", (Map) room.get("monster")));

        return result.toString();
    }

    protected String player(Map player) {
        var result = new StringBuilder();

        result.append(item("Player has", (Map) player.get("key")));
        result.append(item("Player has", (Map) player.get("shield")));
        result.append(item("Player has", (Map) player.get("weapon")));
        result.append("Player has ")
                .append(player.get("lifePoints"))
                .append(" life points.");

        return result.toString();
    }

    protected String roomExits(Map room) {
        var result = new StringBuilder();

        var exits = (List<Map>) room.get("exits");
        if (exits != null && !exits.isEmpty()) {
            result.append("Exits: ")
                    .append(
                            String.join(", ", exits.stream().map(exit -> exit(exit)).toArray(String[]::new))
                    ).append(".\n");
        }

        return result.toString();
    }

    protected String exit(Map exit) {
        var name = exit.get("name");
        var open = exit.get("open");

        if (open != null && open.equals(true)) {
            return "" + name;
        }

        return "" + name + " (closed)";
    }

    protected String item(String text, Map item) {
        if (item == null) return "";

        var name = item.get("name");
        var type = item.get("type");

        return text + " the " + name + " " + type + ".\n";
    }

    protected String monster(String text, Map monster) {
        if (monster == null) return "";

        return text + " the " + monster.get("name") + " monster.\n";
    }

    private String wrap(String text) {
        var result = new StringBuilder();
        var offset = 0;

        while (offset < text.length()) {
            var endIndex = Math.min(offset + 50, text.length());
            var chuck = text.substring(offset, endIndex);

            result.append(chuck).append("\n");

            offset += endIndex;
        }

        return result.toString();
    }
}
