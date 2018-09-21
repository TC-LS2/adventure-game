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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultCommandRunner implements CommandRunner {

    private Map<String, CommandRunner> commandRunners = new HashMap<>();

    public DefaultCommandRunner(
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

    @Override
    public CommandResponse run(Player player, CommandRequest request) {
        var runner = getRunner(request);

        var response = runner.run(player, request);
        return response;
    }

    private CommandRunner getRunner(CommandRequest request) {
        var command = request.getCommand();
        var runner = commandRunners.get(command);

        if (runner == null) {
            throw new IllegalCommandException("unknown-command", "Command '" + command + "' unkown");
        }

        return runner;
    }

}
