package com.drpicox.game.command.runners;

import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.command.CommandResponse;
import com.drpicox.game.command.IllegalCommandException;
import com.drpicox.game.players.Player;
import com.drpicox.game.players.PlayerController;
import com.drpicox.game.rooms.RoomController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetCommandRunner implements CommandRunner {

    @Autowired RoomController roomController;
    @Autowired PlayerController playerController;
    @Autowired LookCommandRunner lookCommandRunner;

    @Override
    public CommandResponse run(Player player, CommandRequest request) {
        var room = player.getRoom();
        var item = room.getItem();
        if (item == null) throw new IllegalCommandException("no-item", "There is no item in this room.");

        var droppedItem = playerController.takeItem(player, item);
        roomController.removeItem(room, item);
        roomController.receiveItem(room, droppedItem);

        return lookCommandRunner.run(player, request);
    }

}
