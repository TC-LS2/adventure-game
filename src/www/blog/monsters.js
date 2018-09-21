import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Jul 30 2018").getTime(),
  title: "Monsters!",
  world: {
    rooms:
      "0 0:Home sweet home:-1 123 -1 0:fly\n" +
      "You are in the main room of your home. There is plenty of light and space.\n" +
      "::::\n" +
      "0 -1:Sweet bedroom:-1 0 0 -1:mosquito\n" +
      "That is your bedrooom, possibly the most cosy place of all of your home. Did you listen that?\n" +
      "::::\n" +
      "-1 -1:Bathroom:0 -1 -1 -1:big mosquito\n" +
      "That was the bathroom of your dreams, nice light, clean, incredible bathroom... is someone knoking to the door?\n" +
      "::::\n" +
      "-1 0:Cabin:0 0 -1 -1:Goron\n" +
      "You are in front of your cabin, main door is closed. You remember that you have the key under the carpet. There is a Goron here. Gorons are famous for having big treasures. It seems very hard.\n" +
      "::::\n" +
      "-2 0:World's End:0 -1 -1 -1:Cthulhu\n" +
      "Cthulhu is here...\n" +
      "::::\n",
    items: "main house key: key 123\n",
    monsters:
      "fly:0 0:nothing\n" +
      "mosquito:2 0:nothing\n" +
      "big mosquito:3 0:main house key\n" +
      "Goron:8 8:nothing\n" +
      "Cthulhu:999 999:nothing\n",
  },
  map:
    "+-------------+      +---------------+      \n" +
    "|SWEET BEDROOM|      |HOME SWEET HOME|      \n" +
    "|that is yo...o------oyou are in t...|      \n" +
    "|   (mosquito)|      |          (fly)|      \n" +
    "+-----o-------+      +------▒--------+      \n" +
    "      |                     |               \n" +
    "      |                     |               \n" +
    "+-----o-------+      +------o--------+      \n" +
    "|   BATHROOM  |      |     CABIN     |      \n" +
    "|that was t...x      xyou are in f...|      \n" +
    "|(big mosqu...|      |        (Goron)|      \n" +
    "+-------------+      +------o--------+      \n" +
    "                            |               \n" +
    "                            |               \n" +
    "                     +------o--------+      \n" +
    "                     |  WORLD'S END  |      \n" +
    "                     |cthulhu is h...|      \n" +
    "                     |      (Cthulhu)|      \n" +
    "                     +---------------+      \n",
  tagline:
    "Oh no! You are now in real danger! There are monsters... but they may have treasures.",
  Component: () => (
    <ReactMarkdown
      source={`
## Defeat monsters!

It is ok to be afraid of monsters,
specially when you have no weapons and no shields.
But not everything is lost, 
you can still try to get rid
of them.

There are some monsters that are really tiny
and weak. You can try to attack to any of them.

    > look 
    Home sweet home
    You are in the main room of your home. There is pl
    enty of light and space.
    Exits: south (closed), west.
    There is the fly monster.
    Player has 16 life points.
    > attack 
    Home sweet home
    You are in the main room of your home. There is pl
    enty of light and space.
    Exits: south (closed), west.
    Player has 16 life points.
    > █

## Ouch, they strike back.

Not all monsters are harmless, some of
them will attack you back.
If that happens get ready to take
some damage!

    > move west
    Sweet bedroom
    That is your bedrooom, possibly the most cosy plac
    e of all of your home. Did you listen that?
    Exits: south, east.
    There is the mosquito monster.
    Player has 16 life points.
    > attack 
    Sweet bedroom
    That is your bedrooom, possibly the most cosy plac
    e of all of your home. Did you listen that?
    Exits: south, east.
    Player has 14 life points.
    > █

## Monsters have treasures!

Why to mess around with monsters? Why 
risk your avatar to have its ass kicked?
Because monsters keep treasures!
And... if you defeat them
they will leave their treasure behind...
and you will be able to get it!

    Main door.
    There is the door to enter, 
    and you can see the key.
    But it seems that there is a mosquito
    preventing you to get the key.
    Room Monster: mosquito.
    Player life points: 16.
    > attack
    The monster attacked you back. You killed the monster.
    Main door.
    There is the door to enter, 
    and you can see the key.
    But it seems that there is a mosquito
    preventing you to get the key.
    Room item: main key.
    Player life points: 14.
    > █

## You may not even scratch them.

Ops, your avatar is soft, really really soft.
Do not have claws, paws, hooks, thorns, or
any other alternative.
But some monsters have really hard skin, 
caparaces, ... and they are really hard to damage.
So it is possible that in attack you will
not damage them at all.

    > move north
    Sweet bedroom
    That is your bedrooom, possibly the most cosy plac
    e of all of your home. Did you listen that?
    Exits: south, east.
    Player has the main house key key.
    Player has 11 life points.
    > move east
    Home sweet home
    You are in the main room of your home. There is pl
    enty of light and space.
    Exits: south (closed), west.
    Player has the main house key key.
    Player has 11 life points.
    > move south
    Cabin
    You are in front of your cabin, main door is close
    d. You remember that you have the key under the ca
    having big treasures. It seems very hard.
    Exits: north, south.
    There is the Goron monster.
    Player has 11 life points.
    > attack 
    Cabin
    You are in front of your cabin, main door is close
    d. You remember that you have the key under the ca
    having big treasures. It seems very hard.
    Exits: north, south.
    There is the Goron monster.
    Player has 3 life points.
    > █


## Oh oh, your avatar may die.

That is sad, but it is possible that a very
dangerous and powerful monster will kill you.
    
    > move south
    World's End
    Cthulhu is here...
    Exits: north.
    There is the Cthulhu monster.
    Player has 3 life points.
    > attack 
    The monster killed you.
    
But when you die, you go back to the initial room,
full of life but without objects.

    > look 
    Home sweet home
    You are in the main room of your home. There is pl
    enty of light and space.
    Exits: south, west.
    Player has 16 life points.
    > █

## No monster, no attack

Uhmm, are you trying to attack when
there is no monster?

    > attack
    There is no monster.
    > █

`}
    />
  ),
}
