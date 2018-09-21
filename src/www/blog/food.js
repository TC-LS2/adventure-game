import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Aug 22 2018").getTime(),
  title: "(WIP) Food",
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

    Home Sweet home.
    You are at home, but there is a mosquito
    waiting for the night.
    Room Monster: mosquito.
    Exits: north.
    Player life points: 16.
    > attack
    The monster attacked you back. You killed the monster.
    Home Sweet home.
    You are at home, but there is a mosquito
    waiting for the night.
    Exits: north.
    Player life points: 14.
    > north
    Kitchen.
    You are in the kitchen. You
    always keep some cookies for
    quick recoveries.
    Room Item: Cookies.
    Player life points: 14.
    > get
    Kitchen.
    You are in the kitchen. You
    always keep some cookies for
    quick recoveries.
    Player life points: 16.
    > █

## Eat is worthless if your are not hungry

You have full life points but you want to get some
food. Get it, but you will not earn any additional
life points and you will loose the opportunity
to eat it later.

    Kitchen.
    You are in the kitchen. You
    always keep some cookies for
    quick recoveries.
    Room Item: Cookies.
    Player life points: 16.
    > get
    Kitchen.
    You are in the kitchen. You
    always keep some cookies for
    quick recoveries.
    Player life points: 16.
    > █



    `}
    />
  ),
}
