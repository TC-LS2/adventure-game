import rest from "../../common/rest"
import { setAlert } from "../alert/actions/setAlert"
import { POST_COMMAND } from "./actions/postCommand"
import { setGame } from "./actions/setGame"

const logGame = ({
  player: { username, lifePoints, key, shield, weapon },
  room: { name, description, exits, item, monster },
}) => {
  console.log(
    [
      name,
      description,
      exits &&
        exits.length &&
        `Exits: ${exits
          .map(exit => `${exit.name}${(!exit.opened && "(closed)") || ""}`)
          .join(", ")}.`,
      item && `There is a ${item.name}`,
      monster && `There is a ${monster.name}`,
      key && `You have a ${key}`,
      weapon && `You have a ${weapon}`,
      shield && `You have a ${shield}`,
      `You have a ${key}`,
      `Life points ${lifePoints}`,
    ]
      .filter(x => x)
      .join("\n"),
  )
}

const gameMiddleware = ({ dispatch }) => next => async action => {
  next(action)

  switch (action.type) {
    case POST_COMMAND: {
      const { type, ...command } = action

      try {
        const game = await rest.post("/api/v1/commands", command)
        logGame(game)
        dispatch(setGame(game))
      } catch (response) {
        if (response.data && response.data.message) {
          dispatch(setAlert(response.data.message))
        } else if (response && response.message) {
          dispatch(setAlert("Error: " + response.message))
          console.error(response)
        }
      }

      break
    }
    default: // nothing
  }
}
export default gameMiddleware
