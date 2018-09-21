package com.drpicox.game.command;

import com.drpicox.game.command.runners.*;
import com.drpicox.game.players.Player;
import com.drpicox.game.players.PlayerController;
import com.drpicox.game.rooms.Room;
import com.drpicox.game.rooms.RoomController;
import com.drpicox.game.world.DefaultWorldFactory;
import com.drpicox.game.world.WorldRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/commands")
public class CommandRestController {

    @Autowired private PlayerController playerController;
    @Autowired private RoomController roomController;
    @Autowired private WorldRestController worldRestController;

    private Map<String, CommandRunner> commandRunners = new HashMap<>();

    @Autowired
    public void setup(
            AttackCommandRunner attackCommandRunner,
            GetCommandRunner getCommandRunner,
            LookCommandRunner lookCommandRunner,
            MoveCommandRunner moveCommandRunner
    ) {
        commandRunners.put("attack", attackCommandRunner);
        commandRunners.put("get", getCommandRunner);
        commandRunners.put("look", lookCommandRunner);
        commandRunners.put("move", moveCommandRunner);
    }

    @PostMapping()
    public CommandResponse run(@RequestBody CommandRequest request) {
        var player = getPlayer(request);
        var runner = getRunner(request);

        return runner.run(player, request);
    }

    private CommandRunner getRunner(CommandRequest request) {
        var command = request.getCommand();
        var runner = commandRunners.get(command);

        if (runner == null) {
            throw new IllegalCommandException("unknown-command", "Command '" + command + "' unkown");
        }

        return runner;
    }

    private Room getInitialRoom() {
        var initialRoom = roomController.getInitialRoom();
        if (initialRoom == null) {
            var world = new DefaultWorldFactory().create();
            worldRestController.replace(world);
            initialRoom = roomController.getInitialRoom();
        }
        return initialRoom;
    }

    private Player getPlayer(CommandRequest request) {
        var username = request.getUsername();
        Player player = playerController.getPlayer(username);
        if (player == null) {
            var initialRoom = getInitialRoom();

            player = playerController.createPlayer(username, initialRoom);
        }
        return player;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IllegalCommandException.class })
    public Map handleException(IllegalCommandException e) {
        return new HashMap() {{
            put("code", e.getCode());
            put("message", e.getMessage());
        }};
    }

}
