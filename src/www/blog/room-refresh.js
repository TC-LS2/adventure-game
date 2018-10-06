import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Sep 03 2018").getTime(),
  title: "Room Refresh",
  tagline: "Someone else defeated your monster? It will revive!",
  world: {
    rooms:
      "0 0:High castle:123 -1 -1 -1:rusty key\n" +
      "You are in the highest tower of a castle in the highest mountain. You can listen to the airflow between your ears. It seems that there is nobody here, but yet, there is a key, and there is a closed door that brings to the highest room of this castle.\n" +
      "::::\n" +
      "1 0:Highest room:-1 0 -1 -1:snail\n" +
      "There is a monster here. Just old. Just waiting for you. It has that object that you wish for your whole life.\n" +
      "::::\n",
    monsters: "snail:0 0:wisdom sphere\n",
    items: "rusty key: key 123\nwisdom sphere: key 875\n",
  },
  map:
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
    "+------------+      \n",
  Component: () => (
    <ReactMarkdown
      source={`
There are too many players, and they are spoiling your game? Are they getting the best objects leaving for you with crumbs? Do not worry, just wait. Everything gets new as fresh.

## Objects reappear
Someone got the room object of this room before you? Do not worry, just wait. Everything will re-appear in almost no time.

    > look 
    High castle
    You are in the highest tower of a castle in the hi
    ghest mountain. You can listen to the airflow betw
    but yet, there is a key, and there is a closed do
    Exits: north (closed).
    There is the rusty key key.
    Player has 16 life points.
    > get 
    High castle
    You are in the highest tower of a castle in the hi
    ghest mountain. You can listen to the airflow betw
    but yet, there is a key, and there is a closed do
    Exits: north (closed).
    Player has the rusty key key.
    Player has 16 life points.
    ... time skip ...
    > look 
    High castle
    You are in the highest tower of a castle in the hi
    ghest mountain. You can listen to the airflow betw
    but yet, there is a key, and there is a closed do
    Exits: north (closed).
    There is the rusty key key.
    Player has the rusty key key.
    Player has 16 life points.
    > █

## Doors close
You left your home door opened? Someone ruined the opportunity of a quest for the queen of the deadliest room? Do not worry, just wait. Doors will close in almost no time.

    > move north
    Highest room
    There is a monster here. Just old. Just waiting fo
    r you. It has that object that you wish for your w
    Exits: south.
    There is the snail monster.
    Player has 16 life points.
    > move south
    High castle
    You are in the highest tower of a castle in the hi
    ghest mountain. You can listen to the airflow betw
    but yet, there is a key, and there is a closed do
    Exits: north.
    Player has 16 life points.
    ... time skip ...
    > look 
    High castle
    You are in the highest tower of a castle in the hi
    ghest mountain. You can listen to the airflow betw
    but yet, there is a key, and there is a closed do
    Exits: north (closed).
    There is the rusty key key.
    Player has 16 life points.
    > █

## Monsters, respawn.
Oh yes, you are thinking: I want to kill that horrible monster. You arrive at the room, and someone got the monster killed just before you. Do not worry, just wait. Monsters will respawn in almost no time.

    > get 
    > move north
    > attack 
    Highest room
    There is a monster here. Just old. Just waiting fo
    r you. It has that object that you wish for your w
    Exits: south.
    There is the wisdom sphere key.
    Player has 16 life points.
    ... time skip ...
    > look 
    Highest room
    There is a monster here. Just old. Just waiting fo
    r you. It has that object that you wish for your w
    Exits: south.
    There is the snail monster.
    Player has 16 life points.
    > █

    `}
    />
  ),
}
