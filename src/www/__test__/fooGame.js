const fooGame = {
  player: {
    username: "foo-player",
    lifePoints: 700,
    key: { name: "foo-key" },
    shield: null,
    weapon: null,
  },
  room: {
    name: "Foo-room",
    description:
      "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud.",
    exits: [
      {
        name: "north",
        open: true,
      },
      {
        name: "east",
        open: false,
      },
    ],
    item: { name: "foo-item", type: "key" },
    monster: null,
  },
}

export default fooGame
