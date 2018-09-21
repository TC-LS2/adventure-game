package com.drpicox.game.command;

import com.drpicox.game.players.Player;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandResponse {

    private Player player;
    private String code;

    public CommandResponse(Player player) {
        this(player, null);
    }
    public CommandResponse(Player player, String code) {
        this.player = player;
        this.code = code;
    }

    public CommandResponse withCode(String code) {
        return new CommandResponse(player, code);
    }

    @JsonValue
    public Map getJsonObject() {
        Map result = new LinkedHashMap();
        result.put("player", player);
        result.put("room", player.getRoom());
        if (code != null) result.put("code", code);
        return result;
    }
}
