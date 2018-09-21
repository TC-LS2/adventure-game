package com.drpicox.game;

import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.items.ItemRepository;
import com.drpicox.game.monsters.MonsterRepository;
import com.drpicox.game.players.PlayerRepository;
import com.drpicox.game.rooms.RoomRepository;
import com.drpicox.game.tools.WorldBuilder;
import com.drpicox.game.world.TextWorldFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class TestHelper {

    public final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired private MockMvc mockMvc;
    @Autowired private ItemRepository itemRepository;
    @Autowired private MonsterRepository monsterRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private PlayerRepository playerRepository;

    public void cleanup() {
        playerRepository.deleteAll();
        roomRepository.deleteAll();
        monsterRepository.deleteAll();
        itemRepository.deleteAll();
    }

    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object fromJson(String json) {
        return fromJson(json, Object.class);
    }

    public Object eraseType(Object object) {
        return fromJson(toJson(object));
    }

    public ResultActions postCommands(String username, String ...commands) throws Exception {
        ResultActions result = null;
        for (var command : commands) {
            switch (command) {
                case "north":
                case "south":
                case "east":
                case "west":
                    command = "move?direction=" + command;
            }

            result = mockMvc.perform(post("/api/v1/players/" + username + "/commands/" + command));
        }
        return result;
    }

    public ResultActions runCommand(String username, String command, String ...arguments) throws Exception {
        var request = new CommandRequest(username, command, Arrays.asList(arguments));

        return mockMvc.perform(post("/api/v1/commands")
                .contentType(contentType)
                .content(toJson(request)));
    }

    public ResultActions putWorld(WorldBuilder builder) throws Exception {
        var text = builder.build();

        return putWorld(text.split("\n"));
    }

    public ResultActions putWorld(String ...lines) throws Exception {
        var world = new TextWorldFactory().create(lines);

        return mockMvc.perform(put("/api/v1/world")
                .contentType(contentType)
                .content(toJson(world)))
                .andExpect(status().isOk());
    }

}
