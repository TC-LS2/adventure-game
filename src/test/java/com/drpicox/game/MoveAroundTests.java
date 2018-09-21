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
public class MoveAroundTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private TestHelper helper;

    @After
    public void cleanup() throws Exception {
        helper.cleanup();
    }

    public static WorldBuilder buildWorld() {
        return HelloWorldTests.buildWorld().goTo(0,0)

                .south().name("Crystal Beach")
                .description("" +
                        "There is one of the most beautiful beaches that " +
                        "you can imagine. Soft sand, great palm trees, " +
                        "water so crystal that you can see fishes " +
                        "swimming. And an endless sea, with some spotted " +
                        "islands far away.")

                .west().name("Crystal Beach")
                .description("" +
                        "The beach extends until the infinity. You are " +
                        "wondering if you start walking direction west " +
                        "will the beach continue forever. You see in the " +
                        "north a small path that goes to the Enchanted " +
                        "Forest.")

                .north().name("Enchanted Forest")
                .description("There is a small river traversing the " +
                        "Enchanted Forest that is flowing to the west. The " +
                        "sound of the water sounds like music.")

                .goTo(0, 0)
                .north().name("Cabin")
                .description("That is your cabin. Old fashioned wooden " +
                        "made. You are in the yard in front of it, with " +
                        "beautiful vegetation and a nice Porch. Towards the " +
                        "west, there is a small path towards the " +
                        "Enchanted Forest.")

                .east().name("Well")
                .description("You fell into the Well. It is dark and moisty. " +
                        "The water level reaches your waist. You tried to " +
                        "climb the walls of the well, but rocks are wet. " +
                        "You slid to the bottom again. It seems that you " +
                        "are trapped without exit.")
                .exit(Direction.WEST, -1)
                ;
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("== rooms:\n" +
                "0 0:A new world:0 0 -1 0:nothing\n" +
                "You see in front of your eyes one of the most beautiful and incredible worlds that you can imagine. Towards the south, you can see a big sea spotted with tiny islands, in the east, just far away, you can see the City of the East, with big spikes pointing to the sky, and in the west, there is the Enchanted Forest and the Eternity Mountains reaching the clouds.\n" +
                "::::\n" +
                "-1 0:Crystal Beach:0 -1 -1 0:nothing\n" +
                "There is one of the most beautiful beaches that you can imagine. Soft sand, great palm trees, water so crystal that you can see fishes swimming. And an endless sea, with some spotted islands far away.\n" +
                "::::\n" +
                "-1 -1:Crystal Beach:0 -1 0 -1:nothing\n" +
                "The beach extends until the infinity. You are wondering if you start walking direction west will the beach continue forever. You see in the north a small path that goes to the Enchanted Forest.\n" +
                "::::\n" +
                "0 -1:Enchanted Forest:-1 0 0 -1:nothing\n" +
                "There is a small river traversing the Enchanted Forest that is flowing to the west. The sound of the water sounds like music.\n" +
                "::::\n" +
                "1 0:Cabin:-1 0 0 -1:nothing\n" +
                "That is your cabin. Old fashioned wooden made. You are in the yard in front of it, with beautiful vegetation and a nice Porch. Towards the west, there is a small path towards the Enchanted Forest.\n" +
                "::::\n" +
                "1 1:Well:-1 -1 -1 -1:nothing\n" +
                "You fell into the Well. It is dark and moisty. The water level reaches your waist. You tried to climb the walls of the well, but rocks are wet. You slid to the bottom again. It seems that you are trapped without exit.\n" +
                "::::\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "                        +-------------+      +----+      \n" +
                "                        |    CABIN    |      |WELL|      \n" +
                "                        |that is yo...o------xy...|      \n" +
                "                        |             |      |    |      \n" +
                "                        +-----o-------+      +----+      \n" +
                "                              |                          \n" +
                "                              |                          \n" +
                "+----------------+      +-----o-------+                  \n" +
                "|ENCHANTED FOREST|      | A NEW WORLD |                  \n" +
                "|there is a sm...o------oyou see in...|                  \n" +
                "|                |      |             |                  \n" +
                "+-------o--------+      +-----o-------+                  \n" +
                "        |                     |                          \n" +
                "        |                     |                          \n" +
                "+-------o--------+      +-----o-------+                  \n" +
                "|  CRYSTAL BEACH |      |CRYSTAL BEACH|                  \n" +
                "|the beach ext...o------othere is o...|                  \n" +
                "|                |      |             |                  \n" +
                "+----------------+      +-------------+                  \n"));
    }

    @Test
    public void see_where_you_can_go() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "look")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.room.name", is("A new world")))
                .andExpect(jsonPath("$.room.description", containsString("You see in front of your eyes one of the most")))
                .andExpect(jsonPath("$.room.exits", containsInAnyOrder(
                        hasEntry("name", "north"),
                        hasEntry("name", "south"),
                        hasEntry("name", "west")
                )));
    }

    @Test
    public void move() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "south")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.room.name", is("Crystal Beach")))
                .andExpect(jsonPath("$.room.description", containsString("There is one of the most beautiful beaches")))
                .andExpect(jsonPath("$.room.exits", containsInAnyOrder(
                        hasEntry("name", "north"),
                        hasEntry("name", "west")
                )));
    }

    @Test
    public void move_towards_other_directions_1() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "move", "west")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.room.name", is("Crystal Beach")))
                .andExpect(jsonPath("$.room.description", containsString("The beach extends until the infinity")))
                .andExpect(jsonPath("$.room.exits", containsInAnyOrder(
                        hasEntry("name", "north"),
                        hasEntry("name", "east")
                )));
    }

    @Test
    public void move_towards_other_directions_2() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "move", "west");
        helper.runCommand("kirito", "move", "north")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.room.name", is("Enchanted Forest")))
                .andExpect(jsonPath("$.room.description", containsString("There is a small river traversing the Enchanted")))
                .andExpect(jsonPath("$.room.exits", containsInAnyOrder(
                        hasEntry("name", "south"),
                        hasEntry("name", "east")
                )));
    }

    @Test
    public void move_towards_other_directions_3() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "move", "west");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "move", "east")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.room.name", is("A new world")))
                .andExpect(jsonPath("$.room.description", containsString("You see in front of your eyes one of the most")))
                .andExpect(jsonPath("$.room.exits", containsInAnyOrder(
                        hasEntry("name", "north"),
                        hasEntry("name", "south"),
                        hasEntry("name", "west")
                )));
    }

    @Test
    public void exits_or_not_exits() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "east")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("no-exit")));
    }

    @Test
    public void its_a_trap_1() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "north")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.room.name", is("Cabin")))
                .andExpect(jsonPath("$.room.description", containsString("That is your cabin. Old fashioned wooden made")))
                .andExpect(jsonPath("$.room.exits", containsInAnyOrder(
                        hasEntry("name", "south"),
                        hasEntry("name", "east")
                )));
    }

    @Test
    public void its_a_trap_2() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "move", "east")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.room.name", is("Well")))
                .andExpect(jsonPath("$.room.description", containsString("You fell into the Well. It is dark and moisty.")))
                .andExpect(jsonPath("$.room.exits", hasSize(0)));
    }
}
