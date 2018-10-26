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
public class FoodTests {

    @Autowired private TestHelper helper;

    @Before @After
    public void cleanup() throws Exception {
        helper.cleanup();
    }

    public static void decorateMustache(MustacheGameStringifier mustache) {
        WeaponsAndShieldsTests.decorateMustache(mustache);
    }

    public static WorldBuilder buildWorld() {
        return new WorldBuilder()
                .name("Home sweet home")
                .description("You are at the main room of your home. There is a disturbing little noise around.")
                .item("mosquito leg", "food", 2)
                .monster("mosquito", 2, 0, "mosquito leg")

                .east().name("Kitchen")
                .description("That is the kitchen. That is the place where you prepare your most delicious dishes. What is that that smells so good?")
                .item("best cake ever", "food", 16)
                ;
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("" +
                "== rooms:\n" +
                "0 0:Home sweet home:-1 -1 0 -1:mosquito\n" +
                "You are at the main room of your home. There is a disturbing little noise around.\n" +
                "::::\n" +
                "0 1:Kitchen:-1 -1 -1 0:best cake ever\n" +
                "That is the kitchen. That is the place where you prepare your most delicious dishes. What is that that smells so good?\n" +
                "::::\n" +
                "== items:\n" +
                "mosquito leg: food 2\n" +
                "best cake ever: food 16\n" +
                "== monsters:\n" +
                "mosquito:2 0:mosquito leg\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "+---------------+      +-------+      \n" +
                "|HOME SWEET HOME|      |KITCHEN|      \n" +
                "|you are at t...o------othat...|      \n" +
                "|     (mosquito)|      |(bes...|      \n" +
                "+---------------+      +-------+      \n"));
    }

    @Test
    public void recover_loosed_life_points() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "look");
        helper.assertResult("Home sweet home\n" +
                "You are at the main room of your home. There is a \n" +
                "disturbing little noise around.\n" +
                "Exits: east.\n" +
                "There is the mosquito monster.\n" +
                "Player has 16 life points.");

        helper.runCommand("kirito", "attack");
        helper.assertResult("Home sweet home\n" +
                "You are at the main room of your home. There is a \n" +
                "disturbing little noise around.\n" +
                "Exits: east.\n" +
                "There is the mosquito leg food.\n" +
                "Player has 14 life points.");

        helper.runCommand("kirito", "get");
        helper.assertResult("Home sweet home\n" +
                "You are at the main room of your home. There is a \n" +
                "disturbing little noise around.\n" +
                "Exits: east.\n" +
                "Player has 16 life points.");
    }

    @Test
    public void eat_is_worthless_if_you_are_not_hungry() throws Exception {
        helper.putWorld(buildWorld());


        helper.runCommand("kirito", "attack");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.assertResult("Kitchen\n" +
                "That is the kitchen. That is the place where you p\n" +
                "repare your most delicious dishes. What is that th\n" +
                "Exits: west.\n" +
                "There is the best cake ever food.\n" +
                "Player has 16 life points.");

        helper.runCommand("kirito", "get");
        helper.assertResult("Kitchen\n" +
                "That is the kitchen. That is the place where you p\n" +
                "repare your most delicious dishes. What is that th\n" +
                "Exits: west.\n" +
                "Player has 16 life points.");
    }
}
