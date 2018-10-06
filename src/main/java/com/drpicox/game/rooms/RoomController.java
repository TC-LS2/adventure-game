package com.drpicox.game.rooms;

import com.drpicox.game.items.Item;
import com.drpicox.game.utils.TimerTaskRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoomController {

    @Autowired private RoomParser roomParser;
    @Autowired private RoomRepository roomRepository;
    @Autowired private TimerTaskRunner timerTaskRunner;

    public Room getDestinationRoom(Room room, Direction direction) {
        var target = room.getCoordinates().move(direction);
        return roomRepository.findById(target).orElse(null);
    }

    public Room getInitialRoom() {
        var origin = new RoomCoordinates(0, 0);
        var room = roomRepository.findById(origin).orElse(null);
        return room;
    }

    public void killMonster(Room room) {
        room.killMonster();
        roomRepository.save(room);
    }

    public void open(Room room, Direction direction) {
        room.open(direction);
        roomRepository.save(room);
    }

    public void receiveItem(Room room, Item item) {
        room.receiveItem(item);
        roomRepository.save(room);
    }

    public void removeItem(Room room, Item item) {
        timerTaskRunner.run(null);
        room.removeItem(item);
        roomRepository.save(room);
    }

    public void read(String rooms) {
        roomParser.read(rooms);
    }
}
