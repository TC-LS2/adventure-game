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
public class AttackCommandRunner implements CommandRunner {

    @Autowired RoomController roomController;
    @Autowired PlayerController playerController;

    @Autowired
    LookCommandRunner lookCommandRunner;

    @Override
    public CommandResponse run(Player player, CommandRequest request) {
        var room = player.getRoom();
        var monster = room.getMonster();

        if (monster == null)
            throw new IllegalCommandException("no-monster", "There is no monster in this room.");

        var code = "attack";
        var playerAttack = player.getAttack();
        var playerDefense = player.getDefense();

        var monsterAttack = monster.getAttack();
        var monsterDefense = monster.getDefense();

        var monsterPoints = playerAttack - monsterDefense;
        var playerPoints = monsterAttack - playerDefense;

        if (monsterPoints >= 0) {
            roomController.killMonster(room);
            code += ",monster-killed";
        }

        if (playerPoints > player.getLifePoints()) {
            playerController.killPlayer(player, roomController.getInitialRoom());
            throw new IllegalCommandException("player-killed", "Monster killed you.");
        } if (playerPoints > 0) {
            playerController.takeDamage(player, playerPoints);
            code += ",player-damaged";
        }

        return lookCommandRunner.run(player, request).withCode(code);
    }

}
