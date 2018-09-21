import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Jul 30 2018").getTime(),
  title: "(WIP) Monsters!",
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

    Home Sweet home.
    You are at home, but there is a fly
    disturbing your peace.
    Room Monster: fly.
    Player life points: 16.
    > attack
    You killed the monster.
    Home Sweet home.
    You are at home, but there is a fly
    disturbing your peace.
    Player life points: 16.
    > █

## Ouch, they strike back.

Not all monsters are harmless, some of
them will attack you back.
If that happens get ready to take
some damage!

    Home Sweet home.
    You are at home, but there is a mosquito
    waiting for the night.
    Room Monster: mosquito.
    Player life points: 16.
    > attack
    The monster attacked you back. You killed the monster.
    Home Sweet home.
    You are at home, but there is a mosquito
    waiting for the night.
    Player life points: 14.
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

    Goron Nest.
    Gorons are famous for having big treasures.
    And there is a Goron here.
    It seems very hard.
    Room Monster: Goron.
    Player life points: 16.
    > attack
    Goron Nest.
    Gorons are famous for having big treasures.
    And there is a Goron here.
    But it seems very hard.
    Room Monster: Goron.
    Player life points: 8.
    > █


## Oh oh, your avatar may die.

That is sad, but it is possible that a very
dangerous and powerful monster will kill you.
    
    World's end.
    Cthulhu is here...
    Room Monster: Chtulhu.
    Player life points: 16.
    > attack
    The monster killed you.
    
But when you die, you go back to the initial room,
full of life but without objects.

    Initial Room.
    You journey starts here.
    Room Monster: mosquito.
    Exits: north
    Player life points: 16.
    > attack
    The monster attacked you back. You killed the monster.
    Initial Room.
    You journey starts here.
    Room item: some key.
    Exits: north
    Player life points: 14.
    > get
    Initial Room.
    You journey starts here.
    Exits: north
    Player item: some key.
    Player life points: 14.
    > north
    World's end.
    Cthulhu is here...
    Room Monster: Chtulhu.
    Player item: some key.
    Player life points: 14.
    > attack
    The monster killed you.
    > look
    Initial Room.
    You journey starts here.
    Exits: north
    Player life points: 16.
    > █

## No monster, no attack

Uhmm, are you trying to attack when
there is no monster?

    Empty room.
    There is nothing here.
    Please, come back another day.
    > attack
    There is no monster.
    > █

`}
    />
  ),
}
