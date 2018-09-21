package com.drpicox.game.command.runners;

import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.command.CommandResponse;
import com.drpicox.game.players.Player;
import org.springframework.stereotype.Component;

@Component
public class LookCommandRunner implements CommandRunner {

    @Override
    public CommandResponse run(Player player, CommandRequest request) {
        return new CommandResponse(player);
    }

}
