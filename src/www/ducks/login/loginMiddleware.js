import { push } from "connected-react-router"
import { postCommand } from "../game/actions/postCommand"
import { SET_GAME } from "../game/actions/setGame"
import hasGame from "../game/selectors/hasGame"

import { LOGIN } from "./actions/login"

const loginMiddleware = ({ getState, dispatch }) => next => action => {
  next(action)

  switch (action.type) {
    case LOGIN: {
      const { username } = action
      dispatch(postCommand(username, "look", []))

      break
    }

    case SET_GAME: {
      const hasValidGame = !!hasGame(getState())
      const isLoginPage = getState().router.location.pathname === "/login"

      if (hasValidGame && isLoginPage) {
        dispatch(push("/game"))
      }
      break
    }
    default: // nothing
  }
}

export default loginMiddleware
