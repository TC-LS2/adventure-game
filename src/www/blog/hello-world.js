import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Jul 3 2018").getTime(),
  title: "Hello World",
  tagline: "Hello to this world. You finally arrived!",
  world: {
    rooms:
      "0 0:A new world:-1 -1 -1 -1:nothing\n" +
      "You see in front of your eyes one of the most\n" +
      "beautiful and incredible worlds that you can\n" +
      "imagine. Towards the south, you can see a big\n" +
      "sea spotted with tiny islands, in the east,\n" +
      "just far away, you can see the City of the East,\n" +
      "with big spikes pointing to the sky, and in the\n" +
      "west, there is the Enchanted Forest and the \n" +
      "Eternity Mountains reaching the clouds.\n" +
      "::::\n",
  },
  map:
    " City       +--------------+       Enchanted Forest \n" +
    "of the  :---| A new World  |---:         and        \n" +
    " East       +--------------+      Eternity Mountains\n" +
    "                   |      	                         \n" +
    "                   v              N                 \n" +
    "              Tiny Islands      W + E               \n" +
    "                                  S                 \n",
  Component: () => (
    <ReactMarkdown
      escapeHtml={false}
      source={`
Welcome!
We are pleased to see you here.

There is a new world here,
just inside your machine.
Open your eyes, look,
and discover a world that
only existed in your mind before.
       						 
## Do login

Pick a username for your avatar,
put it down, and click enter.
You will discover a new world.
Learn its name and what
is in front of the eyes of your avatar.

    > login kirito
    A new world.
    You see in front of your eyes one of the most
    beautiful and incredible worlds that you can
    imagine. Towards the south, you can see a big
    sea spotted with tiny islands, in the east,
    just far away, you can see the City of the East,
    with big spikes pointing to the sky, and in the
    west, there is the Enchanted Forest and the 
    Eternity Mountains reaching the clouds.
    > █

## Look again
Do you think that you missed something?
Just command your avatar  \`look\` again,
you will see the name and the description of
the space where your avatar is.

    > look
    A new world.
    You see in front of your eyes one of the most
    beautiful and incredible worlds that you can
    imagine. Towards the south, you can see a big
    sea spotted with tiny islands, in the east,
    just far away, you can see the City of the East,
    with big spikes pointing to the sky, and in the
    west, there is the Enchanted Forest and the 
    Eternity Mountains reaching the clouds.
    > █

    `}
    />
  ),
}
