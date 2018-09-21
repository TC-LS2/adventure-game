import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Sep 03 2018").getTime(),
  title: "(TODO) Room Refresh",
  tagline: "Someone else defeated your monster? It will revive!",
  Component: () => (
    <ReactMarkdown
      source={`
Someone else defeated your monster? Do not worry. It will revive!
Players, but they resuscitate at the initial room,
why do monsters not deserve the same look?

What if you eat food by mistake when 
you were at the maximum of life and
now you need to eat it again.

Do not worry! Rooms refresh: everything returns
to the original state by itself.

## Keys re-appears

Did you have lost your keys? Do you think that you will need to use them again? No problem,
go to the room where you found the key and it
will reappear eventually.

    Janitor's Room.
    There are all the keys here.
    Room Item: Main Key.
    > get
    Janitor's Room.
    There are all the keys here.
    Player Key: Main Key.
    … wait for refresh …
    > look
    Janitor's Room.
    There are all the keys here.
    Room Item: Main Key.
    Player Key: Main Key.
    > █

## Doors closes

Do not be afraid of leaving the main door of your
home open. It closes automatically when the room
refreshes.

    Forest.
    On the top of that tree you have a copy
    of your home key.
    Room Item: Home Key.
    Exits: north
    > get
    Forest.
    On the top of that tree you have a copy
    of your home key.
    Exits: north
    Player Item: Home Key.
    > north
    Home sweet home.
    You are in front of your home,
    main door is closed, but
    key is under the carpet.
    Exits: north (closed), south.
    Player item: key.
    > north
    Home sweet home.
    You are in the main
    room of your home. There
    is plenty of light and space.
    Exits: south.
    > south
    Home sweet home.
    You are in front of your home,
    main door is closed, but
    key is under the carpet.
    Exits: north, south.
    Player item: key.
    … wait for refresh …
    > look
    Home sweet home.
    You are in front of your home,
    main door is closed, but
    key is under the carpet.
    Exits: north (closed), south.
    Player item: key.
    > █

## Weapons and shields re-appears

Did someone else take that weapon or that shield 
that you need to defeat the monster? Do not worry. 
They will reappear.



## Monsters re-appears

Yes! You can kill them twice or more times.
Are you not afraid of they become intelligent 
after many reboots, are you?
        

    `}
    />
  ),
}
