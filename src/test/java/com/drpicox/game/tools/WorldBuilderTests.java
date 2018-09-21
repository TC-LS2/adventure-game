package com.drpicox.game.tools;

import org.junit.Before;
import org.junit.Test;

import static com.drpicox.game.rooms.Direction.EAST;
import static com.drpicox.game.rooms.Direction.NORTH;
import static org.junit.Assert.assertEquals;

public class WorldBuilderTests {

    private WorldBuilder builder;

    private void assertBuildIs(String ...lines) {
        assertEquals(builder.build(), String.join("\n", lines));
    }

    private void assertMapIs(String ...lines) {
        assertEquals(builder.map(), String.join("\n", lines));
    }

    @Before
    public void setupWorldBuilder() {
        builder = new WorldBuilder();
    }

    @Test
    public void set_room_name_and_description() {
        builder.name("Initial Room")
                .description("Initial Description");

        assertBuildIs("" +
                "== rooms:\n" +
                "0 0:Initial Room:-1 -1 -1 -1:nothing\n" +
                "Initial Description\n" +
                "::::\n" +
                ""
        );
    }

    @Test
    public void autocreates_exits_and_rooms() {
        builder.name("R00")
                .north().name("R10")
                .east().name("R11")
                .west().west().name("R1-1")
                .goTo(0, 0)
                .south().name("R-10");

        assertBuildIs("" +
                "== rooms:\n" +
                "0 0:R00:0 0 -1 -1:nothing\n" +
                "no description\n" +
                "::::\n" +
                "1 0:R10:-1 0 0 0:nothing\n" +
                "no description\n" +
                "::::\n" +
                "1 1:R11:-1 -1 -1 0:nothing\n" +
                "no description\n" +
                "::::\n" +
                "1 -1:R1-1:-1 -1 0 -1:nothing\n" +
                "no description\n" +
                "::::\n" +
                "-1 0:R-10:0 -1 -1 -1:nothing\n" +
                "no description\n" +
                "::::\n" +
                ""
        );
    }

    @Test
    public void change_exits() {
        builder.name("R00").exit(NORTH, 123).north().name("R10");

        assertBuildIs("" +
                "== rooms:\n" +
                "0 0:R00:123 -1 -1 -1:nothing\n" +
                "no description\n" +
                "::::\n" +
                "1 0:R10:-1 0 -1 -1:nothing\n" +
                "no description\n" +
                "::::\n" +
                ""
        );
    }

    @Test
    public void add_items() {
        builder.item("small key", "KEY", 123)
            .north().item("small key");

        assertBuildIs("" +
                "== rooms:\n" +
                "0 0:no name:0 -1 -1 -1:small key\n" +
                "no description\n" +
                "::::\n" +
                "1 0:no name:-1 0 -1 -1:small key\n" +
                "no description\n" +
                "::::\n" +
                "== items:\n" +
                "small key: KEY 123\n" +
                ""
        );
    }

    @Test
    public void add_monsters() {
        builder.item("small key", "KEY", 123)
                .monster("troll", 10, 2, "small key")
                .north().monster("troll")
                .north().monster("mosquito", 2, 1);

        assertBuildIs("" +
                "== rooms:\n" +
                "0 0:no name:0 -1 -1 -1:troll\n" +
                "no description\n" +
                "::::\n" +
                "1 0:no name:0 0 -1 -1:troll\n" +
                "no description\n" +
                "::::\n" +
                "2 0:no name:-1 0 -1 -1:mosquito\n" +
                "no description\n" +
                "::::\n" +
                "== items:\n" +
                "small key: KEY 123\n" +
                "== monsters:\n" +
                "troll:10 2:small key\n" +
                "mosquito:2 1:nothing\n" +
                ""
        );
    }

    @Test
    public void generates_a_map() {
        builder.name("Welcome")
                .description("You just entered to the world")

                .north().name("Enchanted Forest")
                .description("Trees and more")
                .item("rosted old large key")
                .exit(EAST, 123)

                .east().name("Cabin")
                .description("Like Home")

                .south().name("Dark Moisty and Dirty Basement")
                .description("Something moves under here")
                .monster("troll")

                .south().name("Hole")
                .description("you cannot leave")
                .exit(NORTH, -1)
                ;

        assertMapIs("" +
                "+----------------+      +------------------+      \n" +
                "|ENCHANTED FOREST|      |       CABIN      |      \n" +
                "|trees and more  ▒------olike home         |      \n" +
                "|(rosted old l...|      |                  |      \n" +
                "+-------o--------+      +--------o---------+      \n" +
                "        |                        |                \n" +
                "        |                        |                \n" +
                "+-------o--------+      +--------o---------+      \n" +
                "|     WELCOME    |      |DARK MOISTY AND...|      \n" +
                "|you just ente...o------osomething moves...|      \n" +
                "|                |      |           (troll)|      \n" +
                "+----------------+      +--------o---------+      \n" +
                "                                 |                \n" +
                "                                 |                \n" +
                "                        +--------x---------+      \n" +
                "                        |       HOLE       |      \n" +
                "                        |you cannot leave  |      \n" +
                "                        |                  |      \n" +
                "                        +------------------+      \n");
    }
}
