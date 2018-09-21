package com.drpicox.game.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandRequest {

    private String username;
    private String command;
    private List<String> arguments;

    @JsonCreator
    public CommandRequest(
            @JsonProperty("username") String username,
            @JsonProperty("command") String command,
            @JsonProperty("arguments") List<String> arguments) {

        this.username = username;
        this.command = command;
        this.arguments = arguments;
    }

    public String getUsername() {
        return username;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public String getArgument(int number) {
        return arguments.get(number);
    }

    @JsonValue
    public Map getJsonObject() {
        Map result = new LinkedHashMap();
        result.put("username", username);
        result.put("command", command);
        result.put("arguments", arguments);
        return result;
    }
}
