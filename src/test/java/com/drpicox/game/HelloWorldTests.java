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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloWorldTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private TestHelper helper;

    @After
    public void cleanup() throws Exception {
        helper.cleanup();
    }

    public static WorldBuilder buildWorld() {
        return new WorldBuilder()
                .name("A new world")
                .description("You see in front of your eyes one of the most " +
                        "beautiful and incredible worlds that you can " +
                        "imagine. Towards the south, you can see a big " +
                        "sea spotted with tiny islands, in the east, " +
                        "just far away, you can see the City of the East, " +
                        "with big spikes pointing to the sky, and in the " +
                        "west, there is the Enchanted Forest and the " +
                        "Eternity Mountains reaching the clouds.");
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("== rooms:\n" +
                "0 0:A new world:-1 -1 -1 -1:nothing\n" +
                "You see in front of your eyes one of the most beautiful and incredible worlds that you can imagine. Towards the south, you can see a big sea spotted with tiny islands, in the east, just far away, you can see the City of the East, with big spikes pointing to the sky, and in the west, there is the Enchanted Forest and the Eternity Mountains reaching the clouds.\n" +
                "::::\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "+-----------+      \n" +
                "|A NEW WORLD|      \n" +
                "|you see ...|      \n" +
                "|           |      \n" +
                "+-----------+      \n"));
    }

    @Test
    public void do_login_and_look_again() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "look")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username", is("kirito")))
                .andExpect(jsonPath("$.room.name", is("A new world")))
                .andExpect(jsonPath("$.room.description", containsString("You see in front of your eyes one of the most")))
                .andExpect(jsonPath("$.room.description", containsString("Eternity Mountains reaching the clouds.")));
    }

    @Test
    public void put_world_returns_a_valid_json() throws Exception {
        helper.putWorld(buildWorld())
                .andExpect(jsonPath("$.status", is("success")));
    }
}
