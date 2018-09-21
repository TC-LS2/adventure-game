package com.drpicox.game.rooms;

import com.drpicox.game.items.Item;
import com.drpicox.game.items.ItemController;
import com.drpicox.game.monsters.Monster;
import com.drpicox.game.monsters.MonsterController;
import com.drpicox.game.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.drpicox.game.rooms.Direction.*;

@Component
public class RoomParser {

    @Autowired private RoomRepository roomRepository;
    @Autowired private ItemController itemController;
    @Autowired private MonsterController monsterController;

    public void read(String rooms) {
        var parser = new Parser(rooms);

        readRoom(parser);
        while (parser.skipSpaces().hasNext()) {
            readRoom(parser);
        }
    }

    private void readRoom(Parser parser) {
        var coordinates = readCoordinates(parser);
        var name = parser.skipChar(':').readUntil(':');
        var exits = readExits(parser.skipChar(':'));
        var itemOrMonster = readItemOrMonster(parser.skipChar(':'));
        var item = itemOrMonster instanceof Item ? (Item) itemOrMonster : null;
        var monster = itemOrMonster instanceof Monster ? (Monster) itemOrMonster : null;
        var description = readDescription(parser.skipChar('\n'));

        var room = new Room(coordinates, name, exits, item, monster, description);
        roomRepository.save(room);
    }

    private Object readItemOrMonster(Parser parser) {
        var name = parser.readUntil('\n');

        var monster = monsterController.get(name);
        if (monster != null) return monster;

        return itemController.get(name);
    }

    private RoomCoordinates readCoordinates(Parser parser) {
        var i = parser.readInt();
        var j = parser.readInt();

        return new RoomCoordinates(i, j);
    }

    private List<Exit> readExits(Parser parser) {
        var result = new ArrayList<Exit>();

        readExit(parser, NORTH, result);
        readExit(parser, SOUTH, result);
        readExit(parser, EAST, result);
        readExit(parser, WEST, result);

        return result;
    }

    private void readExit(Parser parser, Direction direction, ArrayList<Exit> result) {
        var code = parser.readInt();
        if (code == -1) return;

        result.add(new Exit(direction, code, code == 0));
    }

    private String readDescription(Parser parser) {
        var description = new StringBuilder();
        var line = parser.readLine();
        while (parser.hasNext() && !line.equals("::::")) {
            description.append('\n').append(line);
            line = parser.readLine();
        }

        return description.toString().substring(1);
    }
}