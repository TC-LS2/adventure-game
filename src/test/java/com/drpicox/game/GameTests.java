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

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private TestHelper helper;

    @After
    public void cleanup() throws Exception {
        helper.cleanup();
    }

    private ResultActions putWorld(String ...lines) throws Exception {
        return helper.putWorld(lines);
    }

    private ResultActions postCommands(String ...commands) throws Exception {
        return helper.postCommands("kirito", commands);
    }

    // ========= MONSTERS
    // =================================================================================

    @Test
    public void player_look_player_initial_points() throws Exception {
        putWorld(   "0 0:Jungle:-1 -1 -1 -1:nothing",
                    "Zzzzzzzzz.",
                    "::::");

        mockMvc.perform(post("/api/v1/players/kirito/commands/look"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.name", is("Jungle")))
                .andExpect(jsonPath("$.player.lifePoints", is(16)));
    }

    @Test
    public void player_look_monster() throws Exception {
        putWorld("== rooms:",
                "0 0:Jungle:-1 -1 -1 -1:mosquito",
                "Bzzzzzzz.",
                "::::",
                " == monsters:",
                "mosquito:  2 0:nothing");

        mockMvc.perform(post("/api/v1/players/kirito/commands/look"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.name", is("Jungle")))
                .andExpect(jsonPath("$.room.monster.name", is("mosquito")));
    }

    @Test
    public void player_attack_wins_monster_without_weapon() throws Exception {
        putWorld("== rooms:",
                    "0 0:Jungle:-1 -1 -1 -1:mosquito",
                    "Bzzzzzzz.",
                    "::::",
                " == monsters:",
                    "mosquito:  2 0:nothing");

        mockMvc.perform(post("/api/v1/players/kirito/commands/attack"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.monster", isEmptyOrNullString()))
                .andExpect(jsonPath("$.player.lifePoints", is(14)))
                .andExpect(jsonPath("$.code", containsString("monster-killed")))
                .andExpect(jsonPath("$.code", containsString("player-damaged")));
    }

    @Test
    public void player_attack_wins_monster_with_item() throws Exception {
        putWorld("== rooms:",
                    "0 0:Jungle:-1 -1 -1 -1:mosquito",
                    "Bzzzzzzz.",
                    "::::",
                " == items:",
                    "key:  KEY 123",
                " == monsters:",
                    "mosquito:  2 0:key");

        mockMvc.perform(post("/api/v1/players/kirito/commands/attack"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.monster", isEmptyOrNullString()))
                .andExpect(jsonPath("$.room.item.name", is("key")));
    }

    @Test
    public void player_attack_looses_monster() throws Exception {
        putWorld("== rooms:",
                    "0 0:Jungle:-1 -1 -1 -1:rabbit",
                    "Bzzzzzzz.",
                    "::::",
                " == monsters:",
                    "rabbit:  2 2:nothing");

        mockMvc.perform(post("/api/v1/players/kirito/commands/attack"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.monster.name", is("rabbit")))
                .andExpect(jsonPath("$.player.lifePoints", is(14)))
                .andExpect(jsonPath("$.code", not(containsString("monster-killed"))))
                .andExpect(jsonPath("$.code", containsString("player-damaged")));
    }

    @Test
    public void player_attack_dies() throws Exception {
        putWorld("== rooms:",
                    "0 0:Weird Place:0 -1 -1 -1:nothing",
                    "Some strange noise is at north.",
                    "You may want to take a look...",
                    "::::",
                    "1 0:World's End:-1 -1 -1 -1:cthulhu",
                    "...",
                    "::::",
                " == monsters:",
                    "cthulhu:  999 999:nothing");

        postCommands("kirito", "move?direction=north").andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/players/kirito/commands/attack"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("player-killed")));
    }

    @Test
    public void player_attack_next_position_is_the_beggining() throws Exception {
        putWorld("== rooms:",
                    "0 0:Weird Place:0 -1 -1 -1:nothing",
                    "Some strange noise is at north.",
                    "You may want to take a look...",
                    "::::",
                    "1 0:World's End:-1 -1 -1 -1:cthulhu",
                    "...",
                    "::::",
                " == monsters:",
                    "cthulhu:  999 999:nothing");

        postCommands("kirito", "move?direction=north").andExpect(status().isOk());
        postCommands("kirito", "attack").andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/v1/players/kirito/commands/look"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.name", is("Weird Place")))
                .andExpect(jsonPath("$.player.lifePoints", is(16)));
    }

    // ========= WEAPONS AND SHIELD
    // =================================================================================

    private void putWorldYourHouse() throws Exception {
        putWorld("== rooms:",
                    "0 0:Garden:0010 -1 -1 -1:house key",
                    "There is nice garden in front of your house.",
                    "In front of the door there is a nice carpet that says",
                    "Welcome to the Independent Republic of My Home.",
                    "You remember that you have a copy of your keys below it.",
                    "::::",
                    "1 0:Dinning Room:0 0 0 -1:newspaper",
                    "Nice light is entering throgh the window.",
                    "You may want to read the newspapers.",
                    "::::",
                    "1 1:Kitchen:-1 -1 1112 0:pantry key",
                    "You did your dishes already.",
                    "::::",
                    "1 2:Pantry:-1 -1 -1 0:fly",
                    "Here is were you keep all your food,",
                    "but there is a fly buzzing around,",
                    "You may want to get rid of it.",
                    "::::",
                    "2 0:Bedroom:-1 0 -1 0:mosquito",
                    "It is not time to sleep now, but",
                    "you may want to get rid of these ugly mosquito.",
                    "::::",
                " == items:",
                    "newspaper:  WEAPON 1",
                    "shirt:      SHIELD 2",
                    "house key:  KEY 0010",
                    "pantry key: KEY 1112",
                " == monsters:",
                    "fly:      0 1:shirt",
                    "mosquito: 2 1:nothing");
    }

    @Test
    public void player_get_weapon_keeps_key() throws Exception {
        putWorldYourHouse();

        postCommands("kirito", "get").andExpect(status().isOk());
        postCommands("kirito", "move?direction=north").andExpect(status().isOk());
        postCommands("kirito", "get").andExpect(status().isOk());
        postCommands("kirito", "move?direction=east").andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/players/kirito/commands/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.name", is("Kitchen")))
                .andExpect(jsonPath("$.player.key.name", is("pantry key")))
                .andExpect(jsonPath("$.player.weapon.name", is("newspaper")));

    }

    @Test
    public void player_attack_damage_points_come_from_the_weapon() throws Exception {
        putWorldYourHouse();

        postCommands("kirito", "get", "north", "get", "east", "get", "east");

        mockMvc.perform(post("/api/v1/players/kirito/commands/attack"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.name", is("Pantry")))
                .andExpect(jsonPath("$.room.monster", isEmptyOrNullString()))
                .andExpect(jsonPath("$.code", containsString("monster-killed")));
    }

    @Test
    public void player_get_shield_having_weapon() throws Exception {
        putWorldYourHouse();

        postCommands("kirito", "get", "north", "get", "east", "get", "east", "attack");

        mockMvc.perform(post("/api/v1/players/kirito/commands/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.name", is("Pantry")))
                .andExpect(jsonPath("$.player.weapon.name", is("newspaper")))
                .andExpect(jsonPath("$.player.shield.name", is("shirt")));
    }

    @Test
    public void player_attack_with_shield() throws Exception {
        putWorldYourHouse();

        postCommands("kirito",
                "get", "north", "get", "east", "get", "east", "attack", "get",
                "west", "west", "north");

        mockMvc.perform(post("/api/v1/players/kirito/commands/attack"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.weapon.name", is("newspaper")))
                .andExpect(jsonPath("$.player.shield.name", is("shirt")))
                .andExpect(jsonPath("$.player.lifePoints", is(16)))
                .andExpect(jsonPath("$.room.name", is("Bedroom")))
                .andExpect(jsonPath("$.room.monster", isEmptyOrNullString()))
                .andExpect(jsonPath("$.code", containsString("monster-killed")));
    }

    @Test
    public void player_get_shield_drops_previous_shield() throws Exception {
        putWorld("== rooms:",
                "0 0:Armory 0:0 -1 -1 -1:small shield",
                "An armory full of items",
                "::::",
                "1 0:Armory 1:-1 0 -1 -1:large shield",
                "An armory full of items",
                "::::",
                " == items:",
                "small shield: SHIELD 1",
                "large shield: SHIELD 2");

        postCommands("kirito", "get", "north");

        mockMvc.perform(post("/api/v1/players/kirito/commands/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.name", is("Armory 1")))
                .andExpect(jsonPath("$.room.item.name", is("small shield")))
                .andExpect(jsonPath("$.player.shield.name", is("large shield")));
    }

    @Test
    public void player_get_weapon_drops_previous_weapon() throws Exception {
        putWorld("== rooms:",
                "0 0:Armory 0:0 -1 -1 -1:small weapon",
                "An armory full of items",
                "::::",
                "1 0:Armory 1:-1 0 -1 -1:large weapon",
                "An armory full of items",
                "::::",
                " == items:",
                "small weapon: WEAPON 1",
                "large weapon: WEAPON 2");

        postCommands("kirito", "get", "north");

        mockMvc.perform(post("/api/v1/players/kirito/commands/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room.name", is("Armory 1")))
                .andExpect(jsonPath("$.room.item.name", is("small weapon")))
                .andExpect(jsonPath("$.player.weapon.name", is("large weapon")));
    }

    @Test
    public void default_world() throws Exception {
        mockMvc.perform(post("/api/v1/players/kirito/commands/look"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room", not(isEmptyOrNullString())));
    }

}
