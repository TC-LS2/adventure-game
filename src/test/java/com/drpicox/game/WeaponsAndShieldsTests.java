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

import com.drpicox.game.TestHelper;
import com.drpicox.game.rooms.Direction;
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
public class WeaponsAndShieldsTests {

    @Autowired private TestHelper helper;

    @Before
    @After
    public void cleanup() throws Exception {
        helper.cleanup();
    }

    public static WorldBuilder buildWorld() {
        return new WorldBuilder()
                .name("Home sweet home")
                .description("You are at the main room of your home. It seems that your last order just arrived, a mighty sword able to cut through rocks.")
                .item("swordstone", "weapon", 12)

                .south().name("Cabin")
                .description("You are in front of your cabin. There is a Goron here. Gorons are famous for having prominent treasures. It seems very hard.")
                .item("rockshield", "shield", 8)
                .monster("Goron", 8, 8, "rockshield")

                .south().name("Goron nest")
                .description("You are inside a cave in the mountain. You found yourself in the middle of a Goron Nest. You wonder which marvelous treasures may hide.")
                .item("armory key", "key", 123)
                .monster("Goron chief", 10, 12, "armory key")
                .exit(Direction.EAST, 123)

                .east().name("Goron armory")
                .description("You managed to enter the armory. It is full of weapons and fabulous treasures.")
                .item("ultimate sword", "weapon", 16)

                .east().name("Goron defense room")
                .description("Finally, you managed to arrive here. Here Gorons store the finest and effective shields in the world. It is pretty sure that they are more strong that they seem.")
                .item("paper shield", "shield", 1)

                .north().name("Volcano")
                .description("Deeper in the Goron nest, you find the cauldron of a volcano. It is hot, and there is lava everywhere. It seems that something is moving and coming towards you.")
                .monster("lava monster", 10, 12)
                ;
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("== rooms:\n" +
                "0 0:Home sweet home:-1 0 -1 -1:swordstone\n" +
                "You are at the main room of your home. It seems that your last order just arrived, a mighty sword able to cut through rocks.\n" +
                "::::\n" +
                "-1 0:Cabin:0 0 -1 -1:Goron\n" +
                "You are in front of your cabin. There is a Goron here. Gorons are famous for having prominent treasures. It seems very hard.\n" +
                "::::\n" +
                "-2 0:Goron nest:0 -1 123 -1:Goron chief\n" +
                "You are inside a cave in the mountain. You found yourself in the middle of a Goron Nest. You wonder which marvelous treasures may hide.\n" +
                "::::\n" +
                "-2 1:Goron armory:-1 -1 0 0:ultimate sword\n" +
                "You managed to enter the armory. It is full of weapons and fabulous treasures.\n" +
                "::::\n" +
                "-2 2:Goron defense room:0 -1 -1 0:paper shield\n" +
                "Finally, you managed to arrive here. Here Gorons store the finest and effective shields in the world. It is pretty sure that they are more strong that they seem.\n" +
                "::::\n" +
                "-1 2:Volcano:-1 0 -1 -1:lava monster\n" +
                "Deeper in the Goron nest, you find the cauldron of a volcano. It is hot, and there is lava everywhere. It seems that something is moving and coming towards you.\n" +
                "::::\n" +
                "== items:\n" +
                "swordstone: weapon 12\n" +
                "rockshield: shield 8\n" +
                "armory key: key 123\n" +
                "ultimate sword: weapon 16\n" +
                "paper shield: shield 1\n" +
                "== monsters:\n" +
                "Goron:8 8:rockshield\n" +
                "Goron chief:10 12:armory key\n" +
                "lava monster:10 12:nothing\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "+---------------+                                                    \n" +
                "|HOME SWEET HOME|                                                    \n" +
                "|you are at t...|                                                    \n" +
                "|   (swordstone)|                                                    \n" +
                "+------o--------+                                                    \n" +
                "       |                                                             \n" +
                "       |                                                             \n" +
                "+------o--------+                          +------------------+      \n" +
                "|     CABIN     |                          |      VOLCANO     |      \n" +
                "|you are in f...|                          |deeper in the g...|      \n" +
                "|        (Goron)|                          |    (lava monster)|      \n" +
                "+------o--------+                          +--------o---------+      \n" +
                "       |                                            |                \n" +
                "       |                                            |                \n" +
                "+------o--------+      +------------+      +--------o---------+      \n" +
                "|   GORON NEST  |      |GORON ARMORY|      |GORON DEFENSE ROOM|      \n" +
                "|you are insi...▒------oyou manag...o------ofinally, you ma...|      \n" +
                "|  (Goron chief)|      |(ultimate...|      |    (paper shield)|      \n" +
                "+---------------+      +------------+      +------------------+      \n"));
    }

    @Test
    public void beat_them_with_weapons() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "look");
        helper.assertResult("Home sweet home\n" +
                "You are at the main room of your home. It seems th\n" +
                "at your last order just arrived, a mighty sword ab\n" +
                "Exits: south.\n" +
                "There is the swordstone weapon.\n" +
                "Player has 16 life points.");

        helper.runCommand("kirito", "get");
        helper.assertResult("Home sweet home\n" +
                "You are at the main room of your home. It seems th\n" +
                "at your last order just arrived, a mighty sword ab\n" +
                "Exits: south.\n" +
                "Player has the swordstone weapon.\n" +
                "Player has 16 life points.");

        helper.runCommand("kirito", "move", "south");
        helper.assertResult("Cabin\n" +
                "You are in front of your cabin. There is a Goron h\n" +
                "ere. Gorons are famous for having prominent treasu\n" +
                "Exits: north, south.\n" +
                "There is the Goron monster.\n" +
                "Player has the swordstone weapon.\n" +
                "Player has 16 life points.");

        helper.runCommand("kirito", "attack");
        helper.assertResult("Cabin\n" +
                "You are in front of your cabin. There is a Goron h\n" +
                "ere. Gorons are famous for having prominent treasu\n" +
                "Exits: north, south.\n" +
                "There is the rockshield shield.\n" +
                "Player has the swordstone weapon.\n" +
                "Player has 8 life points.");
    }

    @Test
    public void protect_yourself() throws Exception {
        helper.putWorld(buildWorld());


        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.assertResult("Cabin\n" +
                "You are in front of your cabin. There is a Goron h\n" +
                "ere. Gorons are famous for having prominent treasu\n" +
                "Exits: north, south.\n" +
                "Player has the rockshield shield.\n" +
                "Player has the swordstone weapon.\n" +
                "Player has 8 life points.");

        helper.runCommand("kirito", "move", "south");
        helper.assertResult("Goron nest\n" +
                "You are inside a cave in the mountain. You found y\n" +
                "ourself in the middle of a Goron Nest. You wonder \n" +
                "Exits: north, east (closed).\n" +
                "There is the Goron chief monster.\n" +
                "Player has the rockshield shield.\n" +
                "Player has the swordstone weapon.\n" +
                "Player has 8 life points.");

        helper.runCommand("kirito", "attack");
        helper.assertResult("Goron nest\n" +
                "You are inside a cave in the mountain. You found y\n" +
                "ourself in the middle of a Goron Nest. You wonder \n" +
                "Exits: north, east (closed).\n" +
                "There is the armory key key.\n" +
                "Player has the rockshield shield.\n" +
                "Player has the swordstone weapon.\n" +
                "Player has 6 life points.");
    }

    @Test
    public void combine_all_items() throws Exception {
        helper.putWorld(buildWorld());


        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.assertResult("Goron nest\n" +
                "You are inside a cave in the mountain. You found y\n" +
                "ourself in the middle of a Goron Nest. You wonder \n" +
                "Exits: north, east (closed).\n" +
                "Player has the armory key key.\n" +
                "Player has the rockshield shield.\n" +
                "Player has the swordstone weapon.\n" +
                "Player has 6 life points.");
    }

    @Test
    public void one_hand_one_weapon() throws Exception {
        helper.putWorld(buildWorld());


        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.assertResult("Goron armory\n" +
                "You managed to enter the armory. It is full of wea\n" +
                "pons and fabulous treasures.\n" +
                "Exits: east, west.\n" +
                "There is the ultimate sword weapon.\n" +
                "Player has the rockshield shield.\n" +
                "Player has the swordstone weapon.\n" +
                "Player has 6 life points.");

        helper.runCommand("kirito", "get");
        helper.assertResult("Goron armory\n" +
                "You managed to enter the armory. It is full of wea\n" +
                "pons and fabulous treasures.\n" +
                "Exits: east, west.\n" +
                "There is the swordstone weapon.\n" +
                "Player has the rockshield shield.\n" +
                "Player has the ultimate sword weapon.\n" +
                "Player has 6 life points.");
    }

    @Test
    public void one_hand_one_shield() throws Exception {
        helper.putWorld(buildWorld());


        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.assertResult("Goron defense room\n" +
                "Finally, you managed to arrive here. Here Gorons s\n" +
                "tore the finest and effective shields in the world\n" +
                " they seem.\n" +
                "Exits: north, west.\n" +
                "There is the paper shield shield.\n" +
                "Player has the rockshield shield.\n" +
                "Player has the ultimate sword weapon.\n" +
                "Player has 6 life points.");

        helper.runCommand("kirito", "get");
        helper.assertResult("Goron defense room\n" +
                "Finally, you managed to arrive here. Here Gorons s\n" +
                "tore the finest and effective shields in the world\n" +
                " they seem.\n" +
                "Exits: north, west.\n" +
                "There is the rockshield shield.\n" +
                "Player has the paper shield shield.\n" +
                "Player has the ultimate sword weapon.\n" +
                "Player has 6 life points.");
    }

    @Test
    public void you_die_you_loose_them_all() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "attack")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("player-killed")));

        helper.runCommand("kirito", "look");
        helper.assertResult("Home sweet home\n" +
                "You are at the main room of your home. It seems th\n" +
                "at your last order just arrived, a mighty sword ab\n" +
                "Exits: south.\n" +
                "Player has 16 life points.");
    }

}
