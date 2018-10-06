package com.drpicox.game.items;

import com.drpicox.game.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemParser {

    @Autowired private ItemRepository itemRepository;

    public void read(String items) {
        var parser = new Parser(items);

        var readed = readItem(parser);
        while (readed) {
            readed = readItem(parser);
        }
    }

    private boolean readItem(Parser parser) {
        skipComments(parser);

        if (!parser.skipSpaces().hasNext()) return false;

        String name = parser.readUntil(':');
        String type = parser.skipChar(':').readWord().toLowerCase();
        int attribute = parser.readInt();

        Item item;
        switch (type) {
            case "key":
                item = new Key(name, attribute);
                break;
            case "shield":
                item = new Shield(name, attribute);
                break;
            case "weapon":
                item = new Weapon(name, attribute);
                break;
            case "food":
                item = new Food(name, attribute);
                break;
            default:
                throw new RuntimeException("Unknown item type '" + type + "'");
        }

        itemRepository.save(item);
        return true;
    }

    private void skipComments(Parser parser) {
        while (parser.skipSpaces().hasNext() && parser.peek() == '#') {
            parser.readLine();
        }
    }
}
