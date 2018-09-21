import { SET_WORLD } from "./actions/setWorld"

const builderWorldReducer = (state = {}, action) => {
  switch (action.type) {
    case SET_WORLD:
      return { ...state, ...action.world }
    default:
      return state
  }
}

export default builderWorldReducer
