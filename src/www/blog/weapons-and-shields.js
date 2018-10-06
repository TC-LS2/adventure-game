import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Aug 9 2018").getTime(),
  title: "Weapons and Shields",
  tagline: "This monster is too hard? look for a weapon and a shield!",
  world: {
    rooms:
      "0 0:Home sweet home:-1 0 -1 -1:swordstone\n" +
      "You are at the main room of your home. It seems that your last order just arrived, a mighty sword able to cut through rocks.\n" +
      "::::\n" +
      "-1 0:Cabin:0 0 -1 -1:Goron\n" +
      "You are in front of your cabin. There is a Goron here. Gorons are famous for having prominent treasures. It seems very hard.\n" +
      "::::\n" +
      "-2 0:Goron nest:0 -1 123 -1:Goron chief\n" +
      "You are inside a cave in the mountain. You found yourself in the middle of a Goron Nest. You wonder which marvelous treasures may hide.\n" +
      "::::\n" +
      "-2 1:Goron armory:-1 -1 0 0:ultimate sword\n" +
      "You managed to enter the armory. It is full of weapons and fabulous treasures.\n" +
      "::::\n" +
      "-2 2:Goron defense room:0 -1 -1 0:paper shield\n" +
      "Finally, you managed to arrive here. Here Gorons store the finest and effective shields in the world. It is pretty sure that they are more strong that they seem.\n" +
      "::::\n" +
      "-1 2:Volcano:-1 0 -1 -1:lava monster\n" +
      "Deeper in the Goron nest, you find the cauldron of a volcano. It is hot, and there is lava everywhere. It seems that something is moving and coming towards you.\n" +
      "::::\n",
    items:
      "swordstone: weapon 12\n" +
      "rockshield: shield 8\n" +
      "armory key: key 123\n" +
      "ultimate sword: weapon 16\n" +
      "paper shield: shield 1\n",
    monsters:
      "Goron:8 8:rockshield\n" +
      "Goron chief:10 12:armory key\n" +
      "lava monster:10 12:nothing\n",
  },
  map:
    "+---------------+                                                    \n" +
    "|HOME SWEET HOME|                                                    \n" +
    "|you are at t...|                                                    \n" +
    "|   (swordstone)|                                                    \n" +
    "+------o--------+                                                    \n" +
    "       |                                                             \n" +
    "       |                                                             \n" +
    "+------o--------+                          +------------------+      \n" +
    "|     CABIN     |                          |      VOLCANO     |      \n" +
    "|you are in f...|                          |deeper in the g...|      \n" +
    "|        (Goron)|                          |    (lava monster)|      \n" +
    "+------o--------+                          +--------o---------+      \n" +
    "       |                                            |                \n" +
    "       |                                            |                \n" +
    "+------o--------+      +------------+      +--------o---------+      \n" +
    "|   GORON NEST  |      |GORON ARMORY|      |GORON DEFENSE ROOM|      \n" +
    "|you are insi...▒------oyou manag...o------ofinally, you ma...|      \n" +
    "|  (Goron chief)|      |(ultimate...|      |    (paper shield)|      \n" +
    "+---------------+      +------------+      +------------------+      \n",
  Component: () => (
    <ReactMarkdown
      source={`
We introduce weapons and shields. 
Now you can go for larger monsters, and you have
to care less about take damage. 
Find the best weapons and shields and get
ready for epic battles.

## Beat them Weapons

Your avatar get stronger with weapons, 
now you can beat them. If you have one
get it.

    > look 
    Home sweet home
    You are at the main room of your home. It seems th
    at your last order just arrived, a mighty sword ab
    Exits: south.
    There is the swordstone weapon.
    Player has 16 life points.
    > get 
    Home sweet home
    You are at the main room of your home. It seems th
    at your last order just arrived, a mighty sword ab
    Exits: south.
    Player has the swordstone weapon.
    Player has 16 life points.
    > move south
    Cabin
    You are in front of your cabin. There is a Goron h
    ere. Gorons are famous for having prominent treasu
    Exits: north, south.
    There is the Goron monster.
    Player has the swordstone weapon.
    Player has 16 life points.
    > attack 
    Cabin
    You are in front of your cabin. There is a Goron h
    ere. Gorons are famous for having prominent treasu
    Exits: north, south.
    There is the rockshield shield.
    Player has the swordstone weapon.
    Player has 8 life points.
    > █

## Protect yourself

You got too much damage? 
Try to get a good shield, it
will reduce how mage damage your avatar take.

    > get 
    Cabin
    You are in front of your cabin. There is a Goron h
    ere. Gorons are famous for having prominent treasu
    Exits: north, south.
    Player has the rockshield shield.
    Player has the swordstone weapon.
    Player has 8 life points.
    > move south
    Goron nest
    You are inside a cave in the mountain. You found y
    ourself in the middle of a Goron Nest. You wonder 
    Exits: north, east (closed).
    There is the Goron chief monster.
    Player has the rockshield shield.
    Player has the swordstone weapon.
    Player has 8 life points.
    > attack 
    Goron nest
    You are inside a cave in the mountain. You found y
    ourself in the middle of a Goron Nest. You wonder 
    Exits: north, east (closed).
    There is the armory key key.
    Player has the rockshield shield.
    Player has the swordstone weapon.
    Player has 6 life points.
    > █


## Combine all items

You can hold up to three different items
at the same time:
a key, a weapon and a shield.

    > get 
    Goron nest
    You are inside a cave in the mountain. You found y
    ourself in the middle of a Goron Nest. You wonder 
    Exits: north, east (closed).
    Player has the armory key key.
    Player has the rockshield shield.
    Player has the swordstone weapon.
    Player has 6 life points.
    > █

## One hand one weapon

What is life without limits? You can carry one
weapon and only one weapon. If you get a second
weapon you drop de first.

    > move east
    Goron armory
    You managed to enter the armory. It is full of wea
    pons and fabulous treasures.
    Exits: east, west.
    There is the ultimate sword weapon.
    Player has the rockshield shield.
    Player has the swordstone weapon.
    Player has 6 life points.
    > get 
    Goron armory
    You managed to enter the armory. It is full of wea
    pons and fabulous treasures.
    Exits: east, west.
    There is the swordstone weapon.
    Player has the rockshield shield.
    Player has the ultimate sword weapon.
    Player has 6 life points.
    > █

## One hand one shield

And you can have only one shield.

    > move east
    Goron defense room
    Finally, you managed to arrive here. Here Gorons s
    tore the finest and effective shields in the world
    they seem.
    Exits: north, west.
    There is the paper shield shield.
    Player has the rockshield shield.
    Player has the ultimate sword weapon.
    Player has 6 life points.
    > get 
    Goron defense room
    Finally, you managed to arrive here. Here Gorons s
    tore the finest and effective shields in the world
    they seem.
    Exits: north, west.
    There is the rockshield shield.
    Player has the paper shield shield.
    Player has the ultimate sword weapon.
    Player has 6 life points.
    > █

## You die? you loose them all.

    > attack
    The monster killed you.
    > look 
    Home sweet home
    You are at the main room of your home. It seems th
    at your last order just arrived, a mighty sword ab
    Exits: south.
    Player has 16 life points.
    > █

    `}
    />
  ),
}
