package com.drpicox.game.monsters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MonsterController {

    @Autowired private MonsterParser monsterParser;
    @Autowired private MonsterRepository monsterRepository;

    public Monster get(String monsterName) {
        return monsterRepository.findById(monsterName).orElse(null);
    }

    public void read(String monsters) {
        monsterParser.read(monsters);
    }
}
