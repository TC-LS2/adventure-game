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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/commands")
public class CommandRestController {

    private PlayerController playerController;
    private RoomController roomController;
    private WorldRestController worldRestController;
    private DefaultCommandRunner defaultCommandRunner;

    public CommandRestController(PlayerController playerController, RoomController roomController, WorldRestController worldRestController, DefaultCommandRunner defaultCommandRunner) {
        this.playerController = playerController;
        this.roomController = roomController;
        this.worldRestController = worldRestController;
        this.defaultCommandRunner = defaultCommandRunner;
    }

    @PostMapping()
    public CommandResponse run(@RequestBody CommandRequest request) {
        var player = getPlayer(request);

        return defaultCommandRunner.run(player, request);
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
