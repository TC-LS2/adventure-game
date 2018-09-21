package com.drpicox.game.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ItemController {

    @Autowired private ItemParser itemParser;
    @Autowired private ItemRepository itemRepository;

    public Item get(String itemName) {
        return itemRepository.findById(itemName).orElse(null);
    }

    public void read(String items) {
        itemParser.read(items);
    }
}
