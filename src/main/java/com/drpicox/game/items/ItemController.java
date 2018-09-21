package com.drpicox.game.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ItemController {

    private ItemParser itemParser;
    private ItemRepository itemRepository;

    public ItemController(ItemParser itemParser, ItemRepository itemRepository) {
        this.itemParser = itemParser;
        this.itemRepository = itemRepository;
    }

    public Item get(String itemName) {
        return itemRepository.findById(itemName).orElse(null);
    }

    public void read(String items) {
        itemParser.read(items);
    }
}
