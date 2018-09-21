import rest from "../../common/rest"
import { setAlert } from "../alert/actions/setAlert"
import { POST_COMMAND, postCommand } from "./actions/postCommand"
import { setGame, SET_GAME } from "./actions/setGame"
import getGame from "./selectors/getGame"

const REFRESH_TIMEOUT_MILLISECONDS = 40000

let refreshTimeout
const gameMiddleware = ({ dispatch, getState }) => next => async action => {
  next(action)

  switch (action.type) {
    case POST_COMMAND: {
      const { type, ...command } = action

      try {
        const game = await rest.post("/api/v1/commands", command)
        dispatch(setGame(game))
      } catch (response) {
        if (response.data && response.data.message) {
          dispatch(setAlert(response.data.message))
        }
      }

      break
    }

    case SET_GAME: {
      if (refreshTimeout) clearTimeout(refreshTimeout)
      refreshTimeout = setTimeout(() => {
        const game = getGame(getState())
        dispatch(postCommand(game.player.username, "look"))
      }, REFRESH_TIMEOUT_MILLISECONDS)

      break
    }

    default: // nothing
  }
}
export default gameMiddleware
