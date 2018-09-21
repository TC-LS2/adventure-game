import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Aug 9 2018").getTime(),
  title: "(WIP) Weapons and Shields",
  tagline: "This monster is too hard? look for a weapon and a shield!",
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

    Armory.
    There is a plenty of weapons for a life time.
    Item: Swordstone.
    Exits: north.
    Player life points: 16.
    > get
    Armory.
    There is a plenty of weapons for a life time.
    Exits: north.
    Player Weapon: Swordstone.
    Player life points: 16.
    > north
    Goron Nest.
    Gorons are famous for having big treasures.
    And there is a Goron here.
    It seems very hard.
    Room Monster: Goron.
    Player Weapon: Swordstone.
    Player life points: 16.
    > attack
    Goron Nest.
    Gorons are famous for having big treasures.
    And there is a Goron here.
    It seems very hard.
    Player Weapon: Swordstone.
    Player life points: 8.
    > █

## Protect yourself

You got too much damage? 
Try to get a good shield, it
will reduce how mage damage your avatar take.

    Closet
    There is your shirt. 
    One for each day of the week.
    Item: shirt.
    Exits: north.
    Player life points: 16.
    > get
    Closet
    There is your shirt. 
    One for each day of the week.
    Exits: north.
    Player Shield: shirt.
    Player life points: 16.
    > north
    Home Sweet home.
    You are at home, but there is a mosquito
    waiting for the night.
    Room Monster: mosquito.
    Player Shield: shirt.
    Player life points: 16.
    > attack
    The monster attacked you back. You killed the monster.
    Home Sweet home.
    You are at home, but there is a mosquito
    waiting for the night.
    Player Shield: shirt.
    Player life points: 16.
    > █


## Combine all items

You can hold up to three different items
at the same time:
a key, a weapon and a shield.

    Armory.
    There is a plenty of weapons for a life time.
    Item: Swordstone.
    Exits: north.
    Player life points: 16.
    > get
    Armory.
    There is a plenty of weapons for a life time.
    Exits: north.
    Player Weapon: Swordstone.
    Player life points: 16.
    > north
    Armory.
    There is a plenty of shields for a life time.
    Item: Ironshdield.
    Exits: north.
    Player Weapon: Swordstone.
    Player life points: 16.
    > get
    Armory.
    There is a plenty of shields for a life time.
    Exits: north.
    Player Weapon: Swordstone.
    Player Shield: Ironshdield.
    Player life points: 16.
    > north
    Key Locker.
    There are all the keys.
    Item: treasure key.
    Exits: north.
    Player Weapon: Swordstone.
    Player life points: 16.
    > get
    Armory.
    There is a plenty of shields for a life time.
    Exits: north.
    Player Weapon: Swordstone.
    Player Shield: Ironshdield.
    Player Key: treasure key.
    Player life points: 16.
    > north
    Goron Nest.
    Gorons are famous for having big treasures.
    And there is a Goron here.
    It seems very hard.
    Room Monster: Goron.
    Player Weapon: Swordstone.
    Player Shield: Ironshdield.
    Player life points: 16.
    > attack
    Goron Nest.
    Gorons are famous for having big treasures.
    And there is a Goron here.
    It seems very hard.
    Player Weapon: Swordstone.
    Player Shield: Ironshdield.
    Player life points: 12.
    > █

## One hand one weapon

What is life without limits? You can carry one
weapon and only one weapon. If you get a second
weapon you drop de first.

    Armory.
    It has plenty weapons.
    Room item: Small sword.
    Exits: north.
    Player life points: 16.
    > get
    Armory.
    It has plenty weapons.
    Exits: north.
    Player weapon: Small sword.
    Player life points: 16.
    > north
    Armory.
    It has plenty weapons.
    Room item: Large sword.
    Exits: north.
    Player weapon: Small sword.
    Player life points: 16.
    > get
    Armory.
    It has plenty weapons.
    Room item: Small sword.
    Exits: north.
    Player weapon: Large sword.
    Player life points: 16.
    > █

## One hand one shield

And you can have only one shield.

    Armory.
    It has plenty shields.
    Room item: Small shield.
    Exits: north.
    Player life points: 16.
    > get
    Armory.
    It has plenty shields.
    Exits: north.
    Player shield: Small shield.
    Player life points: 16.
    > north
    Armory.
    It has plenty shields.
    Room item: Large shield.
    Exits: north.
    Player shield: Small shield.
    Player life points: 16.
    > get
    Armory.
    It has plenty shields.
    Room item: Small shield.
    Exits: north.
    Player shield: Large shield.
    Player life points: 16.
    > █

## You die? you loose them all.

    Initial Room.
    You journey starts here.
    Exits: north
    Player life points: 16.
    > north
    Armory.
    It has plenty weapons.
    Room item: Sword.
    Exits: north.
    Player life points: 16.
    > get
    Armory.
    It has plenty weapons.
    Exits: north.
    Player weapon: Sword.
    Player life points: 16.
    > north
    Armory.
    It has plenty shields.
    Room item: Shield.
    Exits: north.
    Player weapon: Sword.
    Player life points: 16.
    > get
    Armory.
    It has plenty shields.
    Exits: north.
    Player weapon: Sword.
    Player shield: Shield.
    Player life points: 16.
    > north
    World's end.
    Cthulhu is here...
    Room Monster: Chtulhu.
    Player weapon: Sword.
    Player shield: Shield.
    Player life points: 16.
    > attack
    The monster killed you.
    > look
    Initial Room.
    You journey starts here.
    Exits: north
    Player life points: 16.
    > █

    `}
    />
  ),
}
