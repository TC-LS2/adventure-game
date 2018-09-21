import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Aug 22 2018").getTime(),
  title: "Food",
  world: {
    rooms:
      "0 0:Home sweet home:-1 -1 0 -1:mosquito\n" +
      "You are at the main room of your home. There is a disturbing little noise around.\n" +
      "::::\n" +
      "0 1:Kitchen:-1 -1 -1 0:best cake ever\n" +
      "That is the kitchen. That is the place where you prepare your most delicious dishes. What is that that smells so good?\n" +
      "::::\n",
    items: "mosquito leg: food 2\nbest cake ever: food 16\n",
    monsters: "mosquito:2 0:mosquito leg\n",
  },
  map:
    "+---------------+      +-------+      \n" +
    "|HOME SWEET HOME|      |KITCHEN|      \n" +
    "|you are at t...o------othat...|      \n" +
    "|     (mosquito)|      |(bes...|      \n" +
    "+---------------+      +-------+      \n",
  tagline:
    "Having a hard time against a monster, do not worry, eat food and you will recover.",
  Component: () => (
    <ReactMarkdown
      source={`
## Recover loosed life points.

Did you think that lost life points are forever? They did not!
You can recover from wounds by eating the food required to 
reconstruct the wounds.

Just look around for food, and get it.
Your avatar will eat it automatically.

    > look 
    Home sweet home
    You are at the main room of your home. There is a 
    disturbing little noise around.
    Exits: east.
    There is the mosquito monster.
    Player has 16 life points.
    > attack 
    Home sweet home
    You are at the main room of your home. There is a 
    disturbing little noise around.
    Exits: east.
    There is the mosquito leg food.
    Player has 14 life points.
    > get 
    Home sweet home
    You are at the main room of your home. There is a 
    disturbing little noise around.
    Exits: east.
    Player has 16 life points.
    > █

## Eat is worthless if your are not hungry

You have full life points but you want to get some
food. Get it, but you will not earn any additional
life points and you will loose the opportunity
to eat it later.

    > move east
    Kitchen
    That is the kitchen. That is the place where you p
    repare your most delicious dishes. What is that th
    Exits: west.
    There is the best cake ever food.
    Player has 16 life points.
    > get 
    Kitchen
    That is the kitchen. That is the place where you p
    repare your most delicious dishes. What is that th
    Exits: west.
    Player has 16 life points.
    > █



    `}
    />
  ),
}
