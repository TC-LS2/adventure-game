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

import com.drpicox.game.mocks.TimerTaskRunnerMock;
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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomRefreshTests {

    @Autowired private TestHelper helper;
    @Autowired private TimerTaskRunnerMock timerTaskRunnerMock;

    @Before @After
    public void cleanup() throws Exception {
        helper.cleanup();

        var mustacheStringifier = new MustacheGameStringifier();
        decorateMustache(mustacheStringifier);
        helper.setGameResultStringifier(mustacheStringifier);
    }

    public static void decorateMustache(MustacheGameStringifier mustache) {
        WeaponsAndShieldsTests.decorateMustache(mustache);
    }

    public static WorldBuilder buildWorld() {
        return new WorldBuilder()
                .name("High castle")
                .description("You are in the highest tower of a castle in the highest mountain. You can listen to the airflow between your ears. It seems that there is nobody here, but yet, there is a key, and there is a closed door that brings to the highest room of this castle.")
                .item("rusty key", "key", 123)
                .exit(Direction.NORTH, 123)

                .north().name("Highest room")
                .description("There is a monster here. Just old. Just waiting for you. It has that object that you wish for your whole life.")
                .item("wisdom sphere", "key", 875)
                .monster("snail", 0, 0, "wisdom sphere")
                ;
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("" +
                "== rooms:\n" +
                "0 0:High castle:123 -1 -1 -1:rusty key\n" +
                "You are in the highest tower of a castle in the highest mountain. You can listen to the airflow between your ears. It seems that there is nobody here, but yet, there is a key, and there is a closed door that brings to the highest room of this castle.\n" +
                "::::\n" +
                "1 0:Highest room:-1 0 -1 -1:snail\n" +
                "There is a monster here. Just old. Just waiting for you. It has that object that you wish for your whole life.\n" +
                "::::\n" +
                "== items:\n" +
                "rusty key: key 123\n" +
                "wisdom sphere: key 875\n" +
                "== monsters:\n" +
                "snail:0 0:wisdom sphere\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "+------------+      \n" +
                "|HIGHEST ROOM|      \n" +
                "|there is ...|      \n" +
                "|     (snail)|      \n" +
                "+-----o------+      \n" +
                "      |             \n" +
                "      |             \n" +
                "+-----▒------+      \n" +
                "| HIGH CASTLE|      \n" +
                "|you are i...|      \n" +
                "| (rusty key)|      \n" +
                "+------------+      \n"));
    }

    @Test
    public void objects_reappear() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "look");
        helper.assertResult("High castle\n" +
                "You are in the highest tower of a castle in the\n" +
                "highest mountain. You can listen to the airflow\n" +
                "between your ears. It seems that there is nobody\n" +
                "here, but yet, there is a key, and there is a\n" +
                "closed door that brings to the highest room of\n" +
                "this castle.\n" +
                "There is the rusty key key.\n" +
                "Exits: north (closed).\n" +
                "Player has 16 life points.");

        helper.runCommand("kirito", "get");
        helper.assertResult("High castle\n" +
                "You are in the highest tower of a castle in the\n" +
                "highest mountain. You can listen to the airflow\n" +
                "between your ears. It seems that there is nobody\n" +
                "here, but yet, there is a key, and there is a\n" +
                "closed door that brings to the highest room of\n" +
                "this castle.\n" +
                "Exits: north (closed).\n" +
                "Player has the rusty key key.\n" +
                "Player has 16 life points.");

        timerTaskRunnerMock.fireTaks();

        helper.runCommand("kirito", "look");
        helper.assertResult("High castle\n" +
                "You are in the highest tower of a castle in the\n" +
                "highest mountain. You can listen to the airflow\n" +
                "between your ears. It seems that there is nobody\n" +
                "here, but yet, there is a key, and there is a\n" +
                "closed door that brings to the highest room of\n" +
                "this castle.\n" +
                "There is the rusty key key.\n" +
                "Exits: north (closed).\n" +
                "Player has the rusty key key.\n" +
                "Player has 16 life points.");
    }

    @Test
    public void doors_close() throws Exception {
        helper.putWorld(buildWorld());


        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.assertResult("Highest room\n" +
                "There is a monster here. Just old. Just waiting\n" +
                "for you. It has that object that you wish for\n" +
                "your whole life.\n" +
                "There is the snail monster.\n" +
                "Exits: south.\n" +
                "Player has 16 life points.");

        helper.runCommand("kirito", "move", "south");
        helper.assertResult("High castle\n" +
                "You are in the highest tower of a castle in the\n" +
                "highest mountain. You can listen to the airflow\n" +
                "between your ears. It seems that there is nobody\n" +
                "here, but yet, there is a key, and there is a\n" +
                "closed door that brings to the highest room of\n" +
                "this castle.\n" +
                "Exits: north.\n" +
                "Player has 16 life points.");

        timerTaskRunnerMock.fireTaks();

        helper.runCommand("kirito", "look");
        helper.assertResult("High castle\n" +
                "You are in the highest tower of a castle in the\n" +
                "highest mountain. You can listen to the airflow\n" +
                "between your ears. It seems that there is nobody\n" +
                "here, but yet, there is a key, and there is a\n" +
                "closed door that brings to the highest room of\n" +
                "this castle.\n" +
                "There is the rusty key key.\n" +
                "Exits: north (closed).\n" +
                "Player has 16 life points.");
    }

    @Test
    public void monsters_respawn() throws Exception {
        helper.putWorld(buildWorld());


        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "attack");
        helper.assertResult("Highest room\n" +
                "There is a monster here. Just old. Just waiting\n" +
                "for you. It has that object that you wish for\n" +
                "your whole life.\n" +
                "There is the wisdom sphere key.\n" +
                "Exits: south.\n" +
                "Player has 16 life points.");

        timerTaskRunnerMock.fireTaks();

        helper.runCommand("kirito", "look");
        helper.assertResult("Highest room\n" +
                "There is a monster here. Just old. Just waiting\n" +
                "for you. It has that object that you wish for\n" +
                "your whole life.\n" +
                "There is the snail monster.\n" +
                "Exits: south.\n" +
                "Player has 16 life points.");
    }
}
