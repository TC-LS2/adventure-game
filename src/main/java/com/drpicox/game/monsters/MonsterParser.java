package com.drpicox.game.monsters;

import com.drpicox.game.items.Item;
import com.drpicox.game.items.ItemController;
import com.drpicox.game.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonsterParser {

    private MonsterRepository monsterRepository;
    private ItemController itemController;

    public MonsterParser(MonsterRepository monsterRepository, ItemController itemController) {
        this.monsterRepository = monsterRepository;
        this.itemController = itemController;
    }

    public void read(String monsters) {
        var parser = new Parser(monsters);

        var readed = readMonster(parser);
        while (readed) {
            readed = readMonster(parser);
        }
    }

    private boolean readMonster(Parser parser) {
        skipComments(parser);

        if (!parser.skipSpaces().hasNext()) return false;

        String name = parser.readUntil(':');
        int attack = parser.skipChar(':').readInt();
        int defense = parser.readInt();
        var item = parseItem(parser.skipChar(':'));

        var monster = new Monster(name, attack, defense, item);

        monsterRepository.save(monster);
        return true;
    }

    private Item parseItem(Parser parser) {
        String itemName = parser.readLine();
        return itemController.get(itemName);
    }

    private void skipComments(Parser parser) {
        while (parser.skipSpaces().hasNext() && parser.peek() == '#') {
            parser.readLine();
        }
    }
}
