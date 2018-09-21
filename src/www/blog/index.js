import kebabCase from "kebab-case"

import food from "./food"
import helloWorld from "./hello-world"
import keysAndDoors from "./keys-and-doors"
import monsters from "./monsters"
import moveAround from "./move-around"
import roomRefresh from "./room-refresh"
import weaponsAndShields from "./weapons-and-shields"

const entries = {
  food,
  helloWorld,
  keysAndDoors,
  monsters,
  moveAround,
  roomRefresh,
  weaponsAndShields,
}

const blog = Object.keys(entries)
  .map(key => ({
    id: kebabCase(key),
    ...entries[key],
  }))
  .sort((a, b) => b.date - a.date)

export default blog
