package com.drpicox.game.players;

import com.drpicox.game.items.Item;
import com.drpicox.game.rooms.Room;
import com.drpicox.game.rooms.RoomController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;

@Controller
public class PlayerController {

    private PlayerRepository playerRepository;
    private RoomController roomController;

    public PlayerController(PlayerRepository playerRepository, RoomController roomController) {
        this.playerRepository = playerRepository;
        this.roomController = roomController;
    }

    public Player createPlayer(String username, Room initialRoom) {
        var player = new Player(username, initialRoom, Player.MAX_LIFE_POINTS, null);
        playerRepository.save(player);

        return player;
    }

    public Player getPlayer(String username) {
        return playerRepository.findById(username).orElse(null);
    }

    public void killPlayer(Player player, Room initialRoom) {
        player.die(initialRoom);
        playerRepository.save(player);
    }

    public void leaveItem(Player player, Item item) {
        player.leaveItem(item);
        playerRepository.save(player);
    }

    public void moveTo(Player player, Room nextRoom) {
        player.moveTo(nextRoom);
        playerRepository.save(player);
    }

    public void takeDamage(Player player, int damagePoints) {
        player.takeDamage(damagePoints);
        playerRepository.save(player);
    }

    public Item takeItem(Player player, Item item) {
        var droppedItem = player.takeItem(item);
        playerRepository.save(player);
        return droppedItem;
    }
}
