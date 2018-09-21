package com.drpicox.game.monsters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MonsterController {

    private MonsterParser monsterParser;
    private MonsterRepository monsterRepository;

    public MonsterController(MonsterParser monsterParser, MonsterRepository monsterRepository) {
        this.monsterParser = monsterParser;
        this.monsterRepository = monsterRepository;
    }

    public Monster get(String monsterName) {
        return monsterRepository.findById(monsterName).orElse(null);
    }

    public void read(String monsters) {
        monsterParser.read(monsters);
    }
}
