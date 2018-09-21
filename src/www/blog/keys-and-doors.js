import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Jul 18 2018").getTime(),
  title: "Keys and doors",
  tagline:
    "Is that door locked? No problem, now you can get a key and open it!",
  world: {
    rooms:
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
      "::::\n",
    items: "key: KEY 123\nrust key: KEY 234\nsmall key: KEY 345\n",
  },
  map:
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
    "+----+      +---------------+                                     \n",
  Component: () => (
    <ReactMarkdown
      source={`
## Closed doors

Some times you have find some closed doors.
Exits that if you try to follow they tell you that
they are close.

    Home sweet home.
    You are in front of your home,
    main door is closed.
    You remember that you have the 
    key under the carpet.
    Room item: key.
    Exits: north (closed), west (closed).
    > north
    Ops, that door is closed.
    > █

## Get the key and open the door.

Keys are items that can be carried and used.
If you see a key, get it. If you
want to cross a door with that key,
just cross the exit with the key.

    Home sweet home.
    You are in front of your home,
    main door is closed.
    You remember that you have the 
    key under the carpet.
    Room item: key.
    Exits: north (closed), west (closed).
    > get
    Home sweet home.
    You are in front of your home,
    main door is closed.
    You remember that you have the 
    key under the carpet.
    Exits: north (closed), west (closed).
    Player item: key.
    > north
    Home sweet home.
    You are in the main
    room of your home. There
    is plenty of light and space.
    Exits: south, north, east.
    > █

Once the exit with the key has traversed,
the door is left open and the key dissapears.

    Home sweet home.
    You are in front of your home,
    main door is closed.
    You remember that you have the 
    key under the carpet.
    Room item: key.
    Exits: north (closed), west (closed).
    > get
    Home sweet home.
    You are in front of your home,
    main door is closed.
    You remember that you have the 
    key under the carpet.
    Room item: key.
    Exits: north (closed), west (closed).
    Player item: key.
    > north
    Home sweet home.
    You are in the main
    room of your home. There
    is plenty of light and space.
    Exits: south, north, east.
    > south
    You are in front of your home,
    main door is closed.
    You remember that you have the 
    key under the carpet.
    Exits: north, west (closed).
    > █

## The right key

Not all keys open all exits,
each key may open a set of doors, one
door or none, you will have to discover
which key use.

    Home sweet home.
    You are in front of your home,
    main door is closed.
    You remember that you have the 
    key under the carpet.
    Exits: north, west (closed).
    > get
    Home sweet home.
    You are in front of your home,
    main door is closed.
    You remember that you have the 
    key under the carpet.
    Player item: key.
    > south
    Ops, you have the wrong key.

## One hand and so many keys

There is a limit of one key. If you
try to get another key your previous key
falls to the floor.

    > north
    Home sweet home.
    You are in the main
    room of your home. There
    is plenty of light and space.
    Exits: south, north, east.
    > north
    Key Locker.
    Here you have some useful keys.
    Exits: south.
    > get
    Key room.
    There are some keys stored here.
    Exits: north, west (closed).
    Player item: big key.
    > north
    There are some keys stored here.
    Room item: small key.
    Exits: south, north, east.
    Player item: big key.
    > get
    There are some keys stored here.
    Room item: big key.
    Exits: south, north, east.
    Player item: small key.
    > █

## No item, no get

Uhmm, are you trying to get a key when
there is no key?

    Empty room.
    There is nothing here.
    Please, come back another day.
    > get
    You got nothing.
    > █

    `}
    />
  ),
}
