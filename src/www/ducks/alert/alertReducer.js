import { CLEAR_ALERT } from "./actions/clearAlert"
import { SET_ALERT } from "./actions/setAlert"

const alertReducer = (state = null, action) => {
  switch (action.type) {
    case CLEAR_ALERT: {
      return null
    }
    case SET_ALERT: {
      const { alert } = action
      return alert
    }
    default:
      return state
  }
}

export default alertReducer
