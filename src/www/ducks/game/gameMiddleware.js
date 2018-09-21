import rest from "../../common/rest"
import { setAlert } from "../alert/actions/setAlert"
import { POST_COMMAND } from "./actions/postCommand"
import { setGame } from "./actions/setGame"

const gameMiddleware = ({ dispatch }) => next => async action => {
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
    default: // nothing
  }
}
export default gameMiddleware
