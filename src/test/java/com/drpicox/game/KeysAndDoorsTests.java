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
import com.drpicox.game.tools.WorldBuilder;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KeysAndDoorsTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private TestHelper helper;

    @After
    public void cleanup() throws Exception {
        helper.cleanup();
    }



    public static WorldBuilder buildWorld() {
        return new WorldBuilder()
                .name("Home sweet home")
                .description("You are in front of your home, main door is closed. You remember that you have the key under the carpet.")
                .exit(Direction.NORTH, 123)
                .item("key", "KEY", 123)

                .north().name("Home sweet home")
                .description("You are in the main room of your home. There is plenty of light and space.")

                .goTo(0, 0).west().name("Barn")
                .description("Is closed and you have lost the key")
                .goTo(0, 0).exit(Direction.WEST, 744)

                .goTo(0, 0).north().north().name("Key Locker")
                .description("Here you have some useful keys")
                .item("rust key", "KEY", 234)

                .south().east().name("Kitchen")
                .description("That it is a great kitchen. There is a cupboard in the south.")
                .item("small key", "KEY", 345)
                .exit(Direction.EAST, 345)

                .east().name("Cupboard")
                .description("You store here some nice food.")
                ;
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("== rooms:\n" +
                "0 0:Home sweet home:123 -1 -1 744:key\n" +
                "You are in front of your home, main door is closed. You remember that you have the key under the carpet.\n" +
                "::::\n" +
                "1 0:Home sweet home:0 0 0 -1:nothing\n" +
                "You are in the main room of your home. There is plenty of light and space.\n" +
                "::::\n" +
                "0 -1:Barn:-1 -1 0 -1:nothing\n" +
                "Is closed and you have lost the key\n" +
                "::::\n" +
                "2 0:Key Locker:-1 0 -1 -1:rust key\n" +
                "Here you have some useful keys\n" +
                "::::\n" +
                "1 1:Kitchen:-1 -1 345 0:small key\n" +
                "That it is a great kitchen. There is a cupboard in the south.\n" +
                "::::\n" +
                "1 2:Cupboard:-1 -1 -1 0:nothing\n" +
                "You store here some nice food.\n" +
                "::::\n" +
                "== items:\n" +
                "key: KEY 123\n" +
                "rust key: KEY 234\n" +
                "small key: KEY 345\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "            +---------------+                                     \n" +
                "            |   KEY LOCKER  |                                     \n" +
                "            |here you hav...|                                     \n" +
                "            |     (rust key)|                                     \n" +
                "            +------o--------+                                     \n" +
                "                   |                                              \n" +
                "                   |                                              \n" +
                "            +------o--------+      +-------+      +--------+      \n" +
                "            |HOME SWEET HOME|      |KITCHEN|      |CUPBOARD|      \n" +
                "            |you are in t...o------othat...▒------oyou s...|      \n" +
                "            |               |      |(sma...|      |        |      \n" +
                "            +------o--------+      +-------+      +--------+      \n" +
                "                   |                                              \n" +
                "                   |                                              \n" +
                "+----+      +------▒--------+                                     \n" +
                "|BARN|      |HOME SWEET HOME|                                     \n" +
                "|i...o------▒you are in f...|                                     \n" +
                "|    |      |          (key)|                                     \n" +
                "+----+      +---------------+                                     \n"));
    }

    @Test
    public void closed_doors_1() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "look")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.room.name", is("Home sweet home")))
                .andExpect(jsonPath("$.room.description", containsString("You are in front of your home")))
                .andExpect(jsonPath("$.room.item.name", is("key")))
                .andExpect(jsonPath("$.room.exits", containsInAnyOrder(
                        allOf(hasEntry("name", "north"), (Matcher) hasEntry("open", false)),
                        allOf(hasEntry("name", "west"), (Matcher) hasEntry("open", false))
                )));
    }

    @Test
    public void closed_doors_2() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "north")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("exit-closed")));
    }

    @Test
    public void get_the_key_and_open_the_door_1() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.player.key.name", is("key")))
                .andExpect(jsonPath("$.room.name", is("Home sweet home")))
                .andExpect(jsonPath("$.room.description", containsString("You are in front of your home")))
                .andExpect(jsonPath("$.room.item", isEmptyOrNullString()))
                .andExpect(jsonPath("$.room.exits", containsInAnyOrder(
                        allOf(hasEntry("name", "north"), (Matcher) hasEntry("open", false)),
                        allOf(hasEntry("name", "west"), (Matcher) hasEntry("open", false))
                )));
    }

    @Test
    public void get_the_key_and_open_the_door_2() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.player.key", isEmptyOrNullString()))
                .andExpect(jsonPath("$.room.name", is("Home sweet home")))
                .andExpect(jsonPath("$.room.description", containsString("You are in the main room of your home")))
                .andExpect(jsonPath("$.room.item", isEmptyOrNullString()))
                .andExpect(jsonPath("$.room.exits", containsInAnyOrder(
                        allOf(hasEntry("name", "south"), (Matcher) hasEntry("open", true)),
                        allOf(hasEntry("name", "north"), (Matcher) hasEntry("open", true)),
                        allOf(hasEntry("name", "east"), (Matcher) hasEntry("open", true))
                )));
    }

    @Test
    public void get_the_key_and_open_the_door__once_opened_remains_opened() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "move", "south")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.player.key", isEmptyOrNullString()))
                .andExpect(jsonPath("$.room.name", is("Home sweet home")))
                .andExpect(jsonPath("$.room.description", containsString("You are in front of your home")))
                .andExpect(jsonPath("$.room.item", isEmptyOrNullString()))
                .andExpect(jsonPath("$.room.exits", containsInAnyOrder(
                        allOf(hasEntry("name", "north"), (Matcher) hasEntry("open", true)),
                        allOf(hasEntry("name", "west"), (Matcher) hasEntry("open", false))
                )));
    }

    @Test
    public void the_right_key() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "west")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("exit-closed")));
    }

    @Test
    public void one_hand_and_so_many_keys_1() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "move", "north")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.key", isEmptyOrNullString()))
                .andExpect(jsonPath("$.room.description", containsString("Here you have some useful keys")))
                .andExpect(jsonPath("$.room.item.name", is("rust key")));
    }

    @Test
    public void one_hand_and_so_many_keys_2() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.key.name", is("rust key")))
                .andExpect(jsonPath("$.room.description", containsString("Here you have some useful keys")))
                .andExpect(jsonPath("$.room.item", isEmptyOrNullString()));
    }

    @Test
    public void one_hand_and_so_many_keys_3() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "move", "east")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.key.name", is("rust key")))
                .andExpect(jsonPath("$.room.description", containsString("That it is a great kitchen")))
                .andExpect(jsonPath("$.room.item.name", is("small key")));
    }

    @Test
    public void one_hand_and_so_many_keys_4() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.key.name", is("small key")))
                .andExpect(jsonPath("$.room.description", containsString("That it is a great kitchen")))
                .andExpect(jsonPath("$.room.item.name", is("rust key")));
    }

    @Test
    public void no_item_no_get() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("no-item")));
    }
}