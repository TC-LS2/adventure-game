/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.drpicox.game;

import com.drpicox.game.rooms.Direction;
import com.drpicox.game.tools.MustacheGameStringifier;
import com.drpicox.game.tools.WorldBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MonstersTests {

    @Autowired private TestHelper helper;

    @Before
    @After
    public void cleanup() throws Exception {
        helper.cleanup();
    }

    public static void decorateMustache(MustacheGameStringifier mustache) {
        KeysAndDoorsTests.decorateMustache(mustache);

        mustache.setTemplate("room", "" +
                "{{name}}\n" +
                "{{description}}\n" +
                "{{#item}}{{>roomItem}}{{/item}}" +
                "{{#monster}}{{>roomMonster}}{{/monster}}" +
                "{{#exits}}{{>exits}}{{/exits}}");

        mustache.setTemplate("roomMonster", "" +
                "There is the {{name}} monster.\n");

        mustache.setTemplate("player", "" +
                "{{#key}}{{>playerItem}}{{/key}}" +
                "Player has {{lifePoints}} life points.\n");
    }

    public static WorldBuilder buildWorld() {
        return new WorldBuilder()
                .name("Home sweet home")
                .description("You are in the main room of your home. There is plenty of light and space.")
                .monster("fly", 0, 0)
                .exit(Direction.SOUTH, 123)

                .west().name("Sweet bedroom")
                .description("That is your bedrooom, possibly the most cosy place of all of your home. Did you listen that?")
                .monster("mosquito", 2, 0)

                .south().name("Bathroom")
                .description("That was the bathroom of your dreams, nice light, clean, incredible bathroom... is someone knoking to the door?")
                .item("main house key", "key", 123)
                .monster("big mosquito", 3, 0, "main house key")
                .exit(Direction.EAST, -1)

                .goTo(0, 0).south().name("Cabin")
                .description("You are in front of your cabin, main door is closed. You remember that you have the key under the carpet. There is a Goron here. Gorons are famous for having big treasures. It seems very hard.")
                .exit(Direction.WEST, -1)
                .monster("Goron", 8, 8)

                .south().name("World's End")
                .description("Cthulhu is here...")
                .monster("Cthulhu", 999, 999)
                ;
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("== rooms:\n" +
                "0 0:Home sweet home:-1 123 -1 0:fly\n" +
                "You are in the main room of your home. There is plenty of light and space.\n" +
                "::::\n" +
                "0 -1:Sweet bedroom:-1 0 0 -1:mosquito\n" +
                "That is your bedrooom, possibly the most cosy place of all of your home. Did you listen that?\n" +
                "::::\n" +
                "-1 -1:Bathroom:0 -1 -1 -1:big mosquito\n" +
                "That was the bathroom of your dreams, nice light, clean, incredible bathroom... is someone knoking to the door?\n" +
                "::::\n" +
                "-1 0:Cabin:0 0 -1 -1:Goron\n" +
                "You are in front of your cabin, main door is closed. You remember that you have the key under the carpet. There is a Goron here. Gorons are famous for having big treasures. It seems very hard.\n" +
                "::::\n" +
                "-2 0:World's End:0 -1 -1 -1:Cthulhu\n" +
                "Cthulhu is here...\n" +
                "::::\n" +
                "== items:\n" +
                "main house key: key 123\n" +
                "== monsters:\n" +
                "fly:0 0:nothing\n" +
                "mosquito:2 0:nothing\n" +
                "big mosquito:3 0:main house key\n" +
                "Goron:8 8:nothing\n" +
                "Cthulhu:999 999:nothing\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "+-------------+      +---------------+      \n" +
                "|SWEET BEDROOM|      |HOME SWEET HOME|      \n" +
                "|that is yo...o------oyou are in t...|      \n" +
                "|   (mosquito)|      |          (fly)|      \n" +
                "+-----o-------+      +------▒--------+      \n" +
                "      |                     |               \n" +
                "      |                     |               \n" +
                "+-----o-------+      +------o--------+      \n" +
                "|   BATHROOM  |      |     CABIN     |      \n" +
                "|that was t...x      xyou are in f...|      \n" +
                "|(big mosqu...|      |        (Goron)|      \n" +
                "+-------------+      +------o--------+      \n" +
                "                            |               \n" +
                "                            |               \n" +
                "                     +------o--------+      \n" +
                "                     |  WORLD'S END  |      \n" +
                "                     |cthulhu is h...|      \n" +
                "                     |      (Cthulhu)|      \n" +
                "                     +---------------+      \n"));
    }

    @Test
    public void defeat_monsters() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "look");
        helper.assertResult("Home sweet home\n" +
                "You are in the main room of your home. There is pl\n" +
                "enty of light and space.\n" +
                "Exits: south (closed), west.\n" +
                "There is the fly monster.\n" +
                "Player has 16 life points.");

        helper.runCommand("kirito", "attack");
        helper.assertResult("Home sweet home\n" +
                "You are in the main room of your home. There is pl\n" +
                "enty of light and space.\n" +
                "Exits: south (closed), west.\n" +
                "Player has 16 life points.");
    }

    @Test
    public void ouch_they_strike_back() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "west");
        helper.assertResult("Sweet bedroom\n" +
                "That is your bedrooom, possibly the most cosy plac\n" +
                "e of all of your home. Did you listen that?\n" +
                "Exits: south, east.\n" +
                "There is the mosquito monster.\n" +
                "Player has 16 life points.");

        helper.runCommand("kirito", "attack");
        helper.assertResult("Sweet bedroom\n" +
                "That is your bedrooom, possibly the most cosy plac\n" +
                "e of all of your home. Did you listen that?\n" +
                "Exits: south, east.\n" +
                "Player has 14 life points.");
    }

    @Test
    public void monsters_have_treasures() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "west");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "south");
        helper.assertResult("Bathroom\n" +
                "That was the bathroom of your dreams, nice light, \n" +
                "clean, incredible bathroom... is someone knoking t\n" +
                "Exits: north.\n" +
                "There is the big mosquito monster.\n" +
                "Player has 14 life points.");

        helper.runCommand("kirito", "attack");
        helper.assertResult("Bathroom\n" +
                "That was the bathroom of your dreams, nice light, \n" +
                "clean, incredible bathroom... is someone knoking t\n" +
                "Exits: north.\n" +
                "There is the main house key key.\n" +
                "Player has 11 life points.");

        helper.runCommand("kirito", "get");
        helper.assertResult("Bathroom\n" +
                "That was the bathroom of your dreams, nice light, \n" +
                "clean, incredible bathroom... is someone knoking t\n" +
                "Exits: north.\n" +
                "Player has the main house key key.\n" +
                "Player has 11 life points.");
    }

    @Test
    public void you_main_not_even_scratch_them() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "west");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.assertResult("Sweet bedroom\n" +
                "That is your bedrooom, possibly the most cosy plac\n" +
                "e of all of your home. Did you listen that?\n" +
                "Exits: south, east.\n" +
                "Player has the main house key key.\n" +
                "Player has 11 life points.");

        helper.runCommand("kirito", "move", "east");
        helper.assertResult("Home sweet home\n" +
                "You are in the main room of your home. There is pl\n" +
                "enty of light and space.\n" +
                "Exits: south (closed), west.\n" +
                "Player has the main house key key.\n" +
                "Player has 11 life points.");

        helper.runCommand("kirito", "move", "south");
        helper.assertResult("Cabin\n" +
                "You are in front of your cabin, main door is close\n" +
                "d. You remember that you have the key under the ca\n" +
                " having big treasures. It seems very hard.\n" +
                "Exits: north, south.\n" +
                "There is the Goron monster.\n" +
                "Player has 11 life points.");

        helper.runCommand("kirito", "attack");
        helper.assertResult("Cabin\n" +
                "You are in front of your cabin, main door is close\n" +
                "d. You remember that you have the key under the ca\n" +
                " having big treasures. It seems very hard.\n" +
                "Exits: north, south.\n" +
                "There is the Goron monster.\n" +
                "Player has 3 life points.");
    }


    @Test
    public void oh_oh_you_avatar_may_die() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "west");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "south");
        helper.assertResult("World's End\n" +
                "Cthulhu is here...\n" +
                "Exits: north.\n" +
                "There is the Cthulhu monster.\n" +
                "Player has 3 life points.");

        helper.runCommand("kirito", "attack")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("player-killed")));
    }

    @Test
    public void when_you_die() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "west");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "look");
        helper.assertResult("Home sweet home\n" +
                "You are in the main room of your home. There is pl\n" +
                "enty of light and space.\n" +
                "Exits: south, west.\n" +
                "Player has 16 life points.");
    }

    @Test
    public void no_monster_no_attack() throws Exception {
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "west");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");

        helper.runCommand("kirito", "attack")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("no-monster")));
    }

}
