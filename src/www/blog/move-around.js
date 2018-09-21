import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Jul 10 2018").getTime(),
  title: "Move around!",
  tagline: "The world was very tinier before, now it becomes colossal.",
  world: {
    rooms:
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
      "::::\n",
  },
  map:
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
    "+----------------+      +-------------+                  \n",
  Component: () => (
    <ReactMarkdown
      source={`
**Come and explore.**
Move north, south, east, and west. There can be unlimited rooms to explore, from south to north and from east to west.

You might discover rivers, mountains, forests, cities, or even space stations or fantastic worlds.

## See where you can go
You start in a room, with its own name and description. Now you can see which exits are there: north, south, east and west.

    A new world.
    You see in front of your eyes one of the most
    beautiful and incredible worlds that you can
    imagine. Towards the south, you can see a big
    sea spotted with tiny islands, in the east,
    just far away, you can see the City of the East,
    with big spikes pointing to the sky, and in the
    west, there is the Enchanted Forest and the 
    Eternity Mountains reaching the clouds.
    Exits: north, south, west.
    > █

## Move!
If there is an exit to the south, do the first step and command
your avatar to \`move\` in the south direction.
You will cross the exit and discover a new room.
Its name and the description of the new room and also
which other exits this room has will be presented to you.

    > move south
    Crystal Beach.
    There is one of the most beautiful beaches that you can 
    imagine. Soft sand, great palm trees, water so crystal 
    that you can see fishes swimming. And an endless sea, 
    with some spotted islands far away.
    Exits: north, east.
    > █

Now try other directions, remember that you can also go to the south, to the east, and to the west.

    > move west
    Crystal Beach.
    The beach extends until the infinity. You are 
    wondering if you start walking direction west will 
    the beach continue forever. You see in the north a 
    small path that goes to the Enchanted Forest.
    Exits: north, west.
    > move north
    Enchanted forest.
    There is a small river traversing the Enchanted 
    Forest that is flowing to the west. The sound of 
    the water sounds like music.
    Exits: south, east.
    > move east
    A new world.
    You see in front of your eyes one of the most
    beautiful and incredible worlds that you can
    imagine. Towards the south, you can see a big
    sea spotted with tiny islands, in the east,
    just far away, you can see the City of the East,
    with big spikes pointing to the sky, and in the
    west, there is the Enchanted Forest and the 
    Eternity Mountains reaching the clouds.
    Exits: north, south, west.
    > █

## Exits or not exits.

Not always is possible to move towards any direction.
If that happens, your avatar will not move if you
ask to go towards an unexisting exit.

    A new world.
    You see in front of your eyes one of the most
    beautiful and incredible worlds that you can
    imagine. Towards the south, you can see a big
    sea spotted with tiny islands, in the east,
    just far away, you can see the City of the East,
    with big spikes pointing to the sky, and in the
    west, there is the Enchanted Forest and the 
    Eternity Mountains reaching the clouds.
    Exits: north, south, west.
    > move east
    Ops that is not possible.
    > █

## It's a trap!

Not all exits are reversible, sometimes
you cannot go back when you reach a place.

    A new world.
    You see in front of your eyes one of the most
    beautiful and incredible worlds that you can
    imagine. Towards the south, you can see a big
    sea spotted with tiny islands, in the east,
    just far away, you can see the City of the East,
    with big spikes pointing to the sky, and in the
    west, there is the Enchanted Forest and the 
    Eternity Mountains reaching the clouds.
    Exits: north, south, west.
    > move north
    Cabin.
    That is your cabin. Old fashioned wooden made. 
    You are in the yard in front of it, with 
    beautiful vegetation and a nice Porch. Towards 
    the west, there is a small path towards the 
    Enchanted Forest..
    Exits: south, east.
    > move east
    Well.
    You fell into the Well. It is dark and moisty. 
    The water level reaches your waist. You tried 
    to climb the walls of the well, but rocks are 
    wet. You slid to the bottom again. It seems 
    that you are trapped without exit.
    > █

    `}
    />
  ),
}
