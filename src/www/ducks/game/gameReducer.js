import { SET_GAME } from "./actions/setGame"

const gameReducer = (state = null, action) => {
  switch (action.type) {
    case SET_GAME: {
      const { game } = action
      return game
    }
    default:
      return state
  }
}
export default gameReducer
