let north, south, east, west

function plus(coordinates) {
  return { i: coordinates.i + this.di, j: coordinates.j + this.dj }
}

north = { name: "north", di: +1, dj: 0, reverse: () => south, plus }
south = { name: "south", di: -1, dj: 0, reverse: () => north, plus }
east = { name: "east", di: 0, dj: +1, reverse: () => west, plus }
west = { name: "west", di: 0, dj: -1, reverse: () => east, plus }

export { north, south, east, west }
