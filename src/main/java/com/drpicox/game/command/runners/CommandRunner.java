package com.drpicox.game.command.runners;

import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.command.CommandResponse;
import com.drpicox.game.players.Player;

public interface CommandRunner {
    CommandResponse run(Player player, CommandRequest request);
}
