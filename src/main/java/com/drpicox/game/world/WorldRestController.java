package com.drpicox.game.world;

import com.drpicox.game.items.ItemController;
import com.drpicox.game.monsters.MonsterController;
import com.drpicox.game.rooms.RoomController;
import com.drpicox.game.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/world")
public class WorldRestController {

    @Autowired private ItemController itemController;
    @Autowired private RoomController roomController;
    @Autowired private MonsterController monsterController;

    @PutMapping
    public SuccessResponse replace(@RequestBody World world) {
        itemController.read(world.getItems());
        monsterController.read(world.getMonsters());
        roomController.read(world.getRooms());
        return new SuccessResponse("success");
    }
}
